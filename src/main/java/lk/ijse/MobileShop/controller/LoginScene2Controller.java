package lk.ijse.MobileShop.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.MobileShop.model.UserModel;
import lk.ijse.MobileShop.utill.Navigation;
import lk.ijse.MobileShop.utill.RegexUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginScene2Controller implements Initializable {
    public ImageView imgShow;
    public ImageView imgeClose;
    public JFXTextField txtShowPassword;
    public JFXPasswordField password;
    public JFXTextField txtusename;
    public JFXButton btnSingUp;

    public void btnLoginOnAction(ActionEvent actionEvent) {
        try {
            if (UserModel.checkUsernameAndPassword(txtusename.getText(),password.getText()).equals("Admin")) {
                Navigation.switchNavigation("AdminDashBord.fxml", actionEvent);
            } else if (UserModel.checkUsernameAndPassword(txtusename.getText(),password.getText()).equals("Cashier")) {
                Navigation.switchNavigation("CashierDashBordForm.fxml", actionEvent);
            } else {
                new Alert(Alert.AlertType.ERROR, "Error").show();
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void eyeMouseEnterd(MouseEvent mouseEvent) {
        imgeClose.setVisible(true);
        imgShow.setVisible(false);
        txtShowPassword.setVisible(true);
        password.setVisible(false);
        txtShowPassword.setText(password.getText());
    }

    public void eyeExited(MouseEvent mouseEvent) {
        imgShow.setVisible(true);
        imgeClose.setVisible(false);
        password.setVisible(true);
        txtShowPassword.setVisible(false);
        password.setText(txtShowPassword.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtShowPassword.setVisible(false);
        imgeClose.setVisible(false);

        Platform.runLater(() -> txtusename.requestFocus());
    }

    public void txtUserNameOnAction(ActionEvent actionEvent) {
        password.requestFocus();
    }

    public void txtPasswordOnAction(ActionEvent actionEvent) {
        btnSingUp.fire();

    }

    public void txtUsernameKeyTyped(KeyEvent keyEvent) {
        RegexUtil.regex(btnSingUp, txtusename, txtusename.getText(), "^[a-zA-Z0-9_]{4,16}$", "-fx-text-fill: white");
    }

    public void txtPasswordKeyTyped(KeyEvent keyEvent) {
    }
}
