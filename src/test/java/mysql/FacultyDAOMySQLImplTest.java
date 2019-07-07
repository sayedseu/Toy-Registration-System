
package mysql;

import java.util.List;
import model.Faculty;
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
public class FacultyDAOMySQLImplTest {

    public FacultyDAOMySQLImplTest() {
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
        Faculty object = new Faculty("KMH", "Monirul Hasan", "Senior Lecturer");
        FacultyDAOMySQLImpl instance = new FacultyDAOMySQLImpl();
        Faculty expResult = object;
        Faculty result = instance.insert(object);
        assertEquals(expResult, result);

    }

    @Test
    public void testRetrieveAll() {
        System.out.println("retrieve");
        FacultyDAOMySQLImpl instance = new FacultyDAOMySQLImpl();
        Faculty object = new Faculty("SM", "Shahriar Monjur", "Chairman");
        Faculty object1 = new Faculty("KIA", "Kimia Aksir", "Senior Lecturer");
        instance.insert(object);
        instance.insert(object1);
        List<Faculty> result = instance.retrieve();
        assertEquals(4, result.size());

    }

    @Test
    public void testRetrieveByInitial() {
        System.out.println("retrieve");
        String data = "RIK";
        Faculty object = new Faculty("RIK", "Rezewan Islam Khan", "Senior Lecturer");
        FacultyDAOMySQLImpl instance = new FacultyDAOMySQLImpl();
        instance.insert(object);
        Faculty expResult = object;
        Faculty result = instance.retrieve(data);
        assertEquals(expResult, result);

    }

    @Test
    public void testUpdate() {
        System.out.println("update");
        String data = "BD";
        Faculty object = new Faculty("BD", "Badhon Das", "Senior Lecturer");
        Faculty object1 = new Faculty("RB", "Rajon Bardhan", "Senior Lecturer");
        FacultyDAOMySQLImpl instance = new FacultyDAOMySQLImpl();
        instance.insert(object);
        Faculty expResult = object1;
        Faculty result = instance.update(data, object1);
        assertEquals(expResult, result);
    }

    @Test
    public void testDelete() {
        System.out.println("delete");
        String data = "SM";
        Faculty object = new Faculty("SM", "Shahriar Monjur", "Chairman");
        FacultyDAOMySQLImpl instance = new FacultyDAOMySQLImpl();
        instance.insert(object);
        boolean expResult = true;
        boolean result = instance.delete(data);
        assertEquals(expResult, result);

    }

}
