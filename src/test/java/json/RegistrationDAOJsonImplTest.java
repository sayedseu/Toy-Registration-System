package json;

import java.util.Random;
import model.Registration;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author sayed
 */
public class RegistrationDAOJsonImplTest {

    public RegistrationDAOJsonImplTest() {
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
        Registration registration = new Registration(String.valueOf(getRandomNumber()), getRandomNumber());
        RegistrationDAOJsonImpl instance = new RegistrationDAOJsonImpl();
        Registration expResult = registration;
        Registration result = instance.insert(registration);
        assertEquals(expResult, result);
    }

    @Test
    public void testRetrieveBy_studentID_and_sectionID() {
        System.out.println("retrieve");
        Registration registration = new Registration(String.valueOf(getRandomNumber()), getRandomNumber());
        String studentID = registration.getStudentID();
        int sectionID = registration.getSectionID();
        RegistrationDAOJsonImpl instance = new RegistrationDAOJsonImpl();
        instance.insert(registration);
        Registration expResult = registration;
        Registration result = instance.retrieve(studentID, sectionID);
        assertEquals(expResult, result);
    }

    @Test
    public void testUpdate() {
        System.out.println("update");
        Registration registration = new Registration(String.valueOf(getRandomNumber()), getRandomNumber());
        Registration registration1 = new Registration(String.valueOf(getRandomNumber()), getRandomNumber());
        String studentID = registration.getStudentID();
        int sectionID = registration.getSectionID();
        RegistrationDAOJsonImpl instance = new RegistrationDAOJsonImpl();
        instance.insert(registration);
        Registration expResult = registration1;
        Registration result = instance.update(studentID, sectionID, registration1);
        assertEquals(expResult, result);
    }

    @Test
    public void testDelete() {
        System.out.println("delete");
        Registration registration = new Registration(String.valueOf(getRandomNumber()), getRandomNumber());
        String studentID = registration.getStudentID();
        int sectionID = registration.getSectionID();
        RegistrationDAOJsonImpl instance = new RegistrationDAOJsonImpl();
        instance.insert(registration);
        boolean expResult = true;
        boolean result = instance.delete(studentID, sectionID);
        assertEquals(expResult, result);
    }

    private int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(Integer.MAX_VALUE);
    }

}
