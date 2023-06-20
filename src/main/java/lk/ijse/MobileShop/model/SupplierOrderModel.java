package lk.ijse.MobileShop.model;

import lk.ijse.MobileShop.db.DBConnection;
import lk.ijse.MobileShop.dto.ItemTm;
import lk.ijse.MobileShop.dto.SupplierOrder;
import lk.ijse.MobileShop.utill.CrudUtil;
import lk.ijse.MobileShop.utill.DateTimeUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierOrderModel {
    public static boolean pleaseOrder(ArrayList<ItemTm> list, SupplierOrder order) throws SQLException {
        Connection connection=null;
        try {
            connection= DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            if (SupplierOrderModel.add(order)){
                if (SupplierOrderModel.setDetails(list,order)){
                    if (ItemModel.updateData(list)){
                        return true;
                    }else {
                        connection.rollback();
                        return false;
                    }
                }else {
                    connection.rollback();
                }
            }else {
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }finally {
            connection.commit();
            connection.setAutoCommit(true);

        }
        return false;
    }

    private static boolean setDetails(ArrayList<ItemTm> list, SupplierOrder order) throws SQLException, ClassNotFoundException {
        for (int i = 0; i < list.size(); i++) {
            if (CrudUtil.crudUtil("INSERT INTO supplier_order_details VALUES (?,?,?,?)",
                    list.get(i).getId(),
                    order.getSupplier_order_id(),
                    list.get(i).getQty(),
                    list.get(i).getPrice()
            )){

            }else {
                return false;
            }
        }
        return true;
    }

    private static boolean add(SupplierOrder order) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO supplier_order VALUES (?,?,?,?,?)",
                order.getSupplier_order_id(),
                order.getSupplier_id(),
                order.getTime(),
                order.getDate(),
                order.getPayment()
        );
    }

    public static ArrayList<String> getAllId() throws SQLException, ClassNotFoundException {
        ArrayList<String>ids =new ArrayList<>();
       ResultSet set= CrudUtil.crudUtil("SELECT supplier_order_id FROM supplier_order ORDER BY LENGTH(supplier_order_id),supplier_order_id");
       while (set.next()){
           ids.add(set.getString(1));
       }
       return ids;
    }
    public static SupplierOrder getData(String id) throws SQLException, ClassNotFoundException {
        SupplierOrder supplierOrder = new SupplierOrder();
        ResultSet set = CrudUtil.crudUtil("SELECT * FROM supplier_order WHERE supplier_order_id = ?",id);
        while (set.next()){
            supplierOrder.setSupplier_id(set.getString(2));
            supplierOrder.setSupplier_order_id(set.getString(1));
            supplierOrder.setDate(set.getString(4));
            supplierOrder.setTime(set.getString(3));
            supplierOrder.setPayment(set.getString(5));
        }
        return supplierOrder;
    }
    public static ItemTm getSupplierOrderDetailsData(String id) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.crudUtil("SELECT unit_price FROM supplier_order_details where item_code=?",id);
        ItemTm itemTm = new ItemTm();
        while (set.next()){
            itemTm.setPrice(set.getString(1));
        }
        return itemTm;
    }
    public static ArrayList<String> getSearchId(String text) throws SQLException, ClassNotFoundException {
        String SearchId = text + "%";
        ResultSet set = CrudUtil.crudUtil("SELECT supplier_order_id FROM supplier_order where supplier_order_id LIKE ? or supplier_id LIKE ? ", SearchId, SearchId);
        ArrayList<String> list = new ArrayList<>();
        while (set.next()) {
            list.add(set.getString(1));
        }
        return list;
    }

    public static ResultSet getCount() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT COUNT(supplier_order_id) FROM supplier_order WHERE date=?", DateTimeUtil.dateNow());
    }
}
