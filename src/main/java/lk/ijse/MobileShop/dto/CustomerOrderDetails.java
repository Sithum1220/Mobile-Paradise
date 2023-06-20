package lk.ijse.MobileShop.dto;

public class CustomerOrderDetails {
    private String item_id;
    private String customer_order_id;
    private String qty;
    private String price;

    public CustomerOrderDetails() {
    }

    public CustomerOrderDetails(String item_id, String customer_order_id, String qty, String price) {
        this.item_id = item_id;
        this.customer_order_id = customer_order_id;
        this.qty = qty;
        this.price = price;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getCustomer_order_id() {
        return customer_order_id;
    }

    public void setCustomer_order_id(String customer_order_id) {
        this.customer_order_id = customer_order_id;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CustomerOrderDetails{" +
                "item_id='" + item_id + '\'' +
                ", customer_order_id='" + customer_order_id + '\'' +
                ", qty='" + qty + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
