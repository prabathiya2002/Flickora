package service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import util.FileHandler;

public abstract class BaseTextService<T> {
    protected final FileHandler fileHandler = new FileHandler();

    protected abstract String getFileName();

    protected abstract String getId(T entity);

    protected abstract void setId(T entity, String id);

    protected abstract String serialize(T entity);

    protected abstract T deserialize(String line);

    protected abstract String getIdPrefix();

    public List<T> findAll(ServletContext context) {
        List<T> records = new ArrayList<T>();
        for (String line : fileHandler.readLines(resolvePath(context))) {
            T entity = deserialize(line);
            if (entity != null) {
                records.add(entity);
            }
        }
        return records;
    }

    public T findById(ServletContext context, String id) {
        for (T entity : findAll(context)) {
            if (getId(entity).equalsIgnoreCase(id)) {
                return entity;
            }
        }
        return null;
    }

    public String add(ServletContext context, T entity) {
        String id = getId(entity);
        if (id == null || id.trim().isEmpty()) {
            id = nextId(context);
            setId(entity, id);
        }
        fileHandler.appendLine(resolvePath(context), serialize(entity));
        return id;
    }

    public boolean update(ServletContext context, T entity) {
        String id = getId(entity);
        if (id == null || id.trim().isEmpty()) {
            return false;
        }
        return fileHandler.updateLine(resolvePath(context), id, serialize(entity));
    }

    public boolean delete(ServletContext context, String id) {
        return fileHandler.deleteLine(resolvePath(context), id);
    }

    protected String nextId(ServletContext context) {
        int max = 0;
        for (T entity : findAll(context)) {
            String id = getId(entity);
            if (id != null && id.startsWith(getIdPrefix())) {
                String numeric = id.replace(getIdPrefix(), "");
                try {
                    max = Math.max(max, Integer.parseInt(numeric));
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return getIdPrefix() + (max + 1);
    }

    protected String resolvePath(ServletContext context) {
        if (context != null) {
            String realPath = context.getRealPath("/WEB-INF/data/" + getFileName());
            if (realPath != null) {
                return realPath;
            }
        }
        return new File("src" + File.separator + "data" + File.separator + getFileName()).getAbsolutePath();
    }
}