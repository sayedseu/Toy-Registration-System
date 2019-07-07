package mysql;

import com.sayed.toyregistrationsystem.DAO;
import com.sayed.toyregistrationsystem.HibernateUtils;
import java.util.List;
import model.Course;
import model.Faculty;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author sayed
 */
public class CourseDAOMySQLImpl implements DAO<Course, String> {

    @Override
    public Course insert(Course object) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(object);
            transaction.commit();
            return retrieve(object.getCode());
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
    public List<Course> retrieve() {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            Query<Course> query = session.createQuery("from Course", Course.class);
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
    public Course retrieve(String data) {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            Query<Course> createQuery = session.createQuery("from Course where code = :code", Course.class);
            createQuery.setParameter("code", data);
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
    public Course update(String data, Course object) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Query createQuery = session.createQuery("UPDATE Course SET code = :update_code, title = :title, credit = :credit WHERE code = :code");
            createQuery.setParameter("update_code", object.getCode());
            createQuery.setParameter("title", object.getTitle());
            createQuery.setParameter("credit", object.getCredit());
            createQuery.setParameter("code", data);

            int executeUpdate = createQuery.executeUpdate();
            transaction.commit();
            if (executeUpdate > 0) {
                return retrieve(object.getCode());
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
            Query createQuery = session.createQuery("DELETE FROM Course WHERE code = :code");
            createQuery.setParameter("code", data);
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
