package lk.ijse.MobileShop.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lk.ijse.MobileShop.dto.Item;
import lk.ijse.MobileShop.dto.ItemTm;
import lk.ijse.MobileShop.dto.Supplier;
import lk.ijse.MobileShop.dto.SupplierOrder;
import lk.ijse.MobileShop.model.ItemModel;
import lk.ijse.MobileShop.model.SupplierModel;
import lk.ijse.MobileShop.model.SupplierOrderModel;
import lk.ijse.MobileShop.utill.DateTimeUtil;
import lk.ijse.MobileShop.utill.RegexUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SupplierOrderFormController implements Initializable {
    public JFXButton btnAdd;
    public JFXComboBox cmbItemsIds;
    public JFXComboBox cmbSupplierIds;
    public Text txtItemName;
    public Text txtQty;
    public JFXTextField txtOderQty;
    public JFXTextField txtItemPrice;
    public JFXTextField txtSellingPrice;
    public Text txtCompanyName;
    public Text txtEmail;
    public JFXButton btnDone;
    public JFXComboBox cmbItemsId;
    double total = 0;
    ArrayList<ItemTm> list = new ArrayList<>();

    private static SupplierOrderFormController controller;

    public SupplierOrderFormController(){
        controller = this;
    }
    public static SupplierOrderFormController getInstance(){
        return controller;
    }

    public void btnAddOnAction(ActionEvent actionEvent) throws IOException {
        if (list.isEmpty()) {
            SupplierFormController.getInstance().tablePane.setTranslateX(-95);
            System.out.println("AAa");
            Parent root = FXMLLoader.load(getClass().getResource("/view/SupplierOrderTableForm.fxml"));
            Scene scene = btnAdd.getScene();
            root.translateYProperty().set(scene.getHeight());

            SupplierFormController.getInstance().tablePane.getChildren().add(root);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(t -> {
                SupplierFormController.getInstance().tablePane.getChildren().remove(SupplierFormController.getInstance().tablePane);
            });
            timeline.play();
        }
// =====================================================================================================================
        boolean isNotDuplicate = true;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(cmbItemsIds.getValue())) {
                list.get(i).setQty(String.valueOf(Integer.parseInt(list.get(i).getQty()) + Integer.parseInt(txtOderQty.getText())));
                isNotDuplicate = false;
            }
        }
        if (isNotDuplicate) {
            ItemTm itemTm = new ItemTm();
            itemTm.setId(String.valueOf(cmbItemsIds.getValue()));
            itemTm.setName(txtItemName.getText());
            itemTm.setPrice(txtItemPrice.getText());
            itemTm.setQty(txtOderQty.getText());
            itemTm.setSellingPrice(txtSellingPrice.getText());
            list.add(itemTm);
        }
        try {
            loadAllData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        textField();
        btnDone.setDisable(false);
    }

    public void backOnAction(ActionEvent actionEvent) {
        back();
    }

    public void cmbItemIdsOnAction(ActionEvent actionEvent) {
        try {
            Item stock = ItemModel.getItem(String.valueOf(cmbItemsIds.getValue()));
            ItemTm itemTm = SupplierOrderModel.getSupplierOrderDetailsData(stock.getItem_code());
            txtItemName.setText(stock.getItem_name());
            txtQty.setText(String.valueOf(stock.getQty()));
            txtItemPrice.setText(itemTm.getPrice());
            txtSellingPrice.setText(String.valueOf(stock.getUnit_price()));
            txtOderQty.setDisable(false);
            System.out.println(txtItemPrice.getText());
            if (txtItemPrice.getText() == null) {
                txtItemPrice.setText("0.0");
            }else {
                txtItemPrice.setText(txtItemPrice.getText()+".0");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbSupplierOnAction(ActionEvent actionEvent) {
        try {
            cmbItemsIds.setDisable(false);
            Supplier supplier = SupplierModel.getData(String.valueOf(cmbSupplierIds.getValue()));
            txtCompanyName.setText(supplier.getCompany_name());
            txtEmail.setText(supplier.getEmail());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnDoneOnAction(ActionEvent actionEvent) {
        for (int i = 0; i < list.size(); i++) {
            total += Double.parseDouble(list.get(i).getPrice()) * Double.parseDouble(list.get(i).getQty());
        }
        SupplierOrder order = new SupplierOrder();
        order.setSupplier_order_id(id());
        order.setDate(DateTimeUtil.dateNow());
        order.setTime(DateTimeUtil.timeNow());
        order.setSupplier_id((String) cmbSupplierIds.getValue());
        order.setPayment(String.valueOf(total));
        System.out.println(cmbSupplierIds.getValue());
        try {
            if (SupplierOrderModel.pleaseOrder(list, order)) {
                list.clear();
                SupplierFormController.getInstance().count();
                if (SupplierFormController.getInstance().redioBtnOrders.isSelected()){
                    SupplierFormController.getInstance().loadAllOrderId();
                }
                new Alert(Alert.AlertType.CONFIRMATION, "Ok").show();
            }
            back();
        } catch (SQLException e) {
          e.printStackTrace();
        }
    }

    private String id() {
        try {
            ArrayList<String> allId = SupplierOrderModel.getAllId();
            String lastId = null;
            for (int i = 0; i < allId.size(); i++) {
                lastId = allId.get(i);
                System.out.println(allId.get(i));
            }
            try {
                String[] e00s = lastId.split("SO00");
                int idIndex = Integer.parseInt(e00s[1]);
                idIndex++;
                System.out.println(idIndex);
                return "SO00" + idIndex;
            } catch (Exception e) {
                return "SO001";
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void loadAllData() throws IOException {
        SupplierOrderTableFormController.getInstance().vBox.getChildren().clear();
        for (int i = 0; i < list.size(); i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SuppierOrderBarForm.fxml"));
            Parent root = loader.load();
            SupplierOrderBarFormController controller = loader.getController();
            controller.setData(list.get(i).getId(), list.get(i).getQty(), list.get(i).getPrice(), list.get(i).getName());
            SupplierOrderTableFormController.getInstance().vBox.getChildren().add(root);
        }
    }

    public void loadItemid() {
        try {
            ArrayList<String> ids = ItemModel.getItemIds();
            cmbItemsIds.getItems().addAll(ids);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadSupplierId() {
        try {
            ArrayList<String> ids = SupplierModel.getAllId();
            cmbSupplierIds.getItems().addAll(ids);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadItemid();
        loadSupplierId();
        cmbItemsIds.setDisable(true);
        btnAdd.setDisable(true);
        txtOderQty.setDisable(true);
        btnDone.setDisable(true);
    }

    public void textField() {
        txtItemName.setText("");
        txtQty.setText("");
        txtItemPrice.setText("");
        txtOderQty.setText("");
        txtSellingPrice.setText("");
        cmbItemsIds.getItems().clear();
        loadItemid();
    }

    public void orderQtyOnKeyRelease(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtOderQty, txtOderQty.getText(), "^0*(\\d{1,9})$", "-fx-text-fill: white");
    }

    public void sellingPriceOnKeyRelease(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtSellingPrice, txtSellingPrice.getText(), "^(\\d+\\.\\d{1,2})$", "-fx-text-fill: white");
        if (txtOderQty.getText().isEmpty() || txtOderQty.getText().equals(0)) {
            btnAdd.setDisable(true);
        } else {
            btnAdd.setDisable(false);
        }
    }

    public void itemPriceKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtItemPrice, txtItemPrice.getText(), "^(\\d+\\.\\d{1,2})$", "-fx-text-fill: white");
        if (txtOderQty.getText().isEmpty() || txtOderQty.getText().equals(0)) {
            btnAdd.setDisable(true);
        } else {
            btnAdd.setDisable(false);
        }
    }
    public void back(){
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(SupplierFormController.getInstance().orderPane);
        slide.setToX(-1000);
        slide.play();
        SupplierFormController.getInstance().orderPane.setTranslateX(0);
        slide.setOnFinished((ActionEvent e) -> {
            SupplierFormController.getInstance().orderPane.getChildren().clear();
        });

        TranslateTransition slide2 = new TranslateTransition();
        slide2.setDuration(Duration.seconds(0.4));
        slide2.setNode(SupplierFormController.getInstance().tablePane);
        slide2.setToX(-1000);
        slide2.play();
        SupplierFormController.getInstance().tablePane.setTranslateX(0);
        slide.setOnFinished((ActionEvent e) -> {
            SupplierFormController.getInstance().tablePane.getChildren().clear();
        });
    }

    public void cmbItemIdOnAction(ActionEvent actionEvent) {

    }
}
