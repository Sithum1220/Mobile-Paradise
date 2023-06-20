package lk.ijse.MobileShop.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class OrderDetailsBarFormController {
    @FXML
    public Text txtId;

    @FXML
    public Text txtName;

    @FXML
    public Text txtQty;

    @FXML
    public Text txtPrice;

    @FXML
    public JFXButton btnDelete;

    public void setData(String id, String name, String qty, String price) {
        txtId.setText(id);
        txtName.setText(name);
        txtQty.setText(qty);
        txtPrice.setText(price);
    }

    @FXML
    void mousEntered(MouseEvent event) {

    }

    @FXML
    void mouseExited(MouseEvent event) {

    }


    public void btnDeleteOnAction(ActionEvent actionEvent) {
        CustomerOrderFormController.getInstance().remove(txtId.getText());
    }
}
