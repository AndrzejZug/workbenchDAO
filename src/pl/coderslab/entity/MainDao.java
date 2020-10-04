package pl.coderslab.entity;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

public class MainDao {

    //    @Override
//    public String toString() {
//        return "MainDao{name}";
//    }



    public static void main(String[] args) {


        UserDao userDao = new UserDao();
//        User user = new User();
//        user.setUserName("adam");
//        user.setEmail("adam@root.pl");
//        user.setPassword("123456");
//        userDao.create(user);

        User read = userDao.read(12);
//        System.out.println(read);
//        User read2 = userDao.read(11);
//        System.out.println(read2);
//        UserDao userDao2 = new UserDao();
        read.setEmail("adam4@noob.pl");
        read.setUserName("NieAdam2");
        read.setPassword("admin4");
        userDao.update(read);

    }

}
