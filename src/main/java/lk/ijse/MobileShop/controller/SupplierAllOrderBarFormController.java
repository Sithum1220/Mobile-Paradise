package lk.ijse.MobileShop.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lk.ijse.MobileShop.db.DBConnection;
import lk.ijse.MobileShop.dto.SupplierOrder;
import lk.ijse.MobileShop.model.SupplierOrderModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;

public class SupplierAllOrderBarFormController {
    public JFXButton btnupdate;
    @FXML
    private Text txtOrderId;

    @FXML
    private Text txtSupplierId;

    @FXML
    private Text txtDate;

    @FXML
    private Text txtTime;

    @FXML
    private Text txtPayment;

    public void setData(String id) {
        try {
            SupplierOrder supplierOrder = SupplierOrderModel.getData(id);
            txtOrderId.setText(supplierOrder.getSupplier_order_id());
            txtSupplierId.setText(supplierOrder.getSupplier_id());
            txtTime.setText(supplierOrder.getTime());
            txtDate.setText(supplierOrder.getDate());
            txtPayment.setText(supplierOrder.getPayment());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void updateOnAction(ActionEvent actionEvent) throws IOException {
        SupplierOrderUpdateFormController.setOrderId(txtOrderId.getText());
        SupplierFormController.getInstance().AddUpdatePane.getChildren().clear();
        SupplierFormController.getInstance().AddUpdatePane.setTranslateX(-297);
        SupplierFormController.getInstance().closePane.setTranslateX(0);
        SupplierFormController.getInstance().btnOrders.setText("     Order Update");
        SupplierFormController.getInstance().newOrderIcon.setVisible(false);
        SupplierFormController.getInstance().orderUpdateIcon.setVisible(true);
        FXMLLoader loader = new FXMLLoader(SupplierFormController.class.getResource("/view/SupplierOrderUpdateForm.fxml"));
        Parent root = loader.load();
        SupplierFormController.getInstance().AddUpdatePane.getChildren().clear();
        SupplierFormController.getInstance().AddUpdatePane.getChildren().add(root);
    }

    public void ViewOnMouseClick(MouseEvent mouseEvent) {
        System.out.println(txtOrderId.getText());
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport("/Users/sithumimesh/Desktop/MobileShopNew/src/main/resources/asses/report/Supplier_Order.jrxml");
            JRDataSource jrDataSource = new JRBeanCollectionDataSource(Arrays.asList(new Object()));

            HashMap hm = new HashMap();
            hm.put("id",txtOrderId.getText());

            Connection connection = DBConnection.getInstance().getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hm, connection);
            JasperViewer.viewReport(jasperPrint, false);
//
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
