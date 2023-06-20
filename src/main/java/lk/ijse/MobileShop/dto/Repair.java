package lk.ijse.MobileShop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Repair {
    private String repair_id;
    private String customer_id;
    private String status;
    private String imei_number;
    private String model_number;
    private String error_type;
    private String given_date;
    private String return_give_date;


}
