package Service;

import Catalog.Description;
import Catalog.Playlist;

import java.util.*;
import java.util.stream.Collectors;

public class Functionalities {
    private final List<Description> catalog = new ArrayList<>();
    private final Map<String, Playlist> playlists = new HashMap<>();

    public List<Description> getCatalog() {
        return catalog;
    }
    public Collection<Playlist> getPlaylist(){
        return playlists.values();
    }

    public void addItem(Description item) {
        catalog.add(item);
    }

    public boolean deleteItemByKey(String key) {
        Description target = findByKey(key);
        if (target == null) return false;

        boolean removed = catalog.remove(target);
        if (removed) {
            for (Playlist p1 : playlists.values()) {
                p1.getItems().removeIf(x -> x.equals(target));
            }
        }
        return removed;
    }

    public List<Description> search(String query) {
        String q = query.toLowerCase();
        return catalog.stream().filter(x ->
                x.getName().toLowerCase().contains(q) ||
                x.getAuthor().toLowerCase().contains(q) ||
                x.getGenre().toLowerCase().contains(q) ||
                x.getCategory().toLowerCase().contains(q) ||
                String.valueOf(x.getYear()).contains(q)).collect(Collectors.toList());
    }

    public List<Description> filterByGenre(String genre) {
        String g = genre.toLowerCase();
        return catalog.stream().filter(x ->
                x.getGenre().toLowerCase().equals(g)).collect(Collectors.toList());
    }

    public List<Description> filterByAuthor(String author) {
        String a = author.toLowerCase();
        return catalog.stream().filter(x ->
                x.getAuthor().toLowerCase().contains(a)).collect(Collectors.toList());
    }

    public List<Description> filterByYear(int year) {
        return catalog.stream().filter(x ->
                x.getYear() == year).collect(Collectors.toList());
    }

    public List<Description> sortedCatalog() {
        return catalog.stream().sorted(Comparator.comparing(Description::getName, String.CASE_INSENSITIVE_ORDER)).collect(Collectors.toList());
    }

    public boolean createPlaylist(String name, String author, String genre, int year) {
        String key = name.toLowerCase();
        if(playlists.containsKey(key)) return false;
        playlists.put(key, new Playlist(name,author, genre, year));
        return true;
    }

    public Playlist getPlaylist(String name) {
        return playlists.get(name.toLowerCase());
    }

    public boolean addToPlaylist(String playlistName, String itemKey) {
        Playlist p1 = getPlaylist(playlistName);
        if (p1 == null) return false;

        Description item = findByKey(itemKey);
        if (item == null) return false;

        p1.add(item);
        return true;
    }

    public boolean removeFromPlaylist(String playlistName, String itemKey) {
        Playlist p1 = getPlaylist(playlistName);
        if (p1 == null) return false;

        Description item = findByKey(itemKey);
        if (item == null) return false;

        return p1.remove(item);
    }

    public void sortPlaylist(String playlistName) {
        Playlist p1 = getPlaylist(playlistName);
        if (p1 != null) p1.sortByName();
    }

    private Description findByKey(String key) {
        String k = key.toLowerCase();
        return catalog.stream().filter(x ->
                x.key().equals(k)).findFirst().orElse(null);
    }

    public void clearCatalogAndPlaylist() {
        catalog.clear();
        playlists.clear();
    }
}

