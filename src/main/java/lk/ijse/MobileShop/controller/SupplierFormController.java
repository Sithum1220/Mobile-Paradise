package lk.ijse.MobileShop.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.lowagie.text.pdf.collection.PdfCollectionField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lk.ijse.MobileShop.model.SupplierModel;
import lk.ijse.MobileShop.model.SupplierOrderModel;
import lk.ijse.MobileShop.utill.Navigation;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SupplierFormController implements Initializable {

    private static SupplierFormController controller;
    public Text txtAllSupplierCount;
    public Text txtTodayOrderCount;
    public JFXRadioButton redioBtnAllSupplier;
    public JFXRadioButton redioBtnOrders;

    public JFXTextField txtSearch;
    public Pane orderPane;
    public Pane tablePane;
    public Button btn;
    public Pane AddUpdatePane;
    public ImageView updateIcon;
    public JFXButton newSupplier;
    public JFXButton btnOrders;
    public ImageView newOrderIcon;
    public Pane closePane;
    public JFXTextField txtOrderSearch;
    public ImageView deleteIcon;
    public ImageView addIcon;
    public ImageView orderUpdateIcon;
    public ImageView supplierDeleteIcon;
    public PdfCollectionField detailsIcon;

    @FXML
    private VBox vBox;

    @FXML
    private HBox supplierMenuHbox;

    @FXML
    private HBox ordersMenuHbox;

    public SupplierFormController() {
        controller = this;
    }

    public static SupplierFormController getInstance() {
        return controller;
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
    void newSupplierOnAction(ActionEvent event) throws IOException {
        AddUpdatePane.setTranslateX(-15);
        closePane.setTranslateX(275);
        FXMLLoader loader = new FXMLLoader(EmployeeFormController.class.getResource("/view/SupllierAddForm.fxml"));
        Parent root = loader.load();
        AddUpdatePane.getChildren().add(root);
    }

    @FXML
    void searchOnKeyReleased(KeyEvent event) {
        try {
            if (redioBtnAllSupplier.isSelected()) {
                vBox.getChildren().clear();
                ArrayList<String> ids = SupplierModel.getSearchId(txtSearch.getText());

                for (int i = 0; i < ids.size(); i++) {
                    loadAllSupplier(ids.get(i));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadAllSupplier(String id) {

        try {
            FXMLLoader loader = new FXMLLoader(SupplierFormController.class.getResource("/view/SupplierBarForm.fxml"));
            Parent root = loader.load();
            SupplierBarFormController controller = loader.getController();
            controller.setData(id);
            vBox.getChildren().add(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void loadAllIds() {
        vBox.getChildren().clear();
        try {
            ArrayList<String> id = SupplierModel.getAllId();
            for (int i = 0; i < id.size(); i++) {
                loadAllSupplier(id.get(i));
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAllIds();
        count();
        orderPane.setTranslateX(-1000);
        tablePane.setTranslateX(-1000);
        AddUpdatePane.setTranslateX(-1500);
        closePane.setTranslateX(-1100);

    }

    public void count() {
        try {
            ResultSet set = SupplierModel.getAllSupplierCount();
            if (set.next()) {
                txtAllSupplierCount.setText(set.getString(1));
            }
            ResultSet set1 = SupplierOrderModel.getCount();
            if (set1.next()){
                txtTodayOrderCount.setText(set1.getString(1));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void closeOnMouseClick(MouseEvent mouseEvent) {
        Navigation.exit();
    }


    public void allSupplierOnAction(ActionEvent actionEvent) {
        if (redioBtnAllSupplier.isSelected()) {
            loadAllIds();
            ordersMenuHbox.setVisible(false);
            supplierMenuHbox.setVisible(true);
            txtOrderSearch.setVisible(false);
            txtSearch.setVisible(true);
        }
        if (redioBtnOrders.isSelected()) {
            ordersMenuHbox.setVisible(true);
            supplierMenuHbox.setVisible(false);
            loadAllOrderId();
            txtOrderSearch.setVisible(true);
            txtSearch.setVisible(false);


        }
    }

    public void allOrdersOnAction(ActionEvent actionEvent) {
        if (redioBtnAllSupplier.isSelected()) {
            ordersMenuHbox.setVisible(false);
            supplierMenuHbox.setVisible(true);
            txtOrderSearch.setVisible(false);
            txtSearch.setVisible(true);
            loadAllIds();
        }
        if (redioBtnOrders.isSelected()) {
            ordersMenuHbox.setVisible(true);
            supplierMenuHbox.setVisible(false);
            txtOrderSearch.setVisible(true);
            txtSearch.setVisible(false);
            loadAllOrderId();
        }
    }

    public void newOrderOnAction(ActionEvent actionEvent) throws IOException {
        orderPane.setTranslateX(50);
        FXMLLoader loader = new FXMLLoader(EmployeeFormController.class.getResource("/view/SupplierOrderForm.fxml"));
        Parent root = loader.load();
        orderPane.getChildren().add(root);
    }

    public void loadAllOrderId() {
        vBox.getChildren().clear();
        try {
            ArrayList<String> allId = SupplierOrderModel.getAllId();
            for (int i = 0; i < allId.size(); i++) {
                loadAllSupplierOrderData(allId.get(i));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadAllSupplierOrderData(String id) {
        try {
            FXMLLoader loader = new FXMLLoader(SupplierFormController.class.getResource("/view/SupplierAllOrderBarForm.fxml"));
            Parent root = loader.load();
            SupplierAllOrderBarFormController controller = loader.getController();
            controller.setData(id);
            vBox.getChildren().add(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closePaneOnMouseClick(MouseEvent mouseEvent) {
        updateIcon.setVisible(false);
        newOrderIcon.setVisible(true);
        deleteIcon.setVisible(false);
        addIcon.setVisible(true);
        orderUpdateIcon.setVisible(false);
        supplierDeleteIcon.setVisible(false);
        closePane.setTranslateX(-1100);
        btnOrders.setText("     New Orders");
        AddUpdatePane.setTranslateX(-1500);
        AddUpdatePane.getChildren().clear();
        SupplierFormController.getInstance().newSupplier.setText("       New Supplier");
    }

    public void orderSearchOnKeyReleased(KeyEvent keyEvent) {
        vBox.getChildren().clear();
        try {
            ArrayList<String> searchId = SupplierOrderModel.getSearchId(txtOrderSearch.getText());
            for (int i = 0; i < searchId.size(); i++) {
                loadAllSupplierOrder(searchId.get(i));
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllSupplierOrder(String id) throws IOException {
        FXMLLoader loader = new FXMLLoader(SupplierAllOrderBarFormController.class.getResource("/view/SupplierAllOrderBarForm.fxml"));
        Parent root = loader.load();
        SupplierAllOrderBarFormController controller = loader.getController();
        controller.setData(id);
        vBox.getChildren().add(root);
    }
}

