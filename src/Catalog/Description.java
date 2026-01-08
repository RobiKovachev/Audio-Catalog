package Catalog;

public abstract class Description {
    protected String name;
    protected String genre;
    protected double duration;
    protected String category;
    protected String author;
    protected int year;

    public Description(String name, String genre, Double duration, String category, String author, int year) {
        this.name = name;
        this.genre = genre;
        this.duration = duration;
        this.category = category;
        this.author = author;
        this.year = year;
    }

    public String key() {
        return (category + "|" + name + "|" + author + "|" + year).toLowerCase();
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public double getDuration() {
        return duration;
    }

    public String getCategory() {
        return category;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "Description{" +
                "name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", duration=" + duration +
                ", category='" + category + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                '}';
    }
}
