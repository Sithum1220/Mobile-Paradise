package lk.ijse.MobileShop.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lk.ijse.MobileShop.dto.Customer;
import lk.ijse.MobileShop.dto.Repair;
import lk.ijse.MobileShop.model.CustomerModel;
import lk.ijse.MobileShop.model.RepairModel;
import lk.ijse.MobileShop.utill.Navigation;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RepairViewFormController implements Initializable {

    public Text txtCustomerId;
    private static String id;
    public CheckBox checkGive;

    @FXML
    private JFXButton btncancel;

    @FXML
    private Text txtRepairId;

    @FXML
    private Text txtName;

    @FXML
    private Text txtSteet;

    @FXML
    private Text txtModelNum;

    @FXML
    private Text txtImeiNum;

    @FXML
    private Text txtStatus;

    @FXML
    private Text txtErrorType;

    @FXML
    private Text txtContactNumber;

    @FXML
    private Text txtreturnDate;

    public void CloseOnAction(ActionEvent actionEvent) {
        OrderFormController.getInstance().AddUpdatePane.setTranslateX(-2000);
        OrderFormController.getInstance().AddUpdatePane.getChildren().clear();
        OrderFormController.getInstance().closePane.setTranslateX(-2000);
        OrderFormController.getInstance().addIcon.setVisible(true);
        OrderFormController.getInstance().detailsIcon.setVisible(false);
        OrderFormController.getInstance().btnNew.setText("New Repair");
    }

    public void colseMouserClick(MouseEvent mouseEvent) {
        Navigation.close(mouseEvent);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setData();
        setVisibleRedoButton();
    }

    private void setVisibleRedoButton() {
        try {
            if (RepairModel.getRepDate(txtRepairId.getText())==null){
                checkGive.setVisible(true);
            }else {
                checkGive.setVisible(false);
            }
            System.out.println(txtRepairId.getText());
            if (RepairModel.getStatus(txtRepairId.getText()).equals("Repair")){
                checkGive.setVisible(false);
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public static void setId(String id){
        RepairViewFormController.id=id;
    }



    private void setData() {
        try {
            Repair repair = RepairModel.get(id);
            Customer customer = CustomerModel.getData(repair.getCustomer_id());
            txtName.setText(customer.getFirst_name()+" "+customer.getLast_name());
            txtContactNumber.setText(customer.getContact_number());

            txtRepairId.setText(repair.getRepair_id());
            txtErrorType.setText(repair.getError_type());
            txtreturnDate.setText(repair.getReturn_give_date());
            txtStatus.setText(repair.getStatus());
            txtCustomerId.setText(repair.getCustomer_id());
            txtImeiNum.setText(repair.getImei_number());
            txtModelNum.setText(repair.getModel_number());


        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void giveOnAction(ActionEvent actionEvent) {
        try {
             if (RepairModel.updateDate(txtRepairId.getText())){
                 setVisibleRedoButton();
                 setData();
             }else {
                 setVisibleRedoButton();

             }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }
}
