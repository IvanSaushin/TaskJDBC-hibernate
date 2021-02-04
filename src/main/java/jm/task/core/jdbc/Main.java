package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class Main {


    public static void main(String[] args) throws SQLException {

        // Hibernate
        UserDaoHibernateImpl idhi = new UserDaoHibernateImpl();

        idhi.dropUsersTable();
        idhi.createUsersTable();
        User user1 = new User("alex", "navalny", (byte)34);
        User user2 = new User("vacya", "guba", (byte)19);
        idhi.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        idhi.saveUser(user2.getName(), user2.getLastName(), user2.getAge());

        idhi.removeUserById(1);
//        idhi.cleanUsersTable();
        List<User> list = idhi.getAllUsers();
        list.forEach(System.out::println);



        //JDBC
//        List<User> users;
//        UserService userDao= new UserServiceImpl();
//
//        // my test
//        userDao.dropUsersTable();
//
//        userDao.createUsersTable();
//        userDao.saveUser("James", "Smith", (byte) 35);
//        userDao.saveUser("John", "Travolta", (byte) 46);
//        userDao.saveUser("Ms", "Smith", (byte) 32);
//        users = userDao.getAllUsers();
//        users.forEach(System.out::println); // 3 user
//        System.out.println();
//
//        userDao.dropUsersTable();
//
//        userDao.createUsersTable();
//        userDao.saveUser("Ms", "Smith", (byte) 32);
//        users = userDao.getAllUsers();
//        users.forEach(System.out::println); // 1 user
//        System.out.println();
//
//        userDao.cleanUsersTable();
//        users = userDao.getAllUsers();
//        users.forEach(System.out::println); // // ________
//        System.out.println();
//
//        userDao.saveUser("Ms", "Smith", (byte) 32);
//        userDao.saveUser("John", "Travolta", (byte) 46);
//        users = userDao.getAllUsers();
//        users.forEach(System.out::println); // 2 user
//        System.out.println();
//
//        userDao.removeUserById(1);
//        users = userDao.getAllUsers();
//        users.forEach(System.out::println); // 1 user

    }

}
