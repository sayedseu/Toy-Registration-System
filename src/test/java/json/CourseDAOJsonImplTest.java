
package json;

import java.util.List;
import java.util.Random;
import model.Course;
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
public class CourseDAOJsonImplTest {

    public CourseDAOJsonImplTest() {
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
        Course object = new Course(getRandomString(8), getRandomString(10), 3.0);
        CourseDAOJsonImpl instance = new CourseDAOJsonImpl();
        Course expResult = object;
        Course result = instance.insert(object);
        assertEquals(expResult, result);
    }

    @Test
    public void testRetrieveAll() {
        System.out.println("retrieve");
        CourseDAOJsonImpl instance = new CourseDAOJsonImpl();
        List<Course> result = instance.retrieve();
        assertEquals(instance.count(), result.size());
    }

    @Test
    public void testRetrieveByCode() {
        System.out.println("retrieve");
        Course object = new Course(getRandomString(8), getRandomString(10), 3.0);
        String data = object.getCode();
        CourseDAOJsonImpl instance = new CourseDAOJsonImpl();
        instance.insert(object);
        Course expResult = object;
        Course result = instance.retrieve(data);
        assertEquals(expResult, result);
    }

    @Test
    public void testUpdate() {
        System.out.println("update");
        Course object = new Course(getRandomString(8), getRandomString(10), 3.0);
        Course object1 = new Course(getRandomString(8), getRandomString(10), 3.0);
        String data = object.getCode();
        CourseDAOJsonImpl instance = new CourseDAOJsonImpl();
        instance.insert(object);
        Course expResult = object1;
        Course result = instance.update(data, object1);
        assertEquals(expResult, result);
    }

    @Test
    public void testDelete() {
        System.out.println("delete");
        Course object = new Course(getRandomString(8), getRandomString(10), 3.0);
        String data = object.getCode();
        CourseDAOJsonImpl instance = new CourseDAOJsonImpl();
        instance.insert(object);
        boolean expResult = true;
        boolean result = instance.delete(data);
        assertEquals(expResult, result);
    }

    private String getRandomString(int n) {

        int lowerLimit = 97;

        int upperLimit = 122;

        Random random = new Random();

        StringBuffer r = new StringBuffer(n);

        for (int i = 0; i < n; i++) {

            int nextRandomChar = lowerLimit
                    + (int) (random.nextFloat()
                    * (upperLimit - lowerLimit + 1));

            r.append((char) nextRandomChar);
        }

        return r.toString();
    }

}
