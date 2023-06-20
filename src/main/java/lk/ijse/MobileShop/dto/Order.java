package lk.ijse.MobileShop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Order {
    private String customer_order_id;
    private String  customer_id;
    private String time;
    private String  date;
    private String payment;
    private String repair_id;

}
