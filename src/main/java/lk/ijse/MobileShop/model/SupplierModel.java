package lk.ijse.MobileShop.model;

import lk.ijse.MobileShop.dto.Supplier;
import lk.ijse.MobileShop.utill.CrudUtil;
import lk.ijse.MobileShop.utill.DateTimeUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierModel {
    public static Supplier getData(String id) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.crudUtil("SELECT * FROM supplier where supplier_id=?", id);
        Supplier supplier=new Supplier();
        if (set.next()) {
            supplier.setSupplier_id(set.getString(1));
            supplier.setCompany_name(set.getString(2));
            supplier.setContact_number(set.getString(3));
            supplier.setEmail(set.getString(4));
            supplier.setLocation(set.getString(5));
        }
        return supplier;
    }

    public static ArrayList<String> getAllId() throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.crudUtil("SELECT supplier_id FROM supplier ORDER BY LENGTH(supplier_id),supplier_id");
        ArrayList<String> list = new ArrayList<>();

        while (set.next()) {
            list.add(set.getString(1));
        }
        return list;
    }

    public static ArrayList<String> getSearchId(String text) throws SQLException, ClassNotFoundException {
        String SearchId = text + "%";
        ResultSet set = CrudUtil.crudUtil("SELECT supplier_id FROM supplier where supplier.supplier_id LIKE ? or supplier.company_name LIKE ? or supplier.location LIKE ?", SearchId, SearchId, SearchId);
        ArrayList<String> list = new ArrayList<>();
        while (set.next()) {
            list.add(set.getString(1));
        }
        return list;
    }

    public static boolean add(Supplier supplier) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO supplier VALUES (?,?,?,?,?)",
                supplier.getSupplier_id(),
                supplier.getCompany_name(),
                supplier.getContact_number(),
                supplier.getEmail(),
                supplier.getLocation()
                );
    }

    public static boolean update(Supplier supplier) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE supplier SET company_name=? , contact_number=? ,email=? ,location=? WHERE supplier_id=?",
                supplier.getCompany_name(),
                supplier.getContact_number(),
                supplier.getEmail(),
                supplier.getLocation(),
                supplier.getSupplier_id()

                );
    }
    public static ResultSet getAllSupplierCount() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT COUNT(supplier_id) FROM supplier");
    }

    public static boolean remove(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE FROM supplier WHERE supplier_id=?",id);
    }

    public static double getOrders(String processDate,String month) throws SQLException, ClassNotFoundException {
        String dateNow = DateTimeUtil.dateNow();
        String[] split = dateNow.split("-");

        String processMonth=null;
        if (month.length()==2){
            processMonth=month;
        }else {
            processMonth="0"+month;
        }

        ResultSet set=  CrudUtil.crudUtil("SELECT  payment FROM supplier_order WHERE date LIKE ?", split[0]+"-"+processMonth+"-"+processDate);

        while (set.next()){
            return Double.parseDouble(set.getString(1));
        }
        return 0;
    }

    public static ArrayList<String> getYear() throws SQLException, ClassNotFoundException {
        ArrayList<String> year=new ArrayList<>();
        ResultSet set=CrudUtil.crudUtil("SELECT date from supplier_order");
        while (set.next()){
            String dateNow = set.getString(1);
            String[] split = dateNow.split("-");
            String date=split[0];
            boolean isNotDuplicate=true;
            for (int i = 0; i < year.size(); i++) {

                if (year.get(i).equals(date)){
                    isNotDuplicate=false;
                }
            }
            if (isNotDuplicate){
                year.add(date);
            }
        }
        return year;
    }
}

