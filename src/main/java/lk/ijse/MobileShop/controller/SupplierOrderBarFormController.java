package lk.ijse.MobileShop.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SupplierOrderBarFormController implements Initializable {
    public Text txtItemId;
    public Text ItemName;
    public Text txtPrice;
    public Text txtQty;
    public JFXButton btnDelete;

    public void setData(String id, String qty, String price, String name) {
        txtItemId.setText(id);
        txtQty.setText(qty);
        txtPrice.setText(price);
        ItemName.setText(name);
    }

    public void ViewOnMouseClick(MouseEvent mouseEvent) {
        
    }

    public void mousEntered(MouseEvent mouseEvent) {
        btnDelete.setVisible(true);
    }

    public void mouseExited(MouseEvent mouseEvent) {
        btnDelete.setVisible(false);
    }

    public void mouseKeyEntered(MouseEvent mouseEvent) {
    }

    public void mouseKeyExited(MouseEvent mouseEvent) {
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        for (int i = 0; i < SupplierOrderFormController.getInstance().list.size(); i++) {
            if (SupplierOrderFormController.getInstance().list.get(i).getId().equals(txtItemId.getText())){
                SupplierOrderFormController.getInstance().list.remove(i);
                try {
                    SupplierOrderFormController.getInstance().loadAllData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnDelete.setVisible(false);
    }
}
