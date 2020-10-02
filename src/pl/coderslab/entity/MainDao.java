package pl.coderslab.entity;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

public class MainDao {


    public static void main(String[] args) {

        User user = new User();
        user.setEmail("ajzugaj@datacard.pl");
        user.setUserName("Andy Zugaj");
        user.setPassword("1234");
        UserDao userD1 = new UserDao();


//        UserDao userD = new UserDao();
        UserDao.create(user);

    }
}
