package lk.ijse.MobileShop.model;

import lk.ijse.MobileShop.dto.SuplierOrderDetails;
import lk.ijse.MobileShop.dto.Supplier;
import lk.ijse.MobileShop.utill.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierOrderDetailsModel {

    public static SuplierOrderDetails getData(String id) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.crudUtil("SELECT * FROM supplier_order_details where supplier_order_id=?", id);
        SuplierOrderDetails supplier=new SuplierOrderDetails();
        while (set.next()) {
            supplier.setItem_code(set.getString(1));
            supplier.setSupplier_order_id(set.getString(2));
            supplier.setQuantity(set.getString(3));
            supplier.setUnit_price(set.getString(4));
        }
        return supplier;
    }
}
