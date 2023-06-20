package lk.ijse.MobileShop.model;

import lk.ijse.MobileShop.dto.EmployeeAttendance;
import lk.ijse.MobileShop.utill.CrudUtil;
import lk.ijse.MobileShop.utill.DateTimeUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeAttendanceModel {
    public static ArrayList<String> getAllId() throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.crudUtil("SELECT employee_id FROM employee_attendance WHERE date=?", DateTimeUtil.dateNow());
        ArrayList<String> list = new ArrayList<>();

        while (set.next()) {
            list.add(set.getString(1));
        }
        return list;
    }

    public static ArrayList<String> getSearchId(String text) throws SQLException, ClassNotFoundException {
        String SearchId = text + "%";
        ResultSet set = CrudUtil.crudUtil("SELECT employee_id FROM employee_attendance where employee_id LIKE ? AND date = ? ", SearchId,DateTimeUtil.dateNow());
        ArrayList<String> list = new ArrayList<>();
        while (set.next()) {
            list.add(set.getString(1));
        }
        return list;
    }

    public static ResultSet getCount() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT COUNT(employee_id) FROM employee_attendance WHERE date=?", DateTimeUtil.dateNow());
    }

    public static boolean isCustomerExsist(String id) throws SQLException, ClassNotFoundException {
        ResultSet set=CrudUtil.crudUtil("SELECT * FROM employee WHERE employee_id=?", id) ;
        if (set.next()){
            return true;
        }
        return false;
    }

    public static boolean isCustomerTodayExsist(String id) throws SQLException, ClassNotFoundException {
        ResultSet set=CrudUtil.crudUtil("SELECT * FROM employee_attendance WHERE employee_id=? and date=?", id, DateTimeUtil.dateNow());
        if (set.next()){
            return false;
        }
        return true;
    }

    public static boolean add(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO employee_attendance VALUES (?,?,?)",
                id,
                DateTimeUtil.timeNow(),
                DateTimeUtil.dateNow()
        );
    }

    public static EmployeeAttendance get(String id) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.crudUtil("SELECT e.first_name,e.last_name , a.employee_id ,a.time,a.date FROM employee_attendance a INNER JOIN  mobileparadise.employee e on a.employee_id = e.employee_id WHERE a.employee_id=?",id);
        EmployeeAttendance attendance = null;
        if (set.next()){
            attendance= new EmployeeAttendance(
                    set.getString(3),
                    set.getString(1) + " " + set.getString(2),
                    set.getString(4),
                    set.getString(5)

            );
        }

        return attendance;
    }

    public static String getEmloyeeCount(String id) throws SQLException, ClassNotFoundException {
        String date=DateTimeUtil.dateNow();
        String[] arDate = date.split("-");
      ResultSet  set=  CrudUtil.crudUtil("SELECT COUNT(employee_id) FROM employee_attendance WHERE employee_id=? AND date LIKE ?",id,arDate[0]+"-"+arDate[1]+"%");
      if (set.next()){
          return set.getString(1);
      }
      return null;
    }

    public static String getAttendanceCountThisMonth(String employee_id) throws SQLException, ClassNotFoundException {
        String dateNow = DateTimeUtil.dateNow();
        String[] date = dateNow.split("-");
        ResultSet set=CrudUtil.crudUtil("SELECT COUNT(employee_id) FROM employee_attendance WHERE employee_id=? and date LIKE ?",employee_id,date[0]+"-"+date[1]+"-%");

        while (set.next()){
            System.out.println(set.getString(1));
            return set.getString(1);
        }
        return"0";
    }
    public static ResultSet getAllTodayAttendance() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT COUNT(employee_id) FROM employee_attendance WHERE date=?",DateTimeUtil.dateNow());
    }
}

