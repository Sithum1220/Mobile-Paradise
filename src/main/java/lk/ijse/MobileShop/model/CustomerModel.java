package lk.ijse.MobileShop.model;

import lk.ijse.MobileShop.dto.Customer;
import lk.ijse.MobileShop.dto.Employee;
import lk.ijse.MobileShop.utill.CrudUtil;
import lk.ijse.MobileShop.utill.DateTimeUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {
    public static boolean add(Customer customer) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("insert into customer VALUES (?,?,?,?,?,?,?,?,?,?)",
                customer.getCustomer_id(),
                customer.getFirst_name(),
                customer.getLast_name(),
                customer.getStreet(),
                customer.getCity(),
                customer.getLane(),
                customer.getDate(),
                customer.getNic(),
                customer.getEmail(),
                customer.getContact_number()
        );
    }

    public static ArrayList<String> getAllId() throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.crudUtil("SELECT customer_id FROM customer ORDER BY LENGTH(customer_id),customer_id");
        ArrayList<String> list = new ArrayList<>();

        while (set.next()) {
            list.add(set.getString(1));
        }
        return list;
    }

    public static ArrayList<String> getSearchId(String id) throws SQLException, ClassNotFoundException {
        String SearchId = id + "%";
        ResultSet set = CrudUtil.crudUtil("SELECT customer_id FROM customer where customer.customer_id LIKE ? or first_name LIKE ? or last_name LIKE ?", SearchId, SearchId, SearchId);
        ArrayList<String> list = new ArrayList<>();
        while (set.next()) {
            list.add(set.getString(1));
        }
        return list;
    }

    public static Customer getData(String id) throws SQLException, ClassNotFoundException {
        System.out.println(id);
        ResultSet set = CrudUtil.crudUtil("SELECT * FROM customer where customer_id=?", id);
        Customer customer = new Customer();
        if (set.next()) {
            customer.setCustomer_id(set.getString(1));
            customer.setFirst_name(set.getString(2));
            customer.setLast_name(set.getString(3));
            customer.setStreet(set.getString(4));
            customer.setCity(set.getString(5));
            customer.setLane(set.getString(6));
            customer.setDate(set.getString(7));
            customer.setNic(set.getString(8));
            customer.setEmail(set.getString(9));
            customer.setContact_number(set.getString(10));
        }
        return customer;
    }

    public static ResultSet getAllCustomerCount() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT COUNT(customer_id) from customer");
    }

    public static ArrayList<String> getItemIds() throws SQLException, ClassNotFoundException {
        ArrayList<String>ids=new ArrayList<>();
        ResultSet set=CrudUtil.crudUtil("SELECT customer_id FROM customer ");
        while (set.next()){
            ids.add(set.getString(1));
        }
        return ids;
    }

    public static double getOrders(int  i,String month) throws SQLException, ClassNotFoundException {
        String dateNow = DateTimeUtil.dateNow();
        String[] split = dateNow.split("-");

        String processMonth=null;
        if (month.length()==2){
            processMonth=month;
        }else {
            processMonth="0"+month;
        }

        String processDate=null;
        if (String.valueOf(i).length() ==2){
            processDate= String.valueOf(i);
        }else {
            processDate="0"+String.valueOf(i);
        }

        //System.out.println(split[0]+"-"+processMonth+"-"+processDate);
        ResultSet set=  CrudUtil.crudUtil("SELECT  payment FROM customer_order WHERE date LIKE ?", split[0]+"-"+processMonth+"-"+processDate);

        while (set.next()){
            return Double.parseDouble(set.getString(1));
        }
        return 0;
    }
    public static boolean Remove(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE FROM customer WHERE customer_id=?",id);
    }
    public static boolean update(Customer customer) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE  customer SET " +
                        "first_name=?," +
                        "last_name=?," +
                        "street=? ," +
                        "city=? ," +
                        "lane=? ," +
                        "nic=?," +
                        "email=? " +
                        "WHERE customer_id=?",
                customer.getFirst_name(),
                customer.getLast_name(),
                customer.getStreet(),
                customer.getCity(),
                customer.getLane(),
                customer.getNic(),
                customer.getEmail(),
                customer.getCustomer_id()
        );
    }
}
