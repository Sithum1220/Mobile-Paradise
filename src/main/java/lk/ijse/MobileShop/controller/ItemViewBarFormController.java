package lk.ijse.MobileShop.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lk.ijse.MobileShop.dto.Item;
import lk.ijse.MobileShop.model.ItemModel;

import java.sql.SQLException;

public class ItemViewBarFormController {
    public Text txtBrand;
    public Text txtWarranty;
    @FXML
    private JFXButton btnupdate;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private Text txtItemCode;

    @FXML
    private Text txtName;

    @FXML
    private Text txtQty;

    @FXML
    private Text txtCatagory;

    @FXML
    private Text txtPrice;

    public void setData(String id) {
        System.out.println(id);
        try {
            Item item = ItemModel.getItem(id);
            txtItemCode.setText(item.getItem_code());
            txtName.setText(item.getItem_name());
            txtCatagory.setText(item.getCategory());
            txtPrice.setText(String.valueOf(item.getUnit_price()));
            txtQty.setText(String.valueOf(item.getQty()));
            txtBrand.setText(item.getBrand());
            txtWarranty.setText(item.getWarranty_Month());

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void ViewOnMouseClick(MouseEvent mouseEvent) {

    }
}
