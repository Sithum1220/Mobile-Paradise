package lk.ijse.MobileShop.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import lk.ijse.MobileShop.dto.Order;
import lk.ijse.MobileShop.model.OrderModel;
import lk.ijse.MobileShop.model.RepairModel;
import lk.ijse.MobileShop.utill.DateTimeUtil;
import lk.ijse.MobileShop.utill.Navigation;
import lk.ijse.MobileShop.utill.RegexUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentformController {

    public JFXButton btnAdd;
    @FXML
    private JFXTextField txtrepairId;

    @FXML
    private JFXTextField txtPayment;

    @FXML
    private JFXTextField txtCustomerId;

    @FXML
    void btnPayOnAction(ActionEvent event) {

        try {
            String status = RepairModel.getStatus(txtrepairId.getText());
            if (status.equals("Warranty")){
                 new Alert(Alert.AlertType.WARNING,"Something Wong").show();
            }else {

                Order order=new Order();

                order.setDate(DateTimeUtil.dateNow());
                order.setTime(DateTimeUtil.timeNow());
                order.setCustomer_id(txtCustomerId.getText());
                order.setPayment(txtPayment.getText());
                order.setRepair_id(txtrepairId.getText());
                order.setCustomer_order_id(id());

                try {
                    if (OrderModel.placeAdd(order)){

                        new Alert(Alert.AlertType.INFORMATION,"Ok").show();
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
            OrderFormController.getInstance().paymentPane.setTranslateX(-2000);
            OrderFormController.getInstance().closePaymentPane.setTranslateX(-2000);
            OrderFormController.getInstance().paymentPane.getChildren().clear();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }



    }
    private String id() {
        try {
            ArrayList<String> allId = OrderModel.getAllId();
            String lastId = null;
            for (int i = 0; i < allId.size(); i++) {
                lastId = allId.get(i);
                System.out.println(allId.get(i));
            }
            try {
                String[] e00s = lastId.split("O00");
                int idIndex = Integer.parseInt(e00s[1]);
                idIndex++;
                System.out.println(idIndex);
                return "O00" + idIndex;
            } catch (Exception e) {
                return "O001";
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void repairOnAction(ActionEvent actionEvent) {

    }

    public void priceKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtPayment, txtPayment.getText(), "^(\\d+\\.\\d{1,2})$", "-fx-text-fill: white");

    }
}
