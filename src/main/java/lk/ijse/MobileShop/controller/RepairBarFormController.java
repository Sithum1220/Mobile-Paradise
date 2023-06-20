package lk.ijse.MobileShop.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lk.ijse.MobileShop.dto.Repair;
import lk.ijse.MobileShop.model.RepairModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RepairBarFormController implements Initializable {

    public static RepairBarFormController controller;
    @FXML
    public Text txtError;
    @FXML
    public JFXButton btnupdate;
    @FXML
    public JFXButton btnDelete;
    @FXML
    private Text txtRepairId;
    @FXML
    private Text txtCustomerId;
    @FXML
    private Text txtImei;
    @FXML
    private Text txtModelNumber;
    @FXML
    private Text txtWarrenty;

    public RepairBarFormController() {
        controller = this;
    }

    public static RepairBarFormController getInstance() {
        return controller;
    }

    public void updateOnAction(ActionEvent actionEvent) {
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws IOException {
        RepairDeleteFormController.setId(txtRepairId.getText());
        RepairDeleteFormController.setDeleteBtn(btnDelete);
        OrderFormController.getInstance().AddUpdatePane.setTranslateX(-235);
        FXMLLoader loader = new FXMLLoader(RepairDeleteFormController.class.getResource("/view/RepairDeleteForm.fxml"));
        Parent root = loader.load();
        OrderFormController.getInstance().AddUpdatePane.getChildren().add(root);
        OrderFormController.getInstance().closePane.setTranslateX(0);
        OrderFormController.getInstance().detailsIcon.setVisible(false);
        OrderFormController.getInstance().addIcon.setVisible(false);
        OrderFormController.getInstance().deleteIcon.setVisible(true);
        OrderFormController.getInstance().btnNew.setText("Delete");
    }

    public void setData(String id) {
        try {
            Repair repair = RepairModel.get(id);
            txtRepairId.setText(repair.getRepair_id());
            txtCustomerId.setText(repair.getCustomer_id());
            txtError.setText(repair.getError_type());
            txtImei.setText(repair.getImei_number());
            txtWarrenty.setText(repair.getStatus());
            txtModelNumber.setText(repair.getModel_number());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void viewClickOnMouseClicked(MouseEvent mouseEvent) throws IOException {

        RepairViewFormController.setId(txtRepairId.getText());
        OrderFormController.getInstance().AddUpdatePane.setTranslateX(-235);
        FXMLLoader loader = new FXMLLoader(EmployeeFormController.class.getResource("/view/RepairViewForm.fxml"));
        Parent root = loader.load();
        OrderFormController.getInstance().AddUpdatePane.getChildren().add(root);
        OrderFormController.getInstance().closePane.setTranslateX(0);
        OrderFormController.getInstance().detailsIcon.setVisible(true);
        OrderFormController.getInstance().addIcon.setVisible(false);
        OrderFormController.getInstance().btnNew.setText("Details");

    }

    public void viewOnMouseEnterd(MouseEvent mouseEvent) {
        btnupdate.setVisible(true);
        btnDelete.setVisible(true);
    }

    public void viewOnMouseExit(MouseEvent mouseEvent) {
        btnupdate.setVisible(false);
        btnDelete.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnupdate.setVisible(false);
        btnDelete.setVisible(false);
    }

}
