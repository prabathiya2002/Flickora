package model;

public class Customer extends User {
    public Customer() {
        setRole("Customer");
    }

    public Customer(String id, String name, String email, String username, String password) {
        super(id, name, email, username, password, "Customer");
    }

    @Override
    public String getAccountType() {
        return "Customer";
    }
}