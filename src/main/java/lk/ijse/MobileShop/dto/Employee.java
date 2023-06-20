package lk.ijse.MobileShop.dto;

public class Employee {

    private String employee_id;
    private String first_name;
    private String last_name;
    private String street;
    private String city;
    private String lane;
    private String role;
    private String date;
    private String nic;
    private String email;
    private String contact_number;

    public Employee() {
    }

    public Employee(String employee_id, String first_name, String last_name, String street, String city, String lane, String role, String date, String nic, String email, String contact_number) {
        this.employee_id = employee_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.street = street;
        this.city = city;
        this.lane = lane;
        this.role = role;
        this.date = date;
        this.nic = nic;
        this.email = email;
        this.contact_number = contact_number;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLane() {
        return lane;
    }

    public void setLane(String lane) {
        this.lane = lane;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employee_id='" + employee_id + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", lane='" + lane + '\'' +
                ", role='" + role + '\'' +
                ", date='" + date + '\'' +
                ", nic='" + nic + '\'' +
                ", email='" + email + '\'' +
                ", contact_number='" + contact_number + '\'' +
                '}';
    }
}
