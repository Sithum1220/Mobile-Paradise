package lk.ijse.MobileShop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import lk.ijse.MobileShop.dto.Customer;
import lk.ijse.MobileShop.model.CustomerModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerViewFormController implements Initializable {
    private static String id;
    public Text txtContactNumber;
    public Text txtEmail;
    public Text txtNIC;
    public Text txtDate;
    public Text txtCity;
    public Text txtLane;
    public Text txtStreet;
    public Text txtName;
    public Text txtId;

    public static void setId(String id) {
        CustomerViewFormController.id = id;
    }

    public void CloseOnAction(ActionEvent event) {
        CustomerFormController.getInstance().deleteIcon.setVisible(false);
        CustomerFormController.getInstance().updateIcon.setVisible(false);
        CustomerFormController.getInstance().addIcon.setVisible(true);
        CustomerFormController.getInstance().detailsIcon.setVisible(false);
        CustomerFormController.getInstance().txtNew.setText("New");
        CustomerFormController.getInstance().closePane.setTranslateX(-1500);
        CustomerFormController.getInstance().addUpdatePane.setTranslateX(-1500);
        CustomerFormController.getInstance().addUpdatePane.getChildren().clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setData();
    }

    private void setData() {
        try {
            Customer customer = CustomerModel.getData(id);
            System.out.println(customer.toString());
            txtContactNumber.setText(customer.getContact_number());
            txtEmail.setText(customer.getEmail());
            txtNIC.setText(customer.getNic());
            txtDate.setText(customer.getDate());
            txtCity.setText(customer.getCity());
            txtLane.setText(customer.getLane());
            txtStreet.setText(customer.getStreet());
            txtName.setText(customer.getFirst_name() + " " + customer.getLast_name());
            txtId.setText(customer.getCustomer_id());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
