package lk.ijse.MobileShop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SupplierOrderUpdateTm {
    private String item_code;
    private String payment;
    private String qty;
    private String itemPrice;
    private String sellinPrice;
}
