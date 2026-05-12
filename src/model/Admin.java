package model;

public class Admin extends User {
    public Admin() {
        setRole("Admin");
    }

    public Admin(String id, String name, String email, String username, String password) {
        super(id, name, email, username, password, "Admin");
    }

    @Override
    public String getAccountType() {
        return "Admin";
    }
}