package pl.coderslab;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {

    public static void main(String[] args) {

        UserDao userDao = new UserDao();
        User user = new User();
        user.setUserName("adam");
        user.setEmail("adam@root.pl");
        user.setPassword("123456");
        userDao.create(user);

        User read = userDao.read(12);
        System.out.println(read);


    }

}
