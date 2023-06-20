package lk.ijse.MobileShop.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lk.ijse.MobileShop.dto.Supplier;
import lk.ijse.MobileShop.model.SupplierModel;
import lk.ijse.MobileShop.utill.Navigation;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SupplierDeleteFormController implements Initializable {
    public Text txtName;
    public Text txtLocation;
    public Text txtEmail;
    public Text txtContactNumber;
    public Text txtId;

    private static String id = null;
    public JFXButton btnComform;
    public JFXButton btncancel;

    public void ComformOnAction(ActionEvent actionEvent) {
        SupplierFormController.getInstance().AddUpdatePane.setTranslateX(-1000);
        SupplierFormController.getInstance().AddUpdatePane.getChildren().clear();
        SupplierFormController.getInstance().closePane.setTranslateX(-1100);

        try {
            if (SupplierModel.remove(id)){
                new Alert(Alert.AlertType.CONFIRMATION,"Succcessfully Deleted");
                SupplierFormController.getInstance().loadAllIds();
                SupplierFormController.getInstance().count();
                SupplierFormController.getInstance().addIcon.setVisible(true);
                SupplierFormController.getInstance().supplierDeleteIcon.setVisible(false);
            }else {
                new Alert(Alert.AlertType.CONFIRMATION,"Error");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void colseMouserClick(MouseEvent mouseEvent) {
        Navigation.close(mouseEvent);
    }

    public void CloseOnAction(ActionEvent actionEvent) {
        SupplierFormController.getInstance().AddUpdatePane.setTranslateX(-1000);
        SupplierFormController.getInstance().AddUpdatePane.getChildren().clear();
        SupplierFormController.getInstance().closePane.setTranslateX(-1100);
        SupplierFormController.getInstance().newSupplier.setText("       New Supplier");
        SupplierFormController.getInstance().supplierDeleteIcon.setVisible(false);
        SupplierFormController.getInstance().addIcon.setVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Supplier supplier = SupplierModel.getData(id);
            txtId.setText(supplier.getSupplier_id());
            txtName.setText(supplier.getCompany_name());
            txtLocation.setText(supplier.getLocation());
            txtEmail.setText(supplier.getEmail());
            txtContactNumber.setText(supplier.getContact_number());

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void setId(String id){
        SupplierDeleteFormController.id = id;
    }
}
