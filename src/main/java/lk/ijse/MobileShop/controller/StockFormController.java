package lk.ijse.MobileShop.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lk.ijse.MobileShop.model.ItemModel;
import lk.ijse.MobileShop.utill.Navigation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StockFormController implements Initializable {

    private static StockFormController stockFormController;
    public JFXTextField txtSearch;
    public ImageView addIcon;
    public Text btnAddtxt;
    public ImageView deleteIcon;
    public ImageView updateIcon;
    public Pane closePane;
    public Pane AddUpdatePane;
    public ImageView detailsIcon;
    public Text txtLimitedStock;

    @FXML
    private VBox vBox;

    public StockFormController() {
        stockFormController = this;
    }

    public static StockFormController getInstance() {
        return stockFormController;
    }

    @FXML
    void backOnAction(ActionEvent event) {
        try {
            Navigation.switchNavigation("AdminDashBord.fxml", event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void searchOnKeyReleased(KeyEvent event) throws SQLException, ClassNotFoundException {
        vBox.getChildren().clear();
        ArrayList<String> ids = ItemModel.getSearchId(txtSearch.getText());

        for (int i = 0; i < ids.size(); i++) {
            loadAllItems(ids.get(i));
        }

    }

    private void loadAllItems(String id) {
        try {
            FXMLLoader loader = new FXMLLoader(StockFormController.class.getResource("/view/ItemBarForm.fxml"));
            Parent root = loader.load();
            ItemBarFormController controller = loader.getController();
            controller.setData(id);
            vBox.getChildren().add(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAllIds();
        btnAddtxt.setText("    New");
        AddUpdatePane.setTranslateX(-1500);
        closePane.setTranslateX(-1500);
        setItemStockLimited();
    }

    public void loadAllIds() {
        try {
            vBox.getChildren().clear();
            ArrayList<String> ids = ItemModel.getItemIds();
            for (int i = 0; i < ids.size(); i++) {
                loadAllItems(ids.get(i));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void newItemOnAction(ActionEvent event) throws IOException {
        AddUpdatePane.setTranslateX(-5);
        closePane.setTranslateX(-10);
        FXMLLoader loader = new FXMLLoader(StockFormController.class.getResource("/view/ItemAddForm.fxml"));
        Parent root = loader.load();
        AddUpdatePane.getChildren().add(root);
    }
    public void closeOnMouseClick(MouseEvent mouseEvent) {
        Navigation.exit();
    }
    public void closePaneOnMouseClick(MouseEvent mouseEvent) {
        updateIcon.setVisible(false);
        detailsIcon.setVisible(false);
        addIcon.setVisible(true);
        closePane.setTranslateX(-1500);
        AddUpdatePane.setTranslateX(-1500);
        AddUpdatePane.getChildren().clear();
        btnAddtxt.setText("    New");
    }
    private void setItemStockLimited() {
        String count = null;
        try {
            count = ItemModel.getItemLimitCount();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        if (count != null) {
            txtLimitedStock.setText(count);

        }
    }

}
