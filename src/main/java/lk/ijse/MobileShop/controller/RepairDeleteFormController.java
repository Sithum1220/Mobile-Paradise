package lk.ijse.MobileShop.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import lk.ijse.MobileShop.dto.Customer;
import lk.ijse.MobileShop.dto.Repair;
import lk.ijse.MobileShop.model.CustomerModel;
import lk.ijse.MobileShop.model.RepairModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RepairDeleteFormController implements Initializable {
    public JFXTextField txtItemId;
    public JFXTextField txtOrderId;

    public Text txtRepairId;
    public Text txtName;
    public Text txtSteet;
    public Text txtModelNum;
    public Text txtImeiNum;
    public Text txtStatus;

    public Text txtreturnDate;
    public Text txtContactNumber;
    public Text txtErrorType;
    public JFXButton btnDelete;
    private static String id;
    public Text txtCustomerId;

private static JFXButton delete;
    public static void setDeleteBtn(JFXButton btnDelete) {
        RepairDeleteFormController.delete = btnDelete;
    }


    public void CloseOnAction(ActionEvent event) {
        OrderFormController.getInstance().AddUpdatePane.setTranslateX(-2000);
        OrderFormController.getInstance().AddUpdatePane.getChildren().clear();
        OrderFormController.getInstance().closePane.setTranslateX(-2000);
        OrderFormController.getInstance().addIcon.setVisible(true);
        OrderFormController.getInstance().detailsIcon.setVisible(false);
        OrderFormController.getInstance().deleteIcon.setVisible(false);
        OrderFormController.getInstance().btnNew.setText("New Repair");
    }

    public void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        System.out.println(id);
        if (!txtreturnDate.getText().equals("")){
           new Alert(Alert.AlertType.ERROR,"This Repair is cannot delete").show();
        }else {
            if (RepairModel.Remove(id)){
                new Alert(Alert.AlertType.CONFIRMATION,"Done").show();
                OrderFormController.getInstance().loadTablaData();

            }else {
                new Alert(Alert.AlertType.ERROR,"Error").show();
            }
        }
        OrderFormController.getInstance().AddUpdatePane.setTranslateX(-2000);
        OrderFormController.getInstance().AddUpdatePane.getChildren().clear();
        OrderFormController.getInstance().closePane.setTranslateX(-2000);
        OrderFormController.getInstance().addIcon.setVisible(true);
        OrderFormController.getInstance().detailsIcon.setVisible(false);
        OrderFormController.getInstance().deleteIcon.setVisible(false);
        OrderFormController.getInstance().btnNew.setText("New Repair");
    }
    public static void setId(String id){
        RepairDeleteFormController.id=id;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setData();
    }
}
