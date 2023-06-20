package lk.ijse.MobileShop.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lk.ijse.MobileShop.dto.Employee;
import lk.ijse.MobileShop.model.EmployeeModel;
import lk.ijse.MobileShop.model.ItemModel;
import lk.ijse.MobileShop.utill.Navigation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StockViewFormController implements Initializable {
    public JFXTextField txtSearch;
    public Text txtLimitedItem;

    @FXML
    private VBox vBox;

    @FXML
    void backOnAction(ActionEvent event) throws IOException {
        Navigation.switchNavigation("CashierDashBordForm.fxml",event);
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
            FXMLLoader loader = new FXMLLoader(StockFormController.class.getResource("/view/ItemViewBarForm.fxml"));
            Parent root = loader.load();
            ItemViewBarFormController controller = loader.getController();
            controller.setData(id);
            vBox.getChildren().add(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAllIds();
        setItemStockLimited();
    }

    private void loadAllIds() {
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

    public void closeOnMouseClick(MouseEvent mouseEvent) {
        Navigation.exit();
    }
    private void setItemStockLimited() {
        String count = null;
        try {
            count = ItemModel.getItemLimitCount();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        if (count != null) {
            txtLimitedItem.setText(count);

        }
    }
}
