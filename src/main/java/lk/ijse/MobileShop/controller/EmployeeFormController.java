package lk.ijse.MobileShop.controller;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lk.ijse.MobileShop.model.EmployeeAttendanceModel;
import lk.ijse.MobileShop.model.EmployeeModel;
import lk.ijse.MobileShop.utill.Navigation;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EmployeeFormController implements Initializable {
    public VBox vBox;
    public JFXTextField txtSearch;
    public JFXRadioButton rBtnAllEmployee;
    public JFXRadioButton rBtnAttendance;

    private static EmployeeFormController controller;
    public Text txtallAttended;
    public Text txtallEmployee;
    public Pane tempPane;
    public Pane closePane;
    public ImageView deleteIcon;
    public Text optionLbl;
    public ImageView newIcon;
    public ImageView detailsIcon;
    public ImageView updateIcon;


    public EmployeeFormController() {
        controller=this;
    }
    public static EmployeeFormController getInstance(){
        return controller;
    }

    public void backOnAction(ActionEvent event){
        try {
            Navigation.switchNavigation("AdminDashBord.fxml",event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchOnKeyReleased(KeyEvent keyEvent)  {
        if (rBtnAllEmployee.isSelected()){
            vBox.getChildren().clear();
            ArrayList<String> ids= null;
            try {
                ids = EmployeeModel.getSearchId(txtSearch.getText());
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            for (int i = 0; i < ids.size(); i++) {
                loadAllEmployee(ids.get(i));
            }
        }
        if (rBtnAttendance.isSelected()){
            vBox.getChildren().clear();
            ArrayList<String> ids= null;
            try {
                ids = EmployeeAttendanceModel.getSearchId(txtSearch.getText());
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            for (int i = 0; i < ids.size(); i++) {
                loadAllEmployee(ids.get(i));
            }
        }

    }

    public void newEmployeeOnAction(ActionEvent event) throws IOException {
        tempPane.setTranslateX(-55);
        closePane.setTranslateX(0);
        FXMLLoader loader = new FXMLLoader(EmployeeFormController.class.getResource("/view/EmployeeAddForm.fxml"));
        Parent root = loader.load();
        tempPane.getChildren().add(root);
    }

    public void allEmployeeOnAction(ActionEvent event) {
        if(rBtnAllEmployee.isSelected()){
            loadAllIds();

        }
        if(rBtnAttendance.isSelected()){
            loadAllTodayAttendanceIds();
        }
    }

    private void loadAllTodayAttendanceIds() {
        vBox.getChildren().clear();
        try {
            ArrayList<String> id= EmployeeAttendanceModel.getAllId();
            for (int i = 0; i < id.size(); i++) {
                loadAllEmployee(id.get(i));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void attendOnAction(ActionEvent event) {
        if(rBtnAllEmployee.isSelected()){
            loadAllIds();

        }if(rBtnAttendance.isSelected()){
            loadAllTodayAttendanceIds();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tempPane.setTranslateX(-380);
        closePane.setTranslateX(-390);
        loadAllIds();
        setCount();
        optionLbl.setText("New");
    }

    public void setCount() {
        try {
            ResultSet set1= EmployeeModel.getAllEmployeeCount();
            if (set1.next()){
                txtallEmployee.setText(set1.getString(1));
            }
            ResultSet set2 =EmployeeAttendanceModel.getCount();
            if (set2.next()){
                txtallAttended.setText(set2.getString(1));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void loadAllIds() {
        vBox.getChildren().clear();
        try {
            ArrayList<String> id= EmployeeModel.getAllId();
            for (int i = 0; i < id.size(); i++) {
                loadAllEmployee(id.get(i));
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllEmployee(String id) {

        try {
            FXMLLoader loader = new FXMLLoader(EmployeeFormController.class.getResource("/view/EmployeeBarForm.fxml"));
            Parent root = loader.load();
            EmployeeBarFormController controller = loader.getController();
            controller.setData(id);
            vBox.getChildren().add(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void closeOnMouseClick(MouseEvent event) {
        Navigation.exit();
    }


    public void mainImgMouseClick(MouseEvent mouseEvent) {
        tempPane.getChildren().clear();
        tempPane.setTranslateX(-380);
        deleteIcon.setVisible(false);
        detailsIcon.setVisible(false);
        updateIcon.setVisible(false);
        newIcon.setVisible(true);
        optionLbl.setText("New");
    }

    public void vBoxMouseclick(MouseEvent mouseEvent) {
        tempPane.getChildren().clear();
        tempPane.setTranslateX(-380);
        deleteIcon.setVisible(false);
        detailsIcon.setVisible(false);
        updateIcon.setVisible(false);
        newIcon.setVisible(true);
        optionLbl.setText("New");
    }

    public void closePaneOnMouseClick(MouseEvent mouseEvent) {
        tempPane.getChildren().clear();
        tempPane.setTranslateX(-380);
        closePane.setTranslateX(-390);
        deleteIcon.setVisible(false);
        detailsIcon.setVisible(false);
        updateIcon.setVisible(false);
        newIcon.setVisible(true);
        optionLbl.setText("New");
    }
}
