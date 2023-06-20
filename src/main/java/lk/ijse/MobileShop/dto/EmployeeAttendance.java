package lk.ijse.MobileShop.dto;

public class EmployeeAttendance {
    private String id;
    private String name;
    private String time;
    private String date;

    public EmployeeAttendance() {
    }

    public EmployeeAttendance(String id, String name, String time, String date) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "EmployeeAttendance{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
