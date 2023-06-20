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
import lk.ijse.MobileShop.model.CustomerModel;
import lk.ijse.MobileShop.utill.DateTimeUtil;
import lk.ijse.MobileShop.utill.Navigation;
import lk.ijse.MobileShop.utill.RegexUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerUpdateFormController implements Initializable {

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
    private JFXButton btnUpdate;

   public static String id;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCustomer();
    }

    private void loadCustomer() {
        try {
            Customer customer = CustomerModel.getData(id);
            txtContactNumber.setText(customer.getContact_number());
            txtEmail.setText(customer.getEmail());
            txtNIC.setText(customer.getNic());
            txtCity.setText(customer.getCity());
            txtLane.setText(customer.getLane());
            txtStreet.setText(customer.getStreet());
            txtFName.setText(customer.getFirst_name());
            txtLname.setText(customer.getLast_name());

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setId(String id) {
        CustomerUpdateFormController.id = id;
    }

    public void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        Customer customer = new Customer();
        customer.setContact_number(txtContactNumber.getText());
        customer.setEmail(txtEmail.getText());
        customer.setNic(txtNIC.getText());
        customer.setCity(txtCity.getText());
        customer.setLane(txtLane.getUserAgentStylesheet());
        customer.setStreet(txtStreet.getText());
        customer.setFirst_name(txtFName.getText());
        customer.setLast_name(txtLname.getText());
        customer.setCustomer_id(id);
        if (CustomerModel.update(customer)){
            new Alert(Alert.AlertType.CONFIRMATION,"Done").show();
            CustomerFormController.getInstance().loadAllIds();
        } else {
            new Alert(Alert.AlertType.ERROR,"Error").show();
        }
        CustomerFormController.getInstance().deleteIcon.setVisible(false);
        CustomerFormController.getInstance().updateIcon.setVisible(false);
        CustomerFormController.getInstance().addIcon.setVisible(true);
        CustomerFormController.getInstance().txtNew.setText("New");
        CustomerFormController.getInstance().closePane.setTranslateX(-1500);
        CustomerFormController.getInstance().addUpdatePane.setTranslateX(-1500);
        CustomerFormController.getInstance().addUpdatePane.getChildren().clear();
    }

    public void fNameKeyRelased(KeyEvent keyEvent) {
        RegexUtil.regex(btnUpdate,txtFName,txtFName.getText(),"^[a-zA-Z-'`]+[ a-zA-Z-'`]$","-fx-text-fill: white");

    }

    public void lNameKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btnUpdate,txtLname,txtLname.getText(),"^[a-zA-Z-'`]+[ a-zA-Z-'`]$","-fx-text-fill: white");

    }

    public void cityKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btnUpdate,txtCity,txtCity.getText(),"^[a-zA-Z-'`]+[ a-zA-Z-'`]$","-fx-text-fill: white");

    }

    public void nicKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btnUpdate,txtNIC,txtNIC.getText(),"^(?:19|20)?\\d{2}[0-9]{10}|[0-9]{9}[x|X|v|V]$","-fx-text-fill: white");

    }

    public void emailKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btnUpdate,txtEmail,txtEmail.getText(),"^([\\w\\.\\-_]+)?\\w+@[\\w-_]+(\\.\\w+){1,}$","-fx-text-fill: white");

    }

    public void contactKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btnUpdate,txtContactNumber,txtContactNumber.getText(),"^(?:0|94|\\+94)?(?:7(0|1|2|4|5|6|7|8)\\d)\\d{6}$","-fx-text-fill: white");

    }
}
