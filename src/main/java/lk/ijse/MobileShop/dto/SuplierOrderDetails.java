package lk.ijse.MobileShop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SuplierOrderDetails {
    private String item_code;
    private String supplier_order_id;
    private String quantity;
    private String unit_price;
}
