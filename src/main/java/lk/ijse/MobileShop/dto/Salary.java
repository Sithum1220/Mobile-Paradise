package lk.ijse.MobileShop.dto;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Salary {
    private String employee_id;
    private String salary_id;
    private String salary;
    private String monthly_Attendance_count;
    private String date;
    private String time;

}
