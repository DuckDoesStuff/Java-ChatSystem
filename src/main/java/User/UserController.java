package User;

import java.sql.*;
import java.util.ArrayList;

public class UserController {
    UserService userService;
    public UserController(Connection conn) {
        this.userService = new UserService(conn);
    }

    public Boolean getUserStatus(String username){
        return userService.getUserStatus(username);
    }
    public static void main(String[] args) {
    }
}
