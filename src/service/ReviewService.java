package service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import model.Review;

public class ReviewService extends BaseTextService<Review> {

    @Override
    protected String getFileName() {
        return "reviews.txt";
    }

    @Override
    protected String getId(Review entity) {
        return entity.getId();
    }

    @Override
    protected void setId(Review entity, String id) {
        entity.setId(id);
    }

    @Override
    protected String serialize(Review entity) {
        return entity.getId() + "|" + safe(entity.getUserId()) + "|" + safe(entity.getUserName()) + "|" + safe(entity.getMovieId()) + "|" + safe(entity.getMovieName()) + "|" + safe(entity.getRating()) + "|" + safe(entity.getComment()) + "|" + safe(entity.getReviewDate());
    }

    @Override
    protected Review deserialize(String line) {
        String[] values = line.split("\\|", -1);
        if (values.length < 8) {
            return null;
        }
        return new Review(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7]);
    }

    @Override
    protected String getIdPrefix() {
        return "RV";
    }

    public List<Review> findByMovieId(ServletContext context, String movieId) {
        List<Review> filtered = new ArrayList<Review>();
        for (Review review : findAll(context)) {
            if (review.getMovieId() != null && review.getMovieId().equalsIgnoreCase(movieId)) {
                filtered.add(review);
            }
        }
        return filtered;
    }

    public List<Review> findByUserId(ServletContext context, String userId) {
        List<Review> filtered = new ArrayList<Review>();
        for (Review review : findAll(context)) {
            if (review.getUserId() != null && review.getUserId().equalsIgnoreCase(userId)) {
                filtered.add(review);
            }
        }
        return filtered;
    }

    private String safe(String value) {
        return value == null ? "" : value.replace("|", "/");
    }
}
