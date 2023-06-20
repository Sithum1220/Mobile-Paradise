package lk.ijse.MobileShop.controller;

import javafx.scene.text.Text;
import lk.ijse.MobileShop.dto.Salary;
import lk.ijse.MobileShop.model.EmployeeSalaryModel;

import java.sql.SQLException;

public class SalaryBarFormController {
    public Text txtEmployeeId;
    public Text txtSalaryId;
    public Text txtTime;
    public Text txtDate;
    public Text txtAttendance;
    public Text txtSalary;

    public void setData(String id) {
        try {
            Salary salary = EmployeeSalaryModel.getData(id);
            txtEmployeeId.setText(salary.getEmployee_id());
            txtSalaryId.setText(salary.getSalary_id());
            txtTime.setText(salary.getTime());
            txtDate.setText(salary.getDate());
            txtAttendance.setText(salary.getMonthly_Attendance_count());
            txtSalary.setText(salary.getSalary());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
