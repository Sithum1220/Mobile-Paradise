package lk.ijse.MobileShop.model;

import lk.ijse.MobileShop.dto.Order;
import lk.ijse.MobileShop.dto.Repair;
import lk.ijse.MobileShop.utill.CrudUtil;
import lk.ijse.MobileShop.utill.DateTimeUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RepairModel {
    public static ArrayList<String> getAllId() throws SQLException, ClassNotFoundException {
        ArrayList<String>ids=new ArrayList<>();
        ResultSet set= CrudUtil.crudUtil("SELECT repair_id FROM repair ORDER BY LENGTH(repair_id),repair_id");
        while (set.next()){
            ids.add(set.getString(1));
        }
        return ids;
    }

    public static boolean add(Repair repair) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO repair VALUES (?,?,?,?,?,?,?,?)",
                repair.getRepair_id(),
                repair.getCustomer_id(),
                repair.getStatus(),
                repair.getImei_number(),
                repair.getModel_number(),
                repair.getError_type(),
                repair.getGiven_date(),
                repair.getReturn_give_date()
                );
    }

    public static Repair get(String id) throws SQLException, ClassNotFoundException {
        ResultSet set=CrudUtil.crudUtil("SELECT * FROM repair WHERE repair_id=?",id);
        Repair repair=new Repair();
        while (set.next()){
            repair.setRepair_id(set.getString(1));
            repair.setCustomer_id(set.getString(2));
            repair.setStatus(set.getString(3));
            repair.setImei_number(set.getString(4));
            repair.setModel_number(set.getString(5));
            repair.setError_type(set.getString(6));
            repair.setGiven_date(set.getString(7));
            repair.setReturn_give_date(set.getString(8));

        }
        return repair;
    }

    public static boolean updateDate(Order order) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE repair SET return_give_date=? WHERE repair_id=?",
                DateTimeUtil.dateNow(),
                order.getRepair_id()
        );
    }

    public static boolean updateDate(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE repair SET  return_give_date=? WHERE repair_id=?",DateTimeUtil.dateNow(),id);
    }

    public static String getRepDate(String id) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.crudUtil("SELECT return_give_date FROM repair WHERE repair_id=?", id);
        while (set.next()){
            return set.getString(1);
        }
        return null;
    }

    public static String getStatus(String id) throws SQLException, ClassNotFoundException {
      ResultSet set=  CrudUtil.crudUtil("SELECT status FROM repair WHERE repair_id=?",id);
      while (set.next()){
          return set.getString(1);
      }
          return null;
    }
    public static ArrayList<String> getSearchId(String text) throws SQLException, ClassNotFoundException {
        String SearchId = text + "%";
        ResultSet set = CrudUtil.crudUtil("SELECT repair_id FROM repair where repair_id LIKE ? or status LIKE ? or model_number LIKE ?", SearchId, SearchId, SearchId);
        ArrayList<String> list = new ArrayList<>();
        while (set.next()) {
            list.add(set.getString(1));
        }
        return list;
    }
    public static boolean Remove(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE FROM repair WHERE repair_id=?",id);
    }
    public static boolean checkReturnDate() throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.crudUtil("SELECT return_give_date FROM repair");
        while (set.next()){
            return true;
        }
        return false;
    }
}
