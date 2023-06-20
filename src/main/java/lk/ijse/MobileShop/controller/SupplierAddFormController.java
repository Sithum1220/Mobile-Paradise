package lk.ijse.MobileShop.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import lk.ijse.MobileShop.dto.Supplier;
import lk.ijse.MobileShop.model.SupplierModel;
import lk.ijse.MobileShop.utill.RegexUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierAddFormController {
    public JFXTextField txtCompanyName;
    public JFXTextField txtContactNumber;
    public JFXTextField txtEmail;
    public JFXTextField txtLocation;
    public JFXButton btnAdd;

    public void AddOnAction(ActionEvent actionEvent) {
        SupplierFormController.getInstance().AddUpdatePane.setTranslateX(-1500);
        SupplierFormController.getInstance().AddUpdatePane.getChildren().clear();
        SupplierFormController.getInstance().closePane.setTranslateX(-1100);

        try {
            if (SupplierModel.add(new Supplier(
                    nextId(),
                    txtCompanyName.getText(),
                    txtContactNumber.getText(),
                    txtEmail.getText(),
                    txtLocation.getText()
            ))) {
                SupplierFormController.getInstance().loadAllIds();
                SupplierFormController.getInstance().count();
                new Alert(Alert.AlertType.CONFIRMATION, "Added ").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something Wrong ").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private String nextId() {
        try {
            ArrayList<String> allId = SupplierModel.getAllId();
            String lastId = null;
            for (int i = 0; i < allId.size(); i++) {
                lastId = allId.get(i);
            }
            try {
                String[] s00s = lastId.split("S00");
                int idIndex = Integer.parseInt(s00s[1]);
                idIndex++;
                return "S00" + idIndex;
            } catch (Exception e) {
                return "S001";
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void txtCompanyNameKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd,txtCompanyName,txtCompanyName.getText(),"^[a-zA-Z-'`]+[ a-zA-Z-'`]$","-fx-text-fill: white");

    }

    public void txtContactKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd,txtContactNumber,txtContactNumber.getText(),"^(?:0|94|\\+94)?(?:7(0|1|2|4|5|6|7|8)\\d)\\d{6}$","-fx-text-fill: white");

    }

    public void txtEmailKeyRelease(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd,txtEmail,txtEmail.getText(),"^([\\w\\.\\-_]+)?\\w+@[\\w-_]+(\\.\\w+){1,}$","-fx-text-fill: white");

    }

    public void txtLocationKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd,txtLocation,txtLocation.getText(),"^[a-zA-Z-'`]+[ a-zA-Z-'`]$","-fx-text-fill: white");

    }

}
