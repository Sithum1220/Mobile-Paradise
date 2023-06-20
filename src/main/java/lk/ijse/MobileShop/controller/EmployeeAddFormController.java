package lk.ijse.MobileShop.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import lk.ijse.MobileShop.db.DBConnection;
import lk.ijse.MobileShop.dto.Employee;
import lk.ijse.MobileShop.dto.User;
import lk.ijse.MobileShop.model.EmployeeModel;
import lk.ijse.MobileShop.model.UserModel;
import lk.ijse.MobileShop.utill.DateTimeUtil;
import lk.ijse.MobileShop.utill.RegexUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

public class EmployeeAddFormController implements Initializable {

    public JFXTextField txtFName;
    public JFXTextField txtLname;
    public JFXTextField txtStreet;
    public JFXTextField txtLane;
    public JFXTextField txtCity;
    public JFXTextField txtNIC;
    public JFXTextField txtEmail;
    public JFXTextField txtContactNumber;
    public JFXComboBox cmbRole;
    public JFXTextField txtUserName;
    public JFXTextField txtPassword;
    public JFXButton btnAdd;

    public void cmbRoleOnAction(ActionEvent actionEvent) {
        textFelfd();
    }

    public void addOnAction(ActionEvent actionEvent) {
        EmployeeFormController.getInstance().tempPane.getChildren().clear();
        EmployeeFormController.getInstance().tempPane.setTranslateX(-380);
        EmployeeFormController.getInstance().closePane.setTranslateX(-390);

        try {
            String id = id();
            if (getRole().equals("Admin") || getRole().equals("Cashier")) {
                boolean isEmpAdd = EmployeeModel.addEmaployee(
                        new Employee(
                                id,
                                txtFName.getText(),
                                txtLname.getText(),
                                txtStreet.getText(),
                                txtCity.getText(),
                                txtLane.getText(),
                                getRole(),
                                DateTimeUtil.dateNow(),
                                txtNIC.getText(),
                                txtEmail.getText(),
                                txtContactNumber.getText()
                        ));
                boolean isUserAdded = UserModel.addUser(
                        new User(
                                id,
                                txtUserName.getText(),
                                txtPassword.getText(),
                                getRole()
                        )
                );
                if (isUserAdded && isEmpAdd) {
                    EmployeeFormController.getInstance().loadAllIds();
                    try {
                        ResultSet set1 = EmployeeModel.getAllEmployeeCount();
                        if (set1.next()) {
                            EmployeeFormController.getInstance().txtallEmployee.setText(set1.getString(1));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    new Alert(Alert.AlertType.CONFIRMATION, "OK ADD").show();
                    checkJasper(id);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something Else ").show();
                }
            } else {
                if (EmployeeModel.addEmaployee(
                        new Employee(
                                id,
                                txtFName.getText(),
                                txtLname.getText(),
                                txtStreet.getText(),
                                txtCity.getText(),
                                txtLane.getText(),
                                getRole(),
                                DateTimeUtil.dateNow(),
                                txtNIC.getText(),
                                txtEmail.getText(),
                                txtContactNumber.getText()
                        ))) {
                    EmployeeFormController.getInstance().loadAllIds();
                    try {
                        ResultSet set1 = EmployeeModel.getAllEmployeeCount();
                        if (set1.next()) {
                            EmployeeFormController.getInstance().txtallEmployee.setText(set1.getString(1));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    new Alert(Alert.AlertType.CONFIRMATION, "OK ADD").show();
                    checkJasper(id);

//                    =============================================================================================
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something Else ").show();
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void textFelfd() {
        if (getRole().equals("Admin") || getRole().equals("Cashier")) {
            txtUserName.setVisible(true);
            txtPassword.setVisible(true);
        } else {
            txtUserName.setVisible(false);
            txtPassword.setVisible(false);
        }
    }

    private String id() throws SQLException, ClassNotFoundException {
        ArrayList<String> allId = EmployeeModel.getAllId();
        String lastId = null;
        for (int i = 0; i < allId.size(); i++) {
            lastId = allId.get(i);
            System.out.println(allId.get(i));
        }
        try {
            String[] e00s = lastId.split("E00");
            int idIndex = Integer.parseInt(e00s[1]);
            idIndex++;
            System.out.println(idIndex);
            return "E00" + idIndex;
        } catch (Exception e) {
            return "E001";
        }
    }

    private String getRole() {
        return String.valueOf(cmbRole.getSelectionModel().getSelectedItem());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setdataInCoboBox();
        textFelfd();
    }

    private void checkJasper(String id) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport("/Users/sithumimesh/Documents/IJSE/Idea Project/MobileShop/src/main/resources/asses/report/QR.jrxml");
            JRDataSource jrDataSource = new JRBeanCollectionDataSource(Arrays.asList(new Object()));

            HashMap hm = new HashMap();
            hm.put("id", id);

            Connection connection = DBConnection.getInstance().getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hm, connection);
            JasperViewer.viewReport(jasperPrint, false);
//
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setdataInCoboBox() {
        ArrayList<String> roles = new ArrayList<>();
        roles.add("Admin");
        roles.add("Cashier");
        roles.add("Repairer");
        roles.add("Salesman");
        roles.add("Other");
        cmbRole.getItems().addAll(roles);
    }

    public void txtContactOnKeyRelease(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd,txtContactNumber,txtContactNumber.getText(),"^(?:0|94|\\+94)?(?:7(0|1|2|4|5|6|7|8)\\d)\\d{6}$","-fx-text-fill: white");
    }

    public void fNameKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd,txtFName,txtFName.getText(),"^[a-zA-Z-'`]+[ a-zA-Z-'`]$","-fx-text-fill: white");

    }

    public void lNameKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd,txtLname,txtLname.getText(),"^[a-zA-Z-'`]+[ a-zA-Z-'`]$","-fx-text-fill: white");

    }

    public void cityKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd,txtCity,txtCity.getText(),"^[a-zA-Z-'`]+[ a-zA-Z-'`]$","-fx-text-fill: white");

    }

    public void nicKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd,txtNIC,txtNIC.getText(),"^(?:19|20)?\\d{2}[0-9]{10}|[0-9]{9}[x|X|v|V]$","-fx-text-fill: white");

    }

    public void emailKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd,txtEmail,txtEmail.getText(),"^([\\w\\.\\-_]+)?\\w+@[\\w-_]+(\\.\\w+){1,}$","-fx-text-fill: white");

    }

    public void userNameKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtUserName, txtUserName.getText(), "^[a-zA-Z0-9_]{4,16}$", "-fx-text-fill: white");

    }

    public void passwordKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtPassword, txtPassword.getText(), "^(?=.{8,})(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$", "-fx-text-fill: white");

    }
}
