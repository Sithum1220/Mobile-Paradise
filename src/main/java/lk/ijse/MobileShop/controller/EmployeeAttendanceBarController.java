package lk.ijse.MobileShop.controller;

import lk.ijse.MobileShop.dto.EmployeeAttendance;
import lk.ijse.MobileShop.dto.Item;
import lk.ijse.MobileShop.model.EmployeeAttendanceModel;
import lk.ijse.MobileShop.model.ItemModel;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class EmployeeAttendanceBarController {

    @FXML
    private Text txtId;

    @FXML
    private Text txtName;

    @FXML
    private Text txtTime;

    @FXML
    private Text txtDate;
    public void setData(String id){

        try {
            EmployeeAttendance attendance= EmployeeAttendanceModel.get(id);

            System.out.println(attendance.getId());
            System.out.println(attendance.getName());
            System.out.println(attendance.getTime());
            System.out.println(attendance.getDate());
            txtId.setText(attendance.getId());
            txtName.setText(attendance.getName());
            txtTime.setText(attendance.getTime());
            txtDate.setText(attendance.getDate());


        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
