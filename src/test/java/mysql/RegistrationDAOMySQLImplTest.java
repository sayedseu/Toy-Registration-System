package mysql;

import java.util.List;
import model.Registration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sayed
 */
public class RegistrationDAOMySQLImplTest {

    public RegistrationDAOMySQLImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testInsert() {
        System.out.println("insert");
        Registration registration = new Registration("1234", 1);
        RegistrationDAOMySQLImpl instance = new RegistrationDAOMySQLImpl();
        Registration expResult = registration;
        Registration result = instance.insert(registration);
        assertEquals(expResult, result);
    }

    @Test
    public void testRetrieveBy_StudentId_And_SectionId() {
        System.out.println("retrieve");
        Registration object = new Registration("123", 3);
        String studentID = object.getStudentID();
        int sectionID = object.getSectionID();
        RegistrationDAOMySQLImpl instance = new RegistrationDAOMySQLImpl();
        instance.insert(object);
        Registration expResult = object;
        Registration result = instance.retrieve(studentID, sectionID);
        assertEquals(expResult, result);
    }

    @Test
    public void testRetrieveBy_StudentID() {
        System.out.println("retrieve");
        Registration object = new Registration("12345678910", 4);
        String id = object.getStudentID();
        RegistrationDAOMySQLImpl instance = new RegistrationDAOMySQLImpl();
        instance.insert(object);
        List<Registration> result = instance.retrieve(id);
        assertEquals(1, result.size());
    }

    @Test
    public void testUpdateBy_StudentId_And_SectionId() {
        System.out.println("update");
        Registration object = new Registration("1", 3);
        Registration object1 = new Registration("2", 4);
        String studentID = object.getStudentID();
        int sectionID = object.getSectionID();
        RegistrationDAOMySQLImpl instance = new RegistrationDAOMySQLImpl();
        instance.insert(object);
        Registration expResult = object1;
        Registration result = instance.update(studentID, sectionID, object1);
        assertEquals(expResult.getStudentID(), result.getStudentID());
    }

    @Test
    public void testDeleteBy_StudentId_And_SectionId() {
        System.out.println("delete");
        Registration object1 = new Registration("222", 4);
        String studentID = object1.getStudentID();
        int sectionID = object1.getSectionID();
        RegistrationDAOMySQLImpl instance = new RegistrationDAOMySQLImpl();
        instance.insert(object1);
        boolean expResult = true;
        boolean result = instance.delete(studentID, sectionID);
        assertEquals(expResult, result);
    }

}
