package lk.ijse.MobileShop.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.MobileShop.dto.Customer;
import lk.ijse.MobileShop.dto.Item;
import lk.ijse.MobileShop.model.CustomerModel;
import lk.ijse.MobileShop.model.EmployeeModel;
import lk.ijse.MobileShop.model.ItemModel;
import lk.ijse.MobileShop.utill.DateTimeUtil;
import lk.ijse.MobileShop.utill.Navigation;
import lk.ijse.MobileShop.utill.RegexUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomerAddFormController implements Initializable {

    @FXML
    private JFXTextField txtFName;

    @FXML
    private JFXTextField txtLname;

    @FXML
    private JFXTextField txtStreet;

    @FXML
    private JFXTextField txtLane;

    @FXML
    private JFXTextField txtCity;

    @FXML
    private JFXTextField txtNIC;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtContactNumber;

    @FXML
    private JFXButton btnAdd;

    @FXML
    void addOnAction(ActionEvent event) {
        try {
            if(CustomerModel.add(new Customer(
                    nextId(),
                    txtFName.getText(),
                    txtLname.getText(),
                    txtStreet.getText(),
                    txtCity.getText(),
                    txtLane.getText(),
                    DateTimeUtil.dateNow(),
                    txtNIC.getText(),
                    txtEmail.getText(),
                    txtContactNumber.getText()

            ))){
                CustomerFormController.getInstance().loadAllIds();
                new Alert(Alert.AlertType.CONFIRMATION,"Added ").show();
                CustomerFormController.getInstance().closePane.setTranslateX(-1500);
                CustomerFormController.getInstance().addUpdatePane.setTranslateX(-1500);
                CustomerFormController.getInstance().addUpdatePane.getChildren().clear();
            }else {
                new Alert(Alert.AlertType.ERROR,"Something Wrong ").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId() {
        try {
            ArrayList<String> allId = CustomerModel.getAllId();
            String lastId=null;
            for (int i = 0; i < allId.size(); i++) {
                lastId=allId.get(i);
                System.out.println(allId.get(i));
            }
            try {
                String[] e00s = lastId.split("C00");
                int idIndex= Integer.parseInt(e00s[1]);
                idIndex++;
                System.out.println(idIndex);
                return "C00"+idIndex;
            }catch (Exception e){
                return "C001";
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void colseMouserClick(MouseEvent event) {
        Navigation.close(event);
    }

    public void contactOnKeyRelease(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd,txtContactNumber,txtContactNumber.getText(),"^(?:0|94|\\+94)?(?:7(0|1|2|4|5|6|7|8)\\d)\\d{6}$","-fx-text-fill: white");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnAdd.setDisable(true);
    }

    public void firstNameOnKeyRelease(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd,txtFName,txtFName.getText(),"^[a-zA-Z-'`]+[ a-zA-Z-'`]$","-fx-text-fill: white");

    }

    public void lastNameOnkeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd,txtLname,txtLname.getText(),"^[a-zA-Z-'`]+[ a-zA-Z-'`]$","-fx-text-fill: white");

    }
    public void cityOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd,txtCity,txtCity.getText(),"^[a-zA-Z-'`]+[ a-zA-Z-'`]$","-fx-text-fill: white");

    }

    public void nicOnKeyRelased(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd,txtNIC,txtNIC.getText(),"^(?:19|20)?\\d{2}[0-9]{10}|[0-9]{9}[x|X|v|V]$","-fx-text-fill: white");

    }
    public void emailOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd,txtEmail,txtEmail.getText(),"^([\\w\\.\\-_]+)?\\w+@[\\w-_]+(\\.\\w+){1,}$","-fx-text-fill: white");
    }
}
