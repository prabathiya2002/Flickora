package service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import model.Movie;
import model.Showtime;

public class ShowtimeService extends BaseTextService<Showtime> {

    @Override
    protected String getFileName() {
        return "showtimes.txt";
    }

    @Override
    protected String getId(Showtime entity) {
        return entity.getId();
    }

    @Override
    protected void setId(Showtime entity, String id) {
        entity.setId(id);
    }

    @Override
    protected String serialize(Showtime entity) {
        return entity.getId() + "|" + safe(entity.getMovieId()) + "|" + safe(entity.getMovieName()) + "|" + safe(entity.getTheater()) + "|" + safe(entity.getShowDate()) + "|" + safe(entity.getShowTime()) + "|" + safe(entity.getHall());
    }

    @Override
    protected Showtime deserialize(String line) {
        String[] values = line.split("\\|", -1);
        if (values.length < 7) {
            return null;
        }
        return new Showtime(values[0], values[1], values[2], values[3], values[4], values[5], values[6]);
    }

    @Override
    protected String getIdPrefix() {
        return "S";
    }

    public List<Showtime> findByMovieId(ServletContext context, String movieId) {
        List<Showtime> filtered = new ArrayList<Showtime>();
        for (Showtime showtime : findAll(context)) {
            if (showtime.getMovieId() != null && showtime.getMovieId().equalsIgnoreCase(movieId)) {
                filtered.add(showtime);
            }
        }
        return filtered;
    }

    public List<Showtime> findByMovieName(ServletContext context, String movieName) {
        List<Showtime> filtered = new ArrayList<Showtime>();
        for (Showtime showtime : findAll(context)) {
            if (showtime.getMovieName() != null && showtime.getMovieName().equalsIgnoreCase(movieName)) {
                filtered.add(showtime);
            }
        }
        return filtered;
    }

    public Showtime enrichWithMovie(Movie movie, Showtime showtime) {
        if (movie != null) {
            showtime.setMovieId(movie.getId());
            showtime.setMovieName(movie.getName());
        }
        return showtime;
    }

    private String safe(String value) {
        return value == null ? "" : value.replace("|", "/");
    }
}