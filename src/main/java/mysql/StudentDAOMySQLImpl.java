package mysql;

import com.sayed.toyregistrationsystem.DAO;
import com.sayed.toyregistrationsystem.HibernateUtils;
import java.util.List;
import model.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author sayed
 */
public class StudentDAOMySQLImpl implements DAO<Student, String> {

    @Override
    public Student insert(Student object) {

        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(object);
            transaction.commit();
            return retrieve(object.getId());
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
    public List<Student> retrieve() {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            Query<Student> query = session.createQuery("from Student", Student.class);
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
    public Student retrieve(String id) {

        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            Query<Student> createQuery = session.createQuery("from Student where id = :id", Student.class);
            createQuery.setParameter("id", id);
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
    public Student update(String id, Student object) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Query createQuery = session.createQuery("UPDATE Student SET id = :student_id, name = :student_name WHERE id = :id");
            createQuery.setParameter("student_id", object.getId());
            createQuery.setParameter("student_name", object.getName());
            createQuery.setParameter("id", id);
            int executeUpdate = createQuery.executeUpdate();
            transaction.commit();
            if (executeUpdate > 0) {
                return retrieve(object.getId());
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
            Query createQuery = session.createQuery("DELETE FROM Student WHERE id = :id");
            createQuery.setParameter("id", data);
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
