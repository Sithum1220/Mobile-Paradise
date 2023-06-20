package lk.ijse.MobileShop.controller;

import com.jfoenix.controls.JFXBadge;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import lk.ijse.MobileShop.dto.Item;
import lk.ijse.MobileShop.model.ItemModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ItemViewFormController implements Initializable {


    private static String id;
    public JFXButton btncancel;
    public Text txtName;
    public Text txtCategory;
    public Text txtQty;
    public Text txtPrice;
    public Text txtId;
    public Text txtBrand;
    public Text txtDescription;
    public Text txtDescription1;
    public JFXButton btnComform;
    public Text txtWrranty;
    private JFXBadge btnAddtxt;

    public static void setId(String id) {
        ItemViewFormController.id = id;
    }

    public void CloseOnAction(ActionEvent actionEvent) {
        StockFormController.getInstance().updateIcon.setVisible(false);
        StockFormController.getInstance().detailsIcon.setVisible(false);
        StockFormController.getInstance().addIcon.setVisible(true);
        StockFormController.getInstance().closePane.setTranslateX(-1500);
        StockFormController.getInstance().AddUpdatePane.setTranslateX(-1500);
        StockFormController.getInstance().AddUpdatePane.getChildren().clear();
        StockFormController.getInstance().btnAddtxt.setText("    New");
    }

    public void setData() {
        try {
            Item item = ItemModel.getItem(id);
            txtId.setText(item.getItem_code());
            txtName.setText(item.getItem_name());
            txtCategory.setText(item.getCategory());
            txtPrice.setText(String.valueOf(item.getUnit_price()));
            txtQty.setText(String.valueOf(item.getQty()));
            txtBrand.setText(item.getBrand());
            txtDescription.setText(item.getDescription());
            txtWrranty.setText(item.getWarranty_Month());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setData();
    }
}
