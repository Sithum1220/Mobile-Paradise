package lk.ijse.MobileShop.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import lk.ijse.MobileShop.dto.Item;
import lk.ijse.MobileShop.dto.SuplierOrderDetails;
import lk.ijse.MobileShop.dto.SupplierOrder;
import lk.ijse.MobileShop.dto.SupplierOrderUpdateTm;
import lk.ijse.MobileShop.model.ItemModel;
import lk.ijse.MobileShop.model.SupplierOrderDetailsModel;
import lk.ijse.MobileShop.model.SupplierOrderModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SupplierOrderUpdateFormController implements Initializable {

    private static String orderId;
    private static SupplierOrderUpdateFormController controller;
    public JFXComboBox cmbItemId;
    public JFXTextField txtPayment;
    public JFXTextField txtQty;
    public JFXTextField txtItemPrice;
    public JFXTextField txtSellingPrice;
    public JFXButton btnAdd;
    public JFXTextField txtReturnQty;
    public Text returnQtylbl;
    public Text buyingQtylbl;
    public JFXButton btnApply;
    public Text totalLbl;
    public Text txtTotal;

    public SupplierOrderUpdateFormController() {
        controller = this;
    }

    public static SupplierOrderUpdateFormController getInstance() {
        return controller;
    }

    public static void setOrderId(String id) {
        SupplierOrderUpdateFormController.orderId = id;
    }

    public void cmbItemIdOnAction(ActionEvent actionEvent) {
        
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        SupplierFormController.getInstance().AddUpdatePane.getChildren().clear();
        SupplierFormController.getInstance().AddUpdatePane.setTranslateX(-1000);
        SupplierFormController.getInstance().closePane.setTranslateX(-1100);
        SupplierFormController.getInstance().btnOrders.setText("     New Orders");
        SupplierFormController.getInstance().newOrderIcon.setVisible(true);
        SupplierFormController.getInstance().updateIcon.setVisible(false);

        SupplierOrderUpdateTm supplierOrderUpdateTm = new SupplierOrderUpdateTm();
        supplierOrderUpdateTm.setItem_code(String.valueOf(cmbItemId.getValue()));
        supplierOrderUpdateTm.setPayment(txtPayment.getText());
    }

    public void setCmbItemId() {
        try {
            ArrayList<String> list = ItemModel.getItemIds();
            cmbItemId.getItems().addAll(list);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCmbItemId();
        setData();
        txtTotal.setVisible(false);
        totalLbl.setVisible(false);
    }

    public void setData() {
        try {
            SuplierOrderDetails details = SupplierOrderDetailsModel.getData(orderId);
            SupplierOrder supplierOrder = SupplierOrderModel.getData(orderId);
            Item item = ItemModel.getItem(details.getItem_code());
            System.out.println(details.getItem_code());
            cmbItemId.setValue(details.getItem_code());
            txtQty.setText(details.getQuantity());
            txtItemPrice.setText(details.getUnit_price());
            txtPayment.setText(supplierOrder.getPayment());
            txtSellingPrice.setText(String.valueOf(item.getUnit_price()));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void btnApplyOnAction(ActionEvent actionEvent) {
        totalLbl.setVisible(true);
        txtTotal.setVisible(true );
        returnQtylbl.setVisible(false);
        buyingQtylbl.setVisible(true);
    }
}
