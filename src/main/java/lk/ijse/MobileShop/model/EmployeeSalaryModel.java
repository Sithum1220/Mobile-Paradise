package lk.ijse.MobileShop.model;

import lk.ijse.MobileShop.dto.Salary;
import lk.ijse.MobileShop.utill.CrudUtil;
import lk.ijse.MobileShop.utill.DateTimeUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeSalaryModel {
    public static ArrayList<String> getAllEmpId() throws SQLException, ClassNotFoundException {
        ArrayList<String>ids=new ArrayList<>();
        String date=DateTimeUtil.dateNow();
        String[] arDate = date.split("-");
        ResultSet set= CrudUtil.crudUtil("SELECT employee_id FROM salary WHERE date LIKE ?",arDate[0]+"-"+arDate[1]+"%");
        while (set.next()){
            ids.add(set.getString(1));
        }
        return ids;
    }



    public static ArrayList<String> getAllId() throws SQLException, ClassNotFoundException {
        ArrayList<String>ids=new ArrayList<>();
        String date=DateTimeUtil.dateNow();
        String[] arDate = date.split("-");
        ResultSet set= CrudUtil.crudUtil("SELECT salary_id FROM salary WHERE date LIKE ?",arDate[0]+"-"+arDate[1]+"%");
        while (set.next()){
            ids.add(set.getString(1));
        }
        return ids;
    }

    public static boolean add(Salary salary) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO salary VALUES (?,?,?,?,?,?)",
                salary.getEmployee_id(),
                salary.getSalary_id(),
                salary.getSalary(),
                salary.getMonthly_Attendance_count(),
                salary.getDate(),
                salary.getTime()
                );
    }

    public static String getCountOfMonthlySalaryPayment() throws SQLException, ClassNotFoundException {
        String dateToDay = DateTimeUtil.dateNow();
        String[] d = dateToDay.split("-");
       ResultSet set= CrudUtil.crudUtil("SELECT COUNT(salary_id) FROM salary WHERE date LIKE ?",d[0]+"-"+d[1]+"-%");
        while (set.next()){
            return set.getString(1);
        }
        return null;
    }
    public static Salary getData(String id) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.crudUtil("SELECT * FROM salary where salary_id=?", id);
        Salary salary = new Salary();
        while (set.next()) {
            salary.setSalary_id(set.getString(2));
            salary.setEmployee_id(set.getString(1));
            salary.setSalary(set.getString(3));
            salary.setMonthly_Attendance_count(set.getString(4));
            salary.setDate(set.getString(5));
            salary.setTime(set.getString(6));
        }
        return salary;
    }
}
