package model;

public class Movie {
    private String id;
    private String name;
    private String genre;
    private String duration;
    private String language;
    private String rating;

    public Movie() {
    }

    public Movie(String id, String name, String genre, String duration, String language, String rating) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.duration = duration;
        this.language = language;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}