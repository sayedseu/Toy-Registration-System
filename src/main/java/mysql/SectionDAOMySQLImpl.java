package mysql;

import com.sayed.toyregistrationsystem.DAO;
import com.sayed.toyregistrationsystem.HibernateUtils;
import java.util.List;
import model.Section;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author sayed
 */
public class SectionDAOMySQLImpl implements DAO<Section, Integer> {

    @Override
    public Section insert(Section object) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(object);
            transaction.commit();
            return retrieve(object.getSectionID());
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
    public List<Section> retrieve() {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            Query<Section> query = session.createQuery("from Section", Section.class);
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
    public Section retrieve(Integer data) {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            Query<Section> createQuery = session.createQuery("from Section where sectionID = :id", Section.class);
            createQuery.setParameter("id", data);
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
    public Section update(Integer data, Section object) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Query createQuery = session.createQuery("UPDATE Section SET "
                    + "sectionID = :update_sectionID"
                    + ", sectionNumber = :sectionNumber"
                    + ", semesterNumber = :semesterNumber"
                    + ", seatLimit = :seatLimit"
                    + ", code = :code"
                    + ", initial = :initial"
                    + " WHERE sectionID = :sectionID");
            createQuery.setParameter("update_sectionID", object.getSectionID());
            createQuery.setParameter("sectionNumber", object.getSectionNumber());
            createQuery.setParameter("semesterNumber", object.getSemesterNumber());
            createQuery.setParameter("seatLimit", object.getSeatLimit());
            createQuery.setParameter("code", object.getCode());
            createQuery.setParameter("initial", object.getInitial());
            createQuery.setParameter("sectionID", data);

            int executeUpdate = createQuery.executeUpdate();
            transaction.commit();
            if (executeUpdate > 0) {
                return retrieve(object.getSectionID());
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
    public boolean delete(Integer data) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Query createQuery = session.createQuery("DELETE FROM Section WHERE sectionID = :sectionID");
            createQuery.setParameter("sectionID", data);
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
