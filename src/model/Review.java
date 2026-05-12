package model;

public class Review {
    private String id;
    private String userId;
    private String userName;
    private String movieId;
    private String movieName;
    private String rating;
    private String comment;
    private String reviewDate;

    public Review() {
    }

    public Review(String id, String userId, String userName, String movieId, String movieName, String rating, String comment, String reviewDate) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.movieId = movieId;
        this.movieName = movieName;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }
}