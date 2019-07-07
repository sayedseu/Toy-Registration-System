package mysql;

import com.sayed.toyregistrationsystem.DAO;
import com.sayed.toyregistrationsystem.HibernateUtils;
import java.util.List;
import model.Faculty;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author sayed
 */
public class FacultyDAOMySQLImpl implements DAO<Faculty, String> {

    @Override
    public Faculty insert(Faculty object) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(object);
            transaction.commit();
            return retrieve(object.getInitial());
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return null;
    }

    @Override
    public List<Faculty> retrieve() {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            Query<Faculty> query = session.createQuery("from Faculty", Faculty.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    @Override
    public Faculty retrieve(String data) {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            Query<Faculty> createQuery = session.createQuery("from Faculty where initial = :initial", Faculty.class);
            createQuery.setParameter("initial", data);
            return createQuery.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    @Override
    public Faculty update(String data, Faculty object) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Query createQuery = session.createQuery("UPDATE Faculty SET initial = :update_initial, name = :name, rank = :rank WHERE initial = :initial");
            createQuery.setParameter("update_initial", object.getInitial());
            createQuery.setParameter("name", object.getName());
            createQuery.setParameter("rank", object.getRank());
            createQuery.setParameter("initial", data);

            int executeUpdate = createQuery.executeUpdate();
            transaction.commit();
            if (executeUpdate > 0) {
                return retrieve(object.getInitial());
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    @Override
    public boolean delete(String data) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Query createQuery = session.createQuery("DELETE FROM Faculty WHERE initial = :initial");
            createQuery.setParameter("initial", data);
            int executeUpdate = createQuery.executeUpdate();
            transaction.commit();
            if (executeUpdate > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return false;
    }

}
