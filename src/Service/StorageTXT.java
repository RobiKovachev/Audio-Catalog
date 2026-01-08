package Service;

import Catalog.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StorageTXT {

    public static void saveCatalog(List<Description> items, String file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Description item : items) {
                writer.write(item.getCategory() + "|" +
                        item.getName() + "|" +
                        item.getAuthor() + "|" +
                        item.getGenre() + "|" +
                        item.getDuration() + "|" +
                        item.getYear());
                writer.newLine();
            }
            System.out.println("Catalog saved.");
        }catch (IOException e){
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

   public static List<Description> readCatalog(String file) {
        List<Description> items = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] parts = line.split("\\|");

                if (parts.length != 6){
                    System.out.println("Invalid line skipped: " + line);
                    continue;
                }
                try {
                    String category = parts[0];
                    String name = parts[1];
                    String author = parts[2];
                    String genre = parts[3];
                    double duration = Double.parseDouble(parts[4]);
                    int year = Integer.parseInt(parts[5]);

                    Description item = switch (category) {
                        case "Podcast" -> new Podcast(name, genre, duration, "Podcast", author, year);
                        case "Audiobook" -> new Audiobook(name, genre, duration, "Audiobook", author, year);
                        case "Album" -> new Album(name, genre, duration, "Album", author, year);
                        default -> new Song(name, genre, duration, "Song", author, year);
                    };

                    items.add(item);
                    System.out.println(item);

                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format in line: " + line);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + file);
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
        return items;
   }

    public static void savePlaylist(Playlist playlist, String file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("PLAYLIST|" +
                    playlist.getName() + "|" +
                    playlist.getAuthor() + "|" +
                    playlist.getGenre() + "|" +
                    playlist.getYear());
            writer.newLine();

            for (Description item : playlist.getItems()) {
                writer.write(item.getCategory() + "|" +
                        item.getName() + "|" +
                        item.getAuthor() + "|" +
                        item.getGenre() + "|" +
                        item.getDuration() + "|" +
                        item.getYear());
                writer.newLine();
            }

            System.out.println("Playlist saved.");
        } catch (IOException e) {
            System.out.println("Error saving playlist: " + e.getMessage());
        }
    }

    public static Playlist readPlaylist(String file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String header = reader.readLine();
            if (header == null || header.isBlank()) {
                System.out.println("Empty playlist file: " + file);
                return null;
            }

            String[] hp = header.split("\\|");
            if (hp.length != 5 || !hp[0].equals("PLAYLIST")) {
                System.out.println("Invalid playlist header: " + header);
                return null;
            }

            String playlistName = hp[1].trim();
            String playlistAuthor = hp[2].trim();
            String playlistGenre = hp[3].trim();
            int playlistYear;
            try {
                playlistYear = Integer.parseInt(hp[4].trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid playlist year in header: " + header);
                return null;
            }

            Playlist playlist = new Playlist(playlistName, playlistAuthor, playlistGenre,playlistYear);

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] parts = line.split("\\|");
                if (parts.length != 6) {
                    System.out.println("Invalid line skipped: " + line);
                    continue;
                }

                try {
                    String category = parts[0];
                    String name = parts[1];
                    String author = parts[2];
                    String genre = parts[3];
                    double duration = Double.parseDouble(parts[4]);
                    int year = Integer.parseInt(parts[5]);

                    Description item = switch (category) {
                        case "Podcast" -> new Podcast(name, genre, duration, "Podcast", author, year);
                        case "Audiobook" -> new Audiobook(name, genre, duration, "Audiobook", author, year);
                        case "Album" -> new Album(name, genre, duration, "Album", author, year);
                        default -> new Song(name, genre, duration, "Song", author, year);
                    };

                    playlist.add(item);

                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format in line: " + line);
                }
            }

            System.out.println("Playlist loaded.");
            return playlist;

        } catch (FileNotFoundException e) {
            System.out.println("Playlist file not found: " + file);
        } catch (IOException e) {
            System.out.println("Error reading playlist: " + e.getMessage());
        }
        return null;
    }
}
