package lk.ijse.MobileShop.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lk.ijse.MobileShop.model.CustomerModel;
import lk.ijse.MobileShop.model.EmployeeAttendanceModel;
import lk.ijse.MobileShop.model.EmployeeModel;
import lk.ijse.MobileShop.utill.Navigation;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {
    private static CustomerFormController controller;
    public VBox vBox;
    public JFXTextField txtSearch;
    public Text txtallCustomer;
    public Pane addUpdatePane;
    public Pane closePane;
    public ImageView addIcon;
    public ImageView deleteIcon;
    public ImageView updateIcon;
    public ImageView detailsIcon;
    public Text txtNew;

    public CustomerFormController() {
        controller = this;
    }

    public static CustomerFormController getInstance() {
        return controller;
    }

    public void backOnAction(ActionEvent event) {
        try {
            Navigation.switchNavigation("CashierDashBordForm.fxml", event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchOnKeyReleased(KeyEvent keyEvent) {
        try {
            vBox.getChildren().clear();
            ArrayList<String> ids = CustomerModel.getSearchId(txtSearch.getText());

            for (int i = 0; i < ids.size(); i++) {
                loadAllCustomer(ids.get(i));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadAllCustomer(String id) {
        try {
            FXMLLoader loader = new FXMLLoader(CustomerFormController.class.getResource("/view/CustomerBarForm.fxml"));
            Parent root = loader.load();
            CustomerBarFormController controller = loader.getController();
            controller.setData(id);
            vBox.getChildren().add(root);
            setAllCustomer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void newCustomerOnAction(ActionEvent event) throws IOException {
        addUpdatePane.setTranslateX(-5);
        closePane.setTranslateX(0);
        FXMLLoader loader = new FXMLLoader(CustomerFormController.class.getResource("/view/CustomerAddForm.fxml"));
        Parent root = loader.load();
        addUpdatePane.getChildren().add(root);
    }

    public void closeOnMouseClick(MouseEvent mouseEvent) {
        Navigation.exit();
    }

    public void loadAllIds() {
        vBox.getChildren().clear();
        try {
            ArrayList<String> id= CustomerModel.getAllId();
            for (int i = 0; i < id.size(); i++) {
                loadAllCustomer(id.get(i));
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAllIds();
        setAllCustomer();
        addUpdatePane.setTranslateX(-1500);
        closePane.setTranslateX(-1500);
        txtNew.setText("New");
    }

    public void setAllCustomer() {
        try {
            ResultSet set1= CustomerModel.getAllCustomerCount();
            if (set1.next()){
                txtallCustomer.setText(set1.getString(1));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void closePaneOnMouseClick(MouseEvent mouseEvent) {
        deleteIcon.setVisible(false);
        updateIcon.setVisible(false);
        addIcon.setVisible(true);
        detailsIcon.setVisible(false);
        txtNew.setText("New");
        closePane.setTranslateX(-1500);
        addUpdatePane.setTranslateX(-1500);
        addUpdatePane.getChildren().clear();
    }
}
