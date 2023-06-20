package lk.ijse.MobileShop.controller;

import javafx.scene.layout.VBox;

public class SupplierOrderTableFormController {
    public VBox vBox;
    private static SupplierOrderTableFormController controller;

    public SupplierOrderTableFormController(){
        controller =this;
    }
    public static SupplierOrderTableFormController getInstance(){
        return controller;
    }
}
