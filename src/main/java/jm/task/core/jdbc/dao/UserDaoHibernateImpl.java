package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


public class UserDaoHibernateImpl extends Util implements UserDao {

    private Transaction transaction = null;

    public UserDaoHibernateImpl() {

    }



    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()){
            transaction =session.beginTransaction();

            session.createSQLQuery("CREATE TABLE IF NOT EXISTS user (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NOT NULL,\n" +
                "  `lastname` VARCHAR(45) NOT NULL,\n" +
                "  `age` INT NOT NULL,\n" +
                "  PRIMARY KEY (`id`));").executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        // var - 2
//        openTransactionSession();
//
//        Session session = openSession();
//        session.createSQLQuery("CREATE TABLE IF NOT EXISTS user (\n" +
//                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
//                "  `name` VARCHAR(45) NOT NULL,\n" +
//                "  `lastname` VARCHAR(45) NOT NULL,\n" +
//                "  `age` INT NOT NULL,\n" +
//                "  PRIMARY KEY (`id`));").executeUpdate();
//        closeTransactionSession();
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()){
            transaction =session.beginTransaction();

            Query query = session.createSQLQuery("drop table if exists user").addEntity(User.class);
            query.executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        // var - 2
//        openTransactionSession();
//
//        Session session = openSession();
//        Query query = session.createSQLQuery("DROP TABLE IF EXISTS user").addEntity(User.class);
//        query.executeUpdate();
//
//        closeTransactionSession();
//        shutdown();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Session session = getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Query query = session.createQuery("delete from User where id = :paramName");
            query.setParameter("paramName", id);
            query.executeUpdate();
            transaction.commit();
        }catch (Exception e) {
            if (transaction!=null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List list = null;
        try (Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            Query query = session.createQuery("from User");
            list = query.list();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            transaction.commit();
        }catch (Exception e) {
            if (transaction!=null) {
                transaction.rollback();
            }
        }
    }
}
