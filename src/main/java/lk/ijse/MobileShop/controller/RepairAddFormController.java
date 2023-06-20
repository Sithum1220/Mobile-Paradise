package lk.ijse.MobileShop.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.MobileShop.dto.Customer;
import lk.ijse.MobileShop.dto.Item;
import lk.ijse.MobileShop.dto.Order;
import lk.ijse.MobileShop.dto.Repair;
import lk.ijse.MobileShop.model.CustomerModel;
import lk.ijse.MobileShop.model.ItemModel;
import lk.ijse.MobileShop.model.OrderModel;
import lk.ijse.MobileShop.model.RepairModel;
import lk.ijse.MobileShop.utill.DateTimeUtil;
import lk.ijse.MobileShop.utill.Navigation;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RepairAddFormController implements Initializable {
    @FXML
    private JFXTextField txtModelNumber;

    @FXML
    private JFXTextField txtImei;

    @FXML
    private JFXTextField txtError;

    @FXML
    private JFXComboBox cmbCustomerId;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXComboBox cmbStatus;

    @FXML
    private JFXTextField txtCustomerName;


    @FXML
    private JFXTextField txtOrderId;

    @FXML
    private JFXTextField txtItemId;

    @FXML
    void addOnAction(ActionEvent event) {
        Thread thread = new Thread(() -> {
            OrderFormController.getInstance().AddUpdatePane.setTranslateX(-2000);
            OrderFormController.getInstance().AddUpdatePane.getChildren().clear();
            OrderFormController.getInstance().closePane.setTranslateX(-2000);
        });
        thread.start();

        if (cmbStatus.getValue().equals("Warranty")){
//            System.out.println("Warranty");
            try {

                Item item = ItemModel.getItem(txtItemId.getText());

                System.out.println(item.getItem_code());
                System.out.println(item.getItem_code()!=null);

                if (item.getItem_code()!=null){

                    try {
                        System.out.println(item.getWarranty_Month());
                        int countOfMonth= Integer.parseInt(item.getWarranty_Month());
                        Order order = OrderModel.getData(txtOrderId.getText());

                        System.out.println(order);
                        String date = order.getDate();

                        System.out.println(date);
                        String[] split = date.split("-");

                        int incrementCountOfMonth=0;
                        int month= Integer.parseInt(split[1]);
                        int year =Integer.parseInt(split[0]);


                        for (int i = 0; i < countOfMonth; i++) {

//                        System.out.println(year+"-"+month+"-"+split[2]);
                            if (month<=12){
                                month++;
                                if (month==13){
                                    month=01;
                                    year++;
                                }
                            }else {
                                year++;
                                month=01;
                            }
                        }
                        String dateNow=  DateTimeUtil.dateNow();
                        String[] dateSplit = dateNow.split("-");


                        if (Integer.parseInt(dateSplit[0])<=year & Integer.parseInt(dateSplit[1])<= month ){

                            if ( Integer.parseInt(dateSplit[0])==year & Integer.parseInt(dateSplit[1])== month ){

                                if( Integer.parseInt(split[2])<Integer.parseInt(dateSplit[2])){
                                    add(event);
                                System.out.println("year == month == but Date Is < OK");
                                }else {
                                    error(event);
                                System.out.println("year != month != but Date Is !< error");
                                }

                            }else {
                                add(event);
                            System.out.println("year > month > OK");
                            }

                        }else {
                            error(event);
                        System.out.println("error");
                        }

                    }catch (NumberFormatException e) {
                        System.out.println(e);
                    }

                }else {
                    System.out.println("Can't Find This Item Code, Please Check Your Warranty Card ");

                  Alert alert=  new Alert(Alert.AlertType.WARNING,"Can't Find This Item Code, Please Check Your Warranty Card ");
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.setAlwaysOnTop(true);
                    stage.toFront();
                    alert.show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }else {
            try {
                add(event);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void add(ActionEvent event) throws SQLException, ClassNotFoundException {
        System.out.println("Ok");
        Repair repair = new Repair();
        repair.setRepair_id(id());
        repair.setStatus((String) cmbStatus.getValue());
        repair.setCustomer_id((String) cmbCustomerId.getValue());
        repair.setError_type(txtError.getText());
        repair.setImei_number(txtImei.getText());
        repair.setGiven_date(DateTimeUtil.dateNow());
        repair.setModel_number(txtModelNumber.getText());
        repair.setReturn_give_date(null);

        if (RepairModel.add(repair)) {
            new Alert(Alert.AlertType.INFORMATION, "Ok").show();
            OrderFormController.getInstance().loadTablaData();
        }
    }

    private void error(ActionEvent event){
        System.out.println("Warranty is Out OF Date");
        new Alert(Alert.AlertType.ERROR,"Warranty is Out OF Date").show();
    }

    private String id() {
        try {
            ArrayList<String> allId = RepairModel.getAllId();
            String lastId = null;
            for (int i = 0; i < allId.size(); i++) {
                lastId = allId.get(i);
                System.out.println(allId.get(i));
            }
            try {
                String[] e00s = lastId.split("R00");
                int idIndex = Integer.parseInt(e00s[1]);
                idIndex++;
                System.out.println(idIndex);
                return "R00" + idIndex;
            } catch (Exception e) {
                return "R001";
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbCustomerOnAction(ActionEvent event) {
        try {
            Customer customer = CustomerModel.getData((String) cmbCustomerId.getValue());
            txtCustomerName.setText(customer.getFirst_name() + " " + customer.getLast_name());

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbStatusOnAction(ActionEvent event) {
        if (cmbStatus.getValue().equals("Warranty")){
            txtItemId.setVisible(true);
            txtOrderId.setVisible(true);
        }else {
            txtItemId.setVisible(false);
            txtOrderId.setVisible(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCustomerIds();
        loadStatus();
    }

    private void loadStatus() {
        ArrayList<String> status = new ArrayList<>();
        status.add("Warranty");
        status.add("Repair");
        cmbStatus.getItems().addAll(status);
    }

    private void loadCustomerIds() {
        try {
            ArrayList<String> allId = CustomerModel.getAllId();
            cmbCustomerId.getItems().addAll(allId);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
