
package mysql;

import java.util.List;
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
public class CourseDAOMySQLImplTest {
    
    public CourseDAOMySQLImplTest() {
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
        Course object = new Course("CSE4047","Advance Java",3.0);
        CourseDAOMySQLImpl instance = new CourseDAOMySQLImpl();
        Course expResult = object;
        Course result = instance.insert(object);
        assertEquals(expResult, result);
    }

    @Test
    public void testRetrieveAll() {
        System.out.println("retrieve");
        Course object = new Course("CSE1011","Computer Fundamental",3.0);
        Course object1 = new Course("CSE2021","Algorithm",3.0);
        CourseDAOMySQLImpl instance = new CourseDAOMySQLImpl();
        instance.insert(object);
        instance.insert(object1);
        List<Course> result = instance.retrieve();
        assertEquals(4, result.size());
    }

    @Test
    public void testRetrieveByCode() {
        System.out.println("retrieve");
        Course object = new Course("CSE3027","Embeded System",3.0);
        String data = "CSE3027";
        CourseDAOMySQLImpl instance = new CourseDAOMySQLImpl();
        instance.insert(object);
        Course expResult = object;
        Course result = instance.retrieve(data);
        assertEquals(expResult, result);
    }

    @Test
    public void testUpdate() {
        System.out.println("update");
        Course object = new Course("CSE3035","Information System Design",3.0);
        Course object1 = new Course("CSE3036","Information System Design Lab",1.0);
        String data = "CSE3035";
        CourseDAOMySQLImpl instance = new CourseDAOMySQLImpl();
        instance.insert(object);
        Course expResult = object1;
        Course result = instance.update(data, object1);
        assertEquals(expResult, result);
    }

    @Test
    public void testDelete() {
        System.out.println("delete");
        Course object = new Course("CSE2026","Advance Algorithm",3.0);
        String data = "CSE2026";
        CourseDAOMySQLImpl instance = new CourseDAOMySQLImpl();
        instance.insert(object);
        boolean expResult = true;
        boolean result = instance.delete(data);
        assertEquals(expResult, result);
    }
    
}
