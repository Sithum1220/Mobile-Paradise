package lk.ijse.MobileShop.model;

import lk.ijse.MobileShop.dto.CustomerOrderDetails;
import lk.ijse.MobileShop.dto.Item;
import lk.ijse.MobileShop.dto.ItemTm;
import lk.ijse.MobileShop.dto.Order;
import lk.ijse.MobileShop.utill.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemModel {
    public static Item getItem(String id) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.crudUtil("SELECT * FROM item where item_code=?", id);
        Item item = new Item();
        if (set.next()) {
            item.setItem_code(set.getString(1));
            item.setItem_name(set.getString(2));
            item.setCategory(set.getString(3));
            item.setUnit_price(Double.parseDouble(set.getString(4)));
            item.setQty(Integer.parseInt(set.getString(5)));
            item.setBrand(set.getString(6));
            item.setDescription(set.getString(7));
            item.setWarranty_Month(set.getString(8));
        }
        return item;
    }

    public static ArrayList<String> getItemIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> ids = new ArrayList<>();
        ResultSet set = CrudUtil.crudUtil("SELECT item_code FROM item");
        while (set.next()) {
            ids.add(set.getString(1));
        }
        return ids;
    }

    public static ArrayList<String> getSearchId(String id) throws SQLException, ClassNotFoundException {
        String SearchId = id + "%";
        ResultSet set = CrudUtil.crudUtil("SELECT item_code FROM item where item.item_code LIKE ? or item.category LIKE ? or item.item_name LIKE ? or brand LIKE ?", SearchId, SearchId, SearchId, SearchId);
        ArrayList<String> list = new ArrayList<>();
        while (set.next()) {
            list.add(set.getString(1));
        }
        return list;
    }

    public static boolean add(Item item) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO item VALUES (?,?,?,?,?,?,?,?)",
                item.getItem_code(),
                item.getItem_name(),
                item.getCategory(),
                item.getUnit_price(),
                item.getQty(),
                item.getBrand(),
                item.getDescription(),
                item.getWarranty_Month()
        );
    }

    public static boolean updateItem(ArrayList<CustomerOrderDetails> list, Order order) throws SQLException, ClassNotFoundException {
        boolean isAdded = false;
        for (int i = 0; i < list.size(); i++) {
            if (CrudUtil.crudUtil("UPDATE item set qty=qty-? WHERE item_code=?",
                    list.get(i).getQty(),
                    list.get(i).getItem_id()
            )) {
                isAdded = true;
            } else {
                isAdded = false;
            }
        }
        return isAdded;
    }

    public static boolean updateData(ArrayList<ItemTm> list) throws SQLException, ClassNotFoundException {
        for (int i = 0; i < list.size(); i++) {
            if (CrudUtil.crudUtil("UPDATE item SET unit_price=?,qty=qty+? WHERE item_code=?",
                    list.get(i).getSellingPrice(),
                    list.get(i).getQty(),
                    list.get(i).getId()
            )) {

            } else {
                return false;
            }
        }
        return true;
    }

    public static boolean update(Item item) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE item SET " +
                        "item_name=?," +
                        "category=?," +
                        "unit_price=? ," +
                        "qty=? ," +
                        "brand=? ," +
                        "description =?," +
                        "Warranty_month=?" +
                        "WHERE item_code=?",
                item.getItem_name(),
                item.getCategory(),
                item.getUnit_price(),
                item.getQty(),
                item.getBrand(),
                item.getDescription(),
                item.getWarranty_Month(),
                item.getItem_code()
        );
    }

    public static String getItemLimitCount() throws SQLException, ClassNotFoundException {
        ResultSet set=CrudUtil.crudUtil("SELECT COUNT(item_code) FROM item WHERE qty<5");
        if (set.next()){
            return set.getString(1);
        }
        return null;
    }
}
