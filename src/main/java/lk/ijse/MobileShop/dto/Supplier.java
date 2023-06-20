package lk.ijse.MobileShop.dto;

public class Supplier {
    private String supplier_id;
    private String company_name;
    private String contact_number;
    private String email;
    private String location;

    public Supplier() {
    }

    public Supplier(String supplier_id, String company_name, String contact_number, String email, String location) {
        this.supplier_id = supplier_id;
        this.company_name = company_name;
        this.contact_number = contact_number;
        this.email = email;
        this.location = location;
    }

    public String getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplier_id='" + supplier_id + '\'' +
                ", company_name='" + company_name + '\'' +
                ", contact_number='" + contact_number + '\'' +
                ", email='" + email + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
