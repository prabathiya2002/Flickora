package service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
//amanda//
import model.Admin;
import model.Customer;
import model.User;

public class UserService extends BaseTextService<User> {

    @Override
    protected String getFileName() {
        return "users.txt";
    }

    @Override
    protected String getId(User entity) {
        return entity.getId();
    }

    @Override
    protected void setId(User entity, String id) {
        entity.setId(id);
    }

    @Override
    protected String serialize(User entity) {
        return entity.getId() + "|" + safe(entity.getName()) + "|" + safe(entity.getEmail()) + "|" + safe(entity.getUsername()) + "|" + safe(entity.getPassword()) + "|" + safe(entity.getRole());
    }

    @Override
    protected User deserialize(String line) {
        String[] values = line.split("\\|", -1);
        if (values.length < 6) {
            return null;
        }
        String role = values[5];
        if ("Admin".equalsIgnoreCase(role)) {
            return new Admin(values[0], values[1], values[2], values[3], values[4]);
        }
        if ("Customer".equalsIgnoreCase(role)) {
            return new Customer(values[0], values[1], values[2], values[3], values[4]);
        }
        return new User(values[0], values[1], values[2], values[3], values[4], role);
    }

    @Override
    protected String getIdPrefix() {
        return "U";
    }

    public User login(ServletContext context, String username, String password) {
        for (User user : findAll(context)) {
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public List<User> findByRole(ServletContext context, String role) {
        List<User> filtered = new ArrayList<User>();
        for (User user : findAll(context)) {
            if (user.getRole() != null && user.getRole().equalsIgnoreCase(role)) {
                filtered.add(user);
            }
        }
        return filtered;
    }

    public User findByUsername(ServletContext context, String username) {
        for (User user : findAll(context)) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    public String registerCustomer(ServletContext context, Customer customer) {
        customer.setRole("Customer");
        return add(context, customer);
    }

    private String safe(String value) {
        return value == null ? "" : value.replace("|", "/");
    }
}