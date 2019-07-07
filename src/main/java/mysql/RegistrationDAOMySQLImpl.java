package mysql;

import com.sayed.toyregistrationsystem.HibernateUtils;
import java.util.List;
import model.Registration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author sayed
 */
public class RegistrationDAOMySQLImpl {

    public RegistrationDAOMySQLImpl() {
    }

    public Registration insert(Registration registration) {

        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(registration);
            transaction.commit();
            return retrieve(registration.getStudentID(), registration.getSectionID());
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

    public Registration retrieve(String studentID, int sectionID) {

        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            Query<Registration> createQuery = session.createQuery("from Registration where studentID = :studentID and sectionID = :sectionID", Registration.class);
            createQuery.setParameter("studentID", studentID);
            createQuery.setParameter("sectionID", sectionID);
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

    public List<Registration> retrieve(String id) {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            Query<Registration> createQuery = session.createQuery("from Registration where studentID = :id", Registration.class);
            createQuery.setParameter("id", id);
            return createQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    public Registration update(String studentID, int sectionID, Registration object) {

        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Query createQuery = session.createQuery("UPDATE Registration SET studentID = :update_studentID, sectionID = :updateSectionID WHERE studentID = :studentID AND sectionID = :sectionID");

            createQuery.setParameter("update_studentID", object.getStudentID());
            createQuery.setParameter("updateSectionID", object.getSectionID());
            createQuery.setParameter("studentID", studentID);
            createQuery.setParameter("sectionID", sectionID);

            int executeUpdate = createQuery.executeUpdate();
            transaction.commit();
            if (executeUpdate > 0) {
                return retrieve(object.getStudentID(), object.getSectionID());
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

    public boolean delete(String studentID, int sectionID) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Query createQuery = session.createQuery("DELETE FROM Registration WHERE studentID = :studentID AND sectionID = :sectionID");
            createQuery.setParameter("sectionID", sectionID);
            createQuery.setParameter("studentID", studentID);
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
