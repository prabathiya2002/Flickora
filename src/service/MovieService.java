package service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import model.Movie;

public class MovieService extends BaseTextService<Movie> {

    @Override
    protected String getFileName() {
        return "movies.txt";
    }

    @Override
    protected String getId(Movie entity) {
        return entity.getId();
    }

    @Override
    protected void setId(Movie entity, String id) {
        entity.setId(id);
    }

    @Override
    protected String serialize(Movie entity) {
        return entity.getId() + "|" + safe(entity.getName()) + "|" + safe(entity.getGenre()) + "|" + safe(entity.getDuration()) + "|" + safe(entity.getLanguage()) + "|" + safe(entity.getRating());
    }

    @Override
    protected Movie deserialize(String line) {
        String[] values = line.split("\\|", -1);
        if (values.length < 6) {
            return null;
        }
        return new Movie(values[0], values[1], values[2], values[3], values[4], values[5]);
    }

    @Override
    protected String getIdPrefix() {
        return "M";
    }

    public Movie findByName(ServletContext context, String movieName) {
        for (Movie movie : findAll(context)) {
            if (movie.getName().equalsIgnoreCase(movieName)) {
                return movie;
            }
        }
        return null;
    }

    public List<Movie> search(ServletContext context, String keyword) {
        List<Movie> results = new ArrayList<Movie>();
        for (Movie movie : findAll(context)) {
            if ((movie.getName() != null && movie.getName().toLowerCase().contains(keyword.toLowerCase()))
                    || (movie.getGenre() != null && movie.getGenre().toLowerCase().contains(keyword.toLowerCase()))) {
                results.add(movie);
            }
        }
        return results;
    }

    private String safe(String value) {
        return value == null ? "" : value.replace("|", "/");
    }
}