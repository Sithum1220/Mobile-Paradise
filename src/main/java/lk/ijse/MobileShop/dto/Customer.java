package lk.ijse.MobileShop.dto;

public class Customer {
    private String customer_id;
    private String first_name;
    private String last_name;
    private String street;
    private String city;
    private String lane;
    private String date;
    private String nic;
    private String email;
    private String contact_number;

    public Customer(String customer_id, String first_name, String last_name, String street, String city, String lane, String date, String nic, String email, String contact_number) {
        this.customer_id = customer_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.street = street;
        this.city = city;
        this.lane = lane;
        this.date = date;
        this.nic = nic;
        this.email = email;
        this.contact_number = contact_number;
    }

    public Customer() {
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
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
        return "Customer{" + "customer_id='" + customer_id + '\'' + ", first_name='" + first_name + '\'' + ", last_name='" + last_name + '\'' + ", street='" + street + '\'' + ", city='" + city + '\'' + ", lane='" + lane + '\'' + ", date='" + date + '\'' + ", nic='" + nic + '\'' + ", email='" + email + '\'' + ", contact_number='" + contact_number + '\'' + '}';
    }
}
