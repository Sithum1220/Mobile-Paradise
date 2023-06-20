package lk.ijse.MobileShop.dto;

public class User {

    private String employee_id;
    private String user_name;
    private String password;
    private String role;

    public User(String employee_id, String user_name, String password, String role) {
        this.employee_id = employee_id;
        this.user_name = user_name;
        this.password = password;
        this.role = role;
    }

    public User() {
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
