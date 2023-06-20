package lk.ijse.MobileShop.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lk.ijse.MobileShop.dto.Supplier;
import lk.ijse.MobileShop.model.SupplierModel;
import lk.ijse.MobileShop.utill.Navigation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SupplierBarFormController implements Initializable {

    private static SupplierBarFormController controller;
    @FXML
    public Text txtSupId;
    @FXML
    private JFXButton btnupdate;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private Text txtCompanyName;

    @FXML
    private Text txtLocation;

    @FXML
    private Text txtContact;

    @FXML
    private Text txtEmail;

    public SupplierBarFormController() {
        controller = this;
    }

    public static SupplierBarFormController getInstance() {
        return controller;
    }

    public void setData(String id) {
        try {
            Supplier supplier = SupplierModel.getData(id);
            txtSupId.setText(supplier.getSupplier_id());
            txtCompanyName.setText(supplier.getCompany_name());
            txtContact.setText(supplier.getContact_number());
            txtLocation.setText(supplier.getLocation());
            txtEmail.setText(supplier.getEmail());

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void ViewOnMouseClick(MouseEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws IOException {
        SupplierDeleteFormController.setId(txtSupId.getText());
        SupplierFormController.getInstance().supplierDeleteIcon.setVisible(true);
        SupplierFormController.getInstance().addIcon.setVisible(false);
        SupplierFormController.getInstance().AddUpdatePane.setTranslateX(-15);
        SupplierFormController.getInstance().closePane.setTranslateX(275);
        FXMLLoader loader = new FXMLLoader(EmployeeFormController.class.getResource("/view/SupplierDeleteForm.fxml"));
        Parent root = loader.load();
        SupplierFormController.getInstance().AddUpdatePane.getChildren().add(root);
        SupplierFormController.getInstance().newSupplier.setText("Delete");
    }

    @FXML
    void mousEntered(MouseEvent event) {
        btnDelete.setVisible(true);
        btnupdate.setVisible(true);
    }

    @FXML
    void mouseExited(MouseEvent event) {
        btnDelete.setVisible(false);
        btnupdate.setVisible(false);
    }

    @FXML
    void mouseKeyEntered(MouseEvent event) {

    }

    @FXML
    void mouseKeyExited(MouseEvent event) {

    }

    @FXML
    void updateOnAction(ActionEvent event) throws IOException {

        SupplierUpdateFormController.setId(txtSupId.getText());
        SupplierFormController.getInstance().AddUpdatePane.setTranslateX(-15);
        SupplierFormController.getInstance().closePane.setTranslateX(275);
        FXMLLoader loader = new FXMLLoader(EmployeeFormController.class.getResource("/view/SupllierUpdateForm.fxml"));
        Parent root = loader.load();
        SupplierFormController.getInstance().AddUpdatePane.getChildren().add(root);
        SupplierFormController.getInstance().newSupplier.setText("Update");
        SupplierFormController.getInstance().addIcon.setVisible(false);
        SupplierFormController.getInstance().updateIcon.setVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnDelete.setVisible(false);
        btnupdate.setVisible(false);
    }
}
