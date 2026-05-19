package model;
//showtime 
public class Showtime {
    private String id;
    private String movieId;
    private String movieName;
    private String theater;
    private String showDate;
    private String showTime;
    private String hall;

    public Showtime() {
    }

    public Showtime(String id, String movieId, String movieName, String theater, String showDate, String showTime, String hall) {
        this.id = id;
        this.movieId = movieId;
        this.movieName = movieName;
        this.theater = theater;
        this.showDate = showDate;
        this.showTime = showTime;
        this.hall = hall;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getTheater() {
        return theater;
    }

    public void setTheater(String theater) {
        this.theater = theater;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }
}