package Design;

import Catalog.*;
import Service.Functionalities;
import Service.StorageTXT;

import java.util.List;
import java.util.Scanner;

public class Menu {
    private Functionalities f = new Functionalities();
    Scanner scanner = new Scanner(System.in);

    public void run() {
        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> addItemUI();
                case "2" -> deleteItemUI();
                case "3" -> searchUI();
                case "4" -> filterUI();
                case "5" -> sortCatalogUI();
                case "6" -> listCatalogUI();
                case "7" -> createPlaylistUI();
                case "8" -> playlistManageUI();
                case "9" -> saveCatalogUI();
                case "10" -> loadCatalogUI();
                case "11" -> savePlaylistUI();
                case "12" -> loadPlaylistUI();
                case "0" -> {
                    System.out.println("Bye!");
                    return;
                }
                default -> System.out.println("Невалиден избор.");
            }
        }
    }

    private void printMenu() {

        System.out.println("\n=== AUDIO CATALOG ===");
        System.out.println("1) Add item");
        System.out.println("2) Delete item");
        System.out.println("3) Search");
        System.out.println("4) Filter");
        System.out.println("5) Sort catalog");
        System.out.println("6) Show catalog");
        System.out.println("7) Create playlist");
        System.out.println("8) Manage playlist");
        System.out.println("9) Save catalog in file");
        System.out.println("10) Load catalog by file");
        System.out.println("11) Save playlist in file");
        System.out.println("12) Load playlist by file");
        System.out.println("0) Exit");
        System.out.print("> ");

    }

    private void addItemUI() {
        System.out.print("Category (Song/Album/Podcast/Audiobook): ");
        String category = scanner.nextLine().trim().toLowerCase();

        System.out.print("Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Author: ");
        String author = scanner.nextLine().trim();

        System.out.print("Genre: ");
        String genre = scanner.nextLine().trim();

        System.out.print("Duration: ");
        double duration = Double.parseDouble(scanner.nextLine().trim());

        System.out.print("Year: ");
        int year = Integer.parseInt(scanner.nextLine().trim());

        Description item = switch (category) {
            case "podcast" -> new Podcast(name, genre, duration, "Podcast", author, year);
            case "audiobook" -> new Audiobook(name, genre, duration, "Audiobook", author, year);
            case "album" -> new Album(name, genre, duration, "Album", author, year);
            default -> new Song(name, genre, duration, "Song", author, year);
        };

        f.addItem(item);
        System.out.println("Added: "+ item);
        System.out.println("KEY: "+ item.key());
    }

    private void deleteItemUI() {
        System.out.print("Enter KEY (category|name|author|year): ");
        String key = scanner.nextLine().trim().toLowerCase();

        boolean ok = f.deleteItemByKey(key);
        System.out.println(ok? "Deleted." : "Not found.");
    }

    private void searchUI() {
        System.out.print("Search by (name/author/genre/year): ");
        String q = scanner.nextLine().trim();
        List<Description> res = f.search(q);
        printItems(res);
    }

    private void filterUI() {
        System.out.println("Filter by: (1-genre, 2-author,3-year) ");
        System.out.print("> ");
        String command = scanner.nextLine().trim();

        switch (command) {
            case "1" -> {
                System.out.print("Genre: ");
                printItems(f.filterByGenre(scanner.nextLine().trim()));
            }
            case "2" -> {
                System.out.print("Author: ");
                printItems(f.filterByAuthor(scanner.nextLine().trim()));
            }
            case "3" -> {
                System.out.print("Year: ");
                printItems(f.filterByYear(Integer.parseInt(scanner.nextLine().trim())));
            }
            default -> System.out.println("Wrong input.");
        }
    }

    private void sortCatalogUI() {
        printItems(f.sortedCatalog());
    }

    private void listCatalogUI() {
        printItems(f.getCatalog());
    }

    private void createPlaylistUI() {
        System.out.print("Name of the playlist: ");
        String name = scanner.nextLine().trim();

        System.out.print("Author: ");
        String author = scanner.nextLine().trim();

        System.out.print("Genre: ");
        String genre = scanner.nextLine().trim();

        System.out.print("Year: ");
        int year = Integer.parseInt(scanner.nextLine().trim());

        boolean ok = f.createPlaylist(name, author, genre, year);
        System.out.println(ok ? "Playlist created." : "There are playlist with this name.");
    }

    private void playlistManageUI() {
        System.out.print("Which playlist: ");
        String playlistName = scanner.nextLine().trim();
        Playlist playlist = f.getPlaylist(playlistName);
        if (playlist == null) {
            System.out.println("There is not playlist with this name.");
            return;
        }

        System.out.println("=== " + playlistName + " ===");
        System.out.println("1-Add item");
        System.out.println("2-Remove item");
        System.out.println("3-Show item");
        System.out.println("4-Sort items");
        System.out.println("> ");

        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1" -> {
                System.out.print("KEY: ");
                System.out.println(f.addToPlaylist(playlistName,scanner.nextLine()
                        .trim().toLowerCase()) ? "Added" : "Error");
            }
            case "2" -> {
                System.out.print("KEY: ");
                System.out.println(f.removeFromPlaylist(playlistName,scanner.nextLine()
                        .trim().toLowerCase()) ? "Removed" : "Error");
            }
            case "3" -> {
                printItems(playlist.getItems());
            }
            case "4" -> {
                f.sortPlaylist(playlistName);
                printItems(playlist.getItems());
            }
            default -> System.out.println("Invalid input");
        }
    }

    private void saveCatalogUI() {
        System.out.print("File: ");
        String file = scanner.nextLine().trim();
        StorageTXT.saveCatalog(f.getCatalog(), file);
    }

    private void loadCatalogUI() {
        System.out.print("File: ");
        String file = scanner.nextLine().trim();
        f.getCatalog().clear();
        f.getCatalog().addAll(StorageTXT.readCatalog(file));
    }

    private void savePlaylistUI() {
        System.out.print("Playlist name: ");
        String name = scanner.nextLine().trim();
        Playlist playlist = f.getPlaylist(name);
        if (playlist == null) {
            System.out.println("This playlist is not exist");
            return;
        }
        System.out.print("File (" + playlist.getName() + ".txt): ");
        String file = scanner.nextLine().trim();
        StorageTXT.savePlaylist(playlist, file);
    }

    private void loadPlaylistUI() {
        System.out.print("File: ");
        String file = scanner.nextLine().trim();
        Playlist playlist = StorageTXT.readPlaylist(file);
        if (playlist == null) return;

        f.createPlaylist(playlist.getName(), playlist.getAuthor(), playlist.getGenre(), playlist.getYear());
        Playlist stored = f.getPlaylist(playlist.getName());
        stored.getItems().clear();
        stored.getItems().addAll(playlist.getItems());
        System.out.println("Playlist loaded: " + stored);
    }

    private void printItems(List<Description> items) {
        if (items.isEmpty()) {
            System.out.println("Zero results");
            return;
        }
        for (Description d : items) {
            System.out.println("- " + d + "| KEY=" + d.key());
        }
    }

}
