package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.util.Properties;


public class Util {
    // реализуйте настройку соеденения с БД
    private static SessionFactory sessionFactory;

    // attempt - 3
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/jdbctesttable?useSSL=false");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "root");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }


    // Hibernate - kudidreS
    private Session session;
    private Transaction transaction;

//    private static SessionFactory buildSessionFactory() {
//        try {
//            return new Configuration().configure().buildSessionFactory();
//        } catch (Throwable e) {
//            System.err.println("Initial err Util clss");
//            throw new ExceptionInInitializerError(e);
//        }
//    }

    public Session getSession() {
        return session;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public Session openSession() {
        return getSessionFactory().openSession();
    }

    public Session openTransactionSession() {
        session = openSession();
        transaction = session.beginTransaction();
        return session;
    }

    public void cloeSession() {
        session.close();
    }

    public void closeTransactionSession() {
        transaction.commit();
        cloeSession();
    }

    public static void shutdown() {
        getSessionFactory().close();
    }


    // Hibernate - 2
//    private static SessionFactory sessionFactory;
//    private static StandardServiceRegistry registry;
//
//    private static SessionFactory getSessionFactory() {
//        if (sessionFactory == null) {
//            try {
//                StandardServiceRegistryBuilder registryBuilder =
//                        new StandardServiceRegistryBuilder();
//
//                Map<String, String> setting = new HashMap<>();
//
//
//            } catch (Exception e) {
//                System.out.println("SessionFactory created failed");
//            }
//            if (sessionFactory != null) {
//                StandardServiceRegistryBuilder.destroy(registry);
//            }
//        }
//        return sessionFactory;
//    }






    // JDBC
//    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
//    private static final String DB_URL = "jdbc:mysql://localhost:3306/jdbctesttable?useSSL=false";
//    private static final String DB_USERNAME = "root";
//    private static final String DB_PASSWORD = "root";
//
    public Connection getConnection() {
        Connection connection = null;
//        try {
//            Class.forName(DB_DRIVER);
//            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }
        return connection;
    }

    /*
    В класс Util должна быть добавлена конфигурация
    для Hibernate (рядом с JDBC), без использования xml
     */
}
