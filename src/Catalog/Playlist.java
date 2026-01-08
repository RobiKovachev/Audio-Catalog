package Catalog;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Playlist {
    private String name;
    private String author;
    private String genre;
    private int year;
    private List<Description> items = new ArrayList<>();

    public Playlist(String name, String author, String genre, int year) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    public List<Description> getItems() {
        return items;
    }

    public void add(Description item) {
        items.add(item);
    }

    public boolean remove(Description item) {
        return items.remove(item);
    }

    public void sortByName() {
        items.sort(Comparator.comparing(Description::getName, String.CASE_INSENSITIVE_ORDER));
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", items=" + items +
                '}';
    }
}
