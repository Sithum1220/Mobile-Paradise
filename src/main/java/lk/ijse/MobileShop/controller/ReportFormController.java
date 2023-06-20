package lk.ijse.MobileShop.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import lk.ijse.MobileShop.model.EmployeeSalaryModel;
import lk.ijse.MobileShop.utill.Navigation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReportFormController implements Initializable {

    private static ReportFormController controller;
    public Pane pane;
    public Text txtAllSalary;
    public JFXButton btnSalary;

    public ReportFormController() {
        controller = this;
    }

    public static ReportFormController getInstance() {
        return controller;
    }

    public void backOnAction(ActionEvent actionEvent) {
        try {
            Navigation.switchNavigation("AdminDashBord.fxml", actionEvent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeOnMouseClick(MouseEvent mouseEvent) {
        Navigation.close(mouseEvent);
    }

    public void SalaryOnAction(ActionEvent actionEvent) throws IOException {
        pane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(EmployeeFormController.class.getResource("/view/SalaryFrom.fxml"));
        Parent root = loader.load();
        pane.getChildren().add(root);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCountOfMonthlySalaryPayment();
        pane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(EmployeeFormController.class.getResource("/view/SalaryFrom.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pane.getChildren().add(root);
    }

    public void setCountOfMonthlySalaryPayment() {
        try {
            txtAllSalary.setText(EmployeeSalaryModel.getCountOfMonthlySalaryPayment());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void RepotOnAction(ActionEvent actionEvent) {
        pane.getChildren().clear();
        try {
            FXMLLoader loader = new FXMLLoader(EmployeeFormController.class.getResource("/view/ReportViewFrom.fxml"));
            Parent root = loader.load();
            pane.getChildren().add(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
