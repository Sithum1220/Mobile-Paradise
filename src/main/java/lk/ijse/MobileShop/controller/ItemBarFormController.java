package lk.ijse.MobileShop.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lk.ijse.MobileShop.dto.Item;
import lk.ijse.MobileShop.model.ItemModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ItemBarFormController implements Initializable {

    @FXML
    private JFXButton btnupdate;

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
    public void setData(String id){
        System.out.println(id);
        try {
            Item item= ItemModel.getItem(id);
            txtItemCode.setText(item.getItem_code());
            txtName.setText(item.getItem_name());
            txtCatagory.setText(item.getCategory());
            txtPrice.setText(String.valueOf(item.getUnit_price()));
            txtQty.setText(String.valueOf(item.getQty()));


        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void ViewOnMouseClick(MouseEvent event) throws IOException {
        ItemViewFormController.setId(txtItemCode.getText());
        StockFormController.getInstance().detailsIcon.setVisible(true);
        StockFormController.getInstance().addIcon.setVisible(false);
        StockFormController.getInstance().AddUpdatePane.setTranslateX(-5);
        StockFormController.getInstance().closePane.setTranslateX(-10);
        FXMLLoader loader = new FXMLLoader(ItemViewFormController.class.getResource("/view/ItemViewForm.fxml"));
        Parent root = loader.load();
        StockFormController.getInstance().AddUpdatePane.getChildren().add(root);
        StockFormController.getInstance().btnAddtxt.setText(" Details");

    }



    @FXML
    void mousEntered(MouseEvent event) {

    }

    @FXML
    void mouseExited(MouseEvent event) {

    }

    @FXML
    void updateOnAction(ActionEvent event) throws IOException {
        ItemUpdateFormController.setId(txtItemCode.getText());
        StockFormController.getInstance().updateIcon.setVisible(true);
        StockFormController.getInstance().addIcon.setVisible(false);
        StockFormController.getInstance().AddUpdatePane.setTranslateX(-5);
        StockFormController.getInstance().closePane.setTranslateX(-10);
        FXMLLoader loader = new FXMLLoader(ItemViewFormController.class.getResource("/view/ItemUpdateForm.fxml"));
        Parent root = loader.load();
        StockFormController.getInstance().AddUpdatePane.getChildren().add(root);
        StockFormController.getInstance().btnAddtxt.setText("Update");
    }

    public void mouseKeyEntered(MouseEvent mouseEvent) {
        btnupdate.setVisible(true);
    }

    public void mouseKeyExited(MouseEvent mouseEvent) {
        btnupdate.setVisible(false);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnupdate.setVisible(false);
    }
}
