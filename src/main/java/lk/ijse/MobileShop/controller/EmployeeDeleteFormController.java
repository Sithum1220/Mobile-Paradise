package lk.ijse.MobileShop.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import lk.ijse.MobileShop.dto.Employee;
import lk.ijse.MobileShop.dto.User;
import lk.ijse.MobileShop.model.EmployeeModel;
import lk.ijse.MobileShop.model.UserModel;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployeeDeleteFormController implements Initializable {
    public Text txtId;
    public Text txtName;
    public Text txtSteet;
    public Text txtLane;
    public Text txtCity;
    public Text txtRole;
    public Text txtDate;
    public Text txtNIC;
    public Text txtEmail;
    public Text txtContactNumber;
    public Text lblUserName;
    public Text txtUserName;
    public Text lblPassword;
    public Text txtPassword;
    public JFXButton btnComform;
    public JFXButton btncancel;
    private static String id=null;
    public void ComformOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ResultSet set = EmployeeModel.checkAdminCount();
        int count = -1;
        if (set.next()) {
            count = set.getInt(1);
        }
        if (txtRole.getText().equals("Admin")){
            if (count > 1) {
                EmployeeModel.Remove(id);
                UserModel.Remove(id);
                EmployeeFormController.getInstance().loadAllIds();
                EmployeeFormController.getInstance().setCount();
            }else {
                new Alert(Alert.AlertType.ERROR,"Only One Admin").show();
            }
        }else {
            EmployeeModel.Remove(id);
            EmployeeFormController.getInstance().loadAllIds();
            EmployeeFormController.getInstance().setCount();
        }
        EmployeeFormController.getInstance().tempPane.getChildren().clear();
        EmployeeFormController.getInstance().tempPane.setTranslateX(-380);
        EmployeeFormController.getInstance().closePane.setTranslateX(-390);
        EmployeeFormController.getInstance().optionLbl.setText("New");
        EmployeeFormController.getInstance().newIcon.setVisible(true);
        EmployeeFormController.getInstance().deleteIcon.setVisible(false);
    }

    public void CloseOnAction(ActionEvent actionEvent) {
        EmployeeFormController.getInstance().tempPane.getChildren().clear();
        EmployeeFormController.getInstance().tempPane.setTranslateX(-380);
        EmployeeFormController.getInstance().closePane.setTranslateX(-390);
        EmployeeFormController.getInstance().optionLbl.setText("New");
        EmployeeFormController.getInstance().newIcon.setVisible(true);
        EmployeeFormController.getInstance().deleteIcon.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allData();
    }
    public static void setId (String id){
        EmployeeDeleteFormController.id=id;
    }
    public void allData() {
        try {

            Employee employee = EmployeeModel.getData(id);
            User user = UserModel.getData(id);
            System.out.println(employee.getEmployee_id());
            txtId.setText(employee.getEmployee_id());
            txtName.setText(employee.getFirst_name()+" "+employee.getLast_name());
            txtSteet.setText(employee.getStreet());
            txtCity.setText(employee.getCity());
            txtLane.setText(employee.getLane());
            txtContactNumber.setText(employee.getContact_number());
            txtNIC.setText(employee.getNic());
            txtEmail.setText(employee.getEmail());
            txtRole.setText(employee.getRole());
            txtDate.setText(employee.getDate());

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
