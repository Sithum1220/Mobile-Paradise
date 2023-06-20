package lk.ijse.MobileShop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class SupplierOrder {
    private String supplier_order_id;
    private String supplier_id;
    private String time;
    private String date;
    private String payment;

}
