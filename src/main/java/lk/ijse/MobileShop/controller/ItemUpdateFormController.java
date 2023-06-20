package lk.ijse.MobileShop.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import lk.ijse.MobileShop.dto.Item;
import lk.ijse.MobileShop.model.ItemModel;
import lk.ijse.MobileShop.utill.RegexUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ItemUpdateFormController implements Initializable {
    private static String id;
    public JFXTextField txtItemName;
    public JFXTextField txtCatagory;
    public JFXTextField txtBrand;
    public JFXTextField txtDiscription;
    public JFXButton btnAdd;
    public JFXTextField txtWarrantyMonth;
    public JFXTextField txtSellingPrice;
    public JFXTextField txtQty;

    public static void setId(String id) {
        ItemUpdateFormController.id = id;
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        System.out.println("mula");
        StockFormController.getInstance().updateIcon.setVisible(false);
        StockFormController.getInstance().detailsIcon.setVisible(false);
        StockFormController.getInstance().addIcon.setVisible(true);
        StockFormController.getInstance().closePane.setTranslateX(-1500);
        StockFormController.getInstance().AddUpdatePane.setTranslateX(-1500);
        StockFormController.getInstance().AddUpdatePane.getChildren().clear();
        StockFormController.getInstance().btnAddtxt.setText("    New");

        Item item = new Item();
        item.setItem_name(txtItemName.getText());
        item.setQty(Integer.parseInt(txtQty.getText()));
        item.setUnit_price(Double.parseDouble(txtSellingPrice.getText()));
        item.setDescription(txtDiscription.getText());
        item.setCategory(txtCatagory.getText());
        item.setWarranty_Month(txtWarrantyMonth.getText());
        item.setBrand(txtBrand.getText());
        item.setItem_code(id);
        System.out.println(id);
        if (ItemModel.update(item)){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated").show();
            StockFormController.getInstance().loadAllIds();
        }else {
            new Alert(Alert.AlertType.ERROR,"Error").show();
        }
    }

    private void setData() {
        try {
            Item item = ItemModel.getItem(id);
            txtItemName.setText(item.getItem_name());
            txtCatagory.setText(item.getCategory());
            txtDiscription.setText(item.getDescription());
            txtBrand.setText(item.getBrand());
            txtWarrantyMonth.setText(item.getWarranty_Month());
            txtQty.setText(String.valueOf(item.getQty()));
            txtSellingPrice.setText(String.valueOf(item.getUnit_price()));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setData();
    }

    public void txtItemNameKeyRelease(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd,txtItemName,txtItemName.getText(),"^[a-zA-Z-'`]+[ a-zA-Z-'`]$","-fx-text-fill: white");

    }

    public void txtCatagoryKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd,txtCatagory,txtCatagory.getText(),"^[a-zA-Z-'`]+[ a-zA-Z-'`]$","-fx-text-fill: white");

    }

    public void txtBrandKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd,txtBrand,txtBrand.getText(),"^[a-zA-Z-'`]+[ a-zA-Z-'`]$","-fx-text-fill: white");
    }

    public void txtDescriptionKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd,txtDiscription,txtDiscription.getText(),"^[a-zA-Z-'`]+[ a-zA-Z-'`]$","-fx-text-fill: white");
    }

    public void txtWarrentyKeyRelease(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtWarrantyMonth, txtWarrantyMonth.getText(), "^0*(\\d{1,9})$", "-fx-text-fill: white");
    }

    public void txtSellingPrice(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtSellingPrice, txtSellingPrice.getText(), "^(\\d+\\.\\d{1,2})$", "-fx-text-fill: white");

    }

    public void txtQtyKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtQty, txtQty.getText(), "^0*(\\d{1,9})$", "-fx-text-fill: white");

    }
}
