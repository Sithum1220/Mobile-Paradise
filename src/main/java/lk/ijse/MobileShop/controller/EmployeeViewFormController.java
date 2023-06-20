package lk.ijse.MobileShop.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import lk.ijse.MobileShop.dto.Employee;
import lk.ijse.MobileShop.dto.User;
import lk.ijse.MobileShop.model.EmployeeModel;
import lk.ijse.MobileShop.model.UserModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployeeViewFormController implements Initializable {
    private static String id = null;
    public JFXButton btncancel;
    public Text txtPassword;
    public Text lblPassword;
    public Text txtUserName;
    public Text lblUserName;
    public Text txtContactNumber;
    public Text txtEmail;
    public Text txtNIC;
    public Text txtDate;
    public Text txtRole;
    public Text txtCity;
    public Text txtLane;
    public Text txtSteet;
    public Text txtName;
    public Text txtId;

    public static void setId(String id) {
        EmployeeViewFormController.id = id;
    }

    public void CloseOnAction(ActionEvent actionEvent) {
        EmployeeFormController.getInstance().deleteIcon.setVisible(false);
        EmployeeFormController.getInstance().newIcon.setVisible(true);
        EmployeeFormController.getInstance().detailsIcon.setVisible(false);
        EmployeeFormController.getInstance().optionLbl.setText("New");
        EmployeeFormController.getInstance().tempPane.getChildren().clear();
        EmployeeFormController.getInstance().tempPane.setTranslateX(-380);
        EmployeeFormController.getInstance().closePane.setTranslateX(-390);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setData();
    }

    private void setData() {
        try {

            Employee employee = EmployeeModel.getData(id);
            User user = UserModel.getData(id);
            txtName.setText(employee.getFirst_name() + " " + employee.getLast_name());
            txtId.setText(employee.getEmployee_id());
            txtSteet.setText(employee.getStreet());
            txtLane.setText(employee.getLane());
            txtCity.setText(employee.getCity());
            txtEmail.setText(employee.getEmail());
            txtNIC.setText(employee.getNic());
            txtContactNumber.setText(employee.getContact_number());
            txtRole.setText(employee.getRole());
            if (txtRole.getText().equals("Admin") || txtRole.getText().equals("Cashier")) {
                lblUserName.setVisible(true);
                lblPassword.setVisible(true);
                txtUserName.setVisible(true);
                txtPassword.setVisible(true);
                txtUserName.setText(user.getUser_name());
                txtPassword.setText(user.getPassword());
            } else {
                lblUserName.setVisible(false);
                lblPassword.setVisible(false);
                txtUserName.setVisible(false);
                txtPassword.setVisible(false);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
