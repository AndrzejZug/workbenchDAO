package pl.coderslab.entity;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

public class MainDao {

    //    @Override
//    public String toString() {
//        return "MainDao{name}";
//    }



    public static void main(String[] args) {


        UserDao userD = new UserDao();
 //       userD = UserDao.findAll();
//        User user = new User();
//        user.setUserName("janpol");
//        user.setEmail("janpol@root.pl");
//        user.setPassword("admin7");
//        userD.create(user);

//        User read = userD.readUserName("adam2");
//        System.out.println(read);
//        User read2 = userD.read(11);
//        System.out.println(read2);
//        try {
//            userD.delete("users", 27);
//            read.setEmail("xxx2@noob.pl");
//            read.setUserName("xxx2");
//            read.setPassword("xxx2");
//
//        userD.update(read);

//            User secondUser = new User();
//            secondUser.setUserName("marek");
//            secondUser.setEmail("marek@coderslab.pl");
//            secondUser.setPassword("pass");
//            userD.create(secondUser);
            User[] all = userD.findAll();
            for (User u : all) {
                System.out.println(u);
            }
//        }catch (NullPointerException ex){
//            System.out.println("Błędny email");
//        }

    }

}
