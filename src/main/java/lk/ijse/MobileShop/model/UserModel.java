package lk.ijse.MobileShop.model;

import lk.ijse.MobileShop.dto.User;
import lk.ijse.MobileShop.utill.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {

    public static boolean addUser(User user) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("insert into user VALUES (?,?,?,?)", user.getEmployee_id(), user.getUser_name(), user.getPassword(), user.getRole());
    }

    public static User getData(String id) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.crudUtil("SELECT * FROM user where employee_id=?", id);

        User user = new User();
        while (set.next()) {
            user.setUser_name(set.getString(2));
            user.setPassword(set.getString(3));
        }
        return user;
    }

    public static boolean Remove(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE FROM user WHERE employee_id=?", id);
    }

    public static boolean update(User user) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE user SET " + "user_name=?," + "password=?," + "role=?" + "WHERE employee_id=?", user.getUser_name(), user.getPassword(), user.getRole(), user.getEmployee_id());
    }

    public static String checkUsernameAndPassword(String userName, String password) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.crudUtil("SELECT e.role FROM user u INNER JOIN employee e on u.employee_id = e.employee_id WHERE user_name=? AND password=?", userName, password);

        if (set.next()) {
            return set.getString(1);
        } else {
            return "No";
        }

    }
}
