package lk.ijse.MobileShop.model;

import lk.ijse.MobileShop.dto.CustomerOrderDetails;
import lk.ijse.MobileShop.utill.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsModel {
    public static boolean addDetails(ArrayList<CustomerOrderDetails> list) throws SQLException, ClassNotFoundException {
        boolean isAdded = false;
        for (int i = 0; i < list.size(); i++) {
            isAdded = CrudUtil.crudUtil("INSERT INTO customer_order_detail VALUES (?,?,?,?)",
                    list.get(i).getItem_id(),
                    list.get(i).getCustomer_order_id(),
                    list.get(i).getQty(),
                    list.get(i).getPrice()
            );
        }
        return isAdded;
    }

    public static String getSoldCountThis(String itemCode) throws SQLException, ClassNotFoundException {
//        ResultSet set=CrudUtil.crudUtil("");
        return null;
    }

    public static ArrayList<CustomerOrderDetails> get(String orderId) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.crudUtil("SELECT * FROM customer_order_detail WHERE customer_order_id=?",orderId);

        ArrayList<CustomerOrderDetails> customerOrderDetails = new ArrayList<>();

        CustomerOrderDetails details = new CustomerOrderDetails();

        while (set.next()) {
            details.setItem_id(set.getString(1));
            details.setCustomer_order_id(set.getString(2));
            details.setQty(set.getString(3));
            details.setPrice(set.getString(4));

            customerOrderDetails.add(details);
        }
        return customerOrderDetails;
    }
}
