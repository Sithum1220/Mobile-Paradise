package lk.ijse.MobileShop.model;

import lk.ijse.MobileShop.db.DBConnection;
import lk.ijse.MobileShop.dto.Customer;
import lk.ijse.MobileShop.dto.CustomerOrderDetails;
import lk.ijse.MobileShop.dto.Order;
import lk.ijse.MobileShop.utill.CrudUtil;
import lk.ijse.MobileShop.utill.DateTimeUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderModel {
    public static ArrayList<String> getAllId() throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.crudUtil("SELECT customer_order_id FROM customer_order ORDER BY LENGTH(customer_order_id),customer_order_id");
        ArrayList<String> list = new ArrayList<>();

        while (set.next()) {
            list.add(set.getString(1));
        }
        return list;
    }

    public static Order getData(String id) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.crudUtil("SELECT * FROM customer_order where customer_order_id=?", id);
        Order order = new Order();
        if (set.next()) {
            order.setCustomer_order_id(set.getString(1));
            order.setCustomer_id(set.getString(2));
            order.setTime(set.getString(3));
            order.setDate(set.getString(4));
            order.setPayment(set.getString(5));
        }
        return order;
    }

    public static Order getDataView(String id) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.crudUtil("SELECT o.customer_order_id,c.first_name,c.last_name,o.time,o.date,o.payment FROM customer_order o join customer c on c.customer_id = o.customer_id where customer_order_id=?", id);
        Order order = new Order();
        if (set.next()) {
            order.setCustomer_order_id(set.getString(1));
            order.setCustomer_id(set.getString(2) + " " + set.getString(3));
            order.setTime(set.getString(4));
            order.setDate(set.getString(5));
            order.setPayment(set.getString(6));
        }
        return order;
    }

    public static boolean placeOder(ArrayList<CustomerOrderDetails> list, Order order) throws SQLException {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            if (OrderModel.addOrder(order)) {
                if (OrderDetailsModel.addDetails(list)) {
                    if (ItemModel.updateItem(list, order)) {
                        return true;
                    } else {
                        connection.rollback();
                        return false;
                    }
                } else {
                    connection.rollback();
                    return false;
                }
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            connection.commit();
            connection.setAutoCommit(true);
        }

    }

    private static boolean addOrder(Order order) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO customer_order VALUES (?,?,?,?,?,?)",
                order.getCustomer_order_id(),
                order.getCustomer_id(),
                order.getTime(),
                order.getDate(),
                order.getPayment(),
                null
        );
    }

    public static ArrayList<String> getSearchId(String id) throws SQLException, ClassNotFoundException {
        String SearchId = id + "%";
        ResultSet set = CrudUtil.crudUtil("SELECT o.customer_order_id FROM customer_order o join customer c on c.customer_id = o.customer_id where customer_order_id LIKE ? or c.first_name LIKE ? or o.customer_id LIKE ? ", SearchId, SearchId, SearchId);
        ArrayList<String> list = new ArrayList<>();
        while (set.next()) {
            list.add(set.getString(1));
        }
        return list;
    }

    public static boolean add(Order order) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO customer_order VALUES (?,?,?,?,?,?)",
                order.getCustomer_order_id(),
                order.getCustomer_id(),
                order.getTime(),
                order.getDate(),
                order.getPayment(),
                order.getRepair_id()
        );
    }

    public static boolean placeAdd(Order order) throws SQLException {
        System.out.println(order);
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            if (add(order)) {
                System.out.println("Order Ok");
                if (RepairModel.updateDate(order)) {
                    System.out.println("update");
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                }
            } else {
                connection.rollback();
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
        }
        return false;
    }

    public static ArrayList<String> getMonthlyAllId() throws SQLException, ClassNotFoundException {
        String date = DateTimeUtil.dateNow();
        String[] dateSplit = date.split("-");
        ResultSet set = CrudUtil.crudUtil("SELECT customer_order_id FROM customer_order WHERE date LIKE ? ORDER BY LENGTH(customer_order_id),customer_order_id ",dateSplit[0]+"-"+dateSplit[1]+"-%");
        ArrayList<String> list = new ArrayList<>();

        while (set.next()) {
            list.add(set.getString(1));
        }
        return list;
    }

    public static ResultSet getCount() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT COUNT(customer_order_id) FROM customer_order WHERE date=?", DateTimeUtil.dateNow());
    }
}
