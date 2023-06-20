package lk.ijse.MobileShop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lk.ijse.MobileShop.model.EmployeeAttendanceModel;
import lk.ijse.MobileShop.model.ItemModel;
import lk.ijse.MobileShop.model.OrderModel;
import lk.ijse.MobileShop.utill.Navigation;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class CashierDashbordFormController implements Initializable {
    private static CashierDashbordFormController controller;
    public Text txtAttendanceCount;
    public Text txtTodayOrders;
    public Text hh;
    public Text mm;
    public Text date;
    public Text txtLimitedStock;

    public CashierDashbordFormController() {
        controller = this;
    }

    public static CashierDashbordFormController getInstance() {
        return controller;
    }

    public void closeAction(MouseEvent mouseEvent) {
        Navigation.exit();
    }

    public void btnCustomerOnAction(ActionEvent event) {
        try {
            Navigation.switchNavigation("CustomerForm.fxml", event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stockViewOnAction(ActionEvent event) throws IOException {
        Navigation.switchNavigation("StockViewForm.fxml", event);
    }

    public void empAttendanceOnAction(ActionEvent event) throws IOException {
        Navigation.switchNavigation("EmployeeAttendance.fxml", event);
    }

    public void btnOderOnAction(ActionEvent actionEvent) {
        try {
            Navigation.switchNavigation("OderFrom.fxml", actionEvent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void logOutOnMouseClick(MouseEvent mouseEvent) {
        try {
            Navigation.switchNavigation("LoginForm.fxml", mouseEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCount() {
        try {
            ResultSet set = EmployeeAttendanceModel.getCount();
            if (set.next()) {
                txtAttendanceCount.setText(set.getString(1));
            }
            ResultSet set1 = OrderModel.getCount();
            if (set1.next()){
               txtTodayOrders.setText(set1.getString(1));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCount();
        time();
        setItemStockLimited();
    }
    private void time() {
        Thread thread=new Thread(() -> {
            SimpleDateFormat format = new SimpleDateFormat("hh:mm");
            try {
                while (true){
                    Thread.sleep(1000);
                    String format1 = format.format(new Date());
                    String[] split = format1.split(":");
                    mm.setText(split[1]);
                    hh.setText(split[0]);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();

        SimpleDateFormat format=new SimpleDateFormat("EEE, MMM d,");
        date.setText(format.format(new Date()));
    }
    private void setItemStockLimited() {
        String count = null;
        try {
            count = ItemModel.getItemLimitCount();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        if (count != null) {
            txtLimitedStock.setText(count);

        }
    }
}
