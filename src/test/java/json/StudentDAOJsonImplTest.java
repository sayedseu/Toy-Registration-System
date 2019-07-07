package json;

import java.util.List;
import java.util.Random;
import model.Student;
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
public class StudentDAOJsonImplTest {

    public StudentDAOJsonImplTest() {
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
        Random random = new Random();
        Student object = new Student(String.format("%013d", random.nextInt(Integer.MAX_VALUE)), "Nobody");;
        StudentDAOJsonImpl instance = new StudentDAOJsonImpl();
        Student expResult = object;
        Student result = instance.insert(object);
        assertEquals(expResult, result);
    }

    @Test
    public void testRetrieveAll() {
        System.out.println("retrieve");
        StudentDAOJsonImpl instance = new StudentDAOJsonImpl();
        assertEquals(instance.count(), instance.retrieve().size());

    }

    @Test
    public void testRetrieveById() {
        System.out.println("retrieve");
        Random random = new Random();
        Student object = new Student(String.format("%013d", random.nextInt(Integer.MAX_VALUE)), "Nobody");;
        String data = object.getId();
        StudentDAOJsonImpl instance = new StudentDAOJsonImpl();
        instance.insert(object);
        Student expResult = object;
        Student result = instance.retrieve(data);
        assertEquals(expResult, result);
    }

    @Test
    public void testUpdate() {
        System.out.println("update");
        Random random = new Random();
        Student object = new Student(String.format("%013d", random.nextInt(Integer.MAX_VALUE)), "Nobody");
        Student object1 = new Student(String.format("%013d", random.nextInt(Integer.MAX_VALUE)), "Nobody");;
        String data = object.getId();
        StudentDAOJsonImpl instance = new StudentDAOJsonImpl();
        instance.insert(object);
        Student expResult = object1;
        Student result = instance.update(data, object1);
        assertEquals(expResult, result);
    }

    @Test
    public void testDelete() {
        System.out.println("delete");
        Random random = new Random();
        Student object = new Student(String.format("%013d", random.nextInt(Integer.MAX_VALUE)), "Nobody");
        String data = object.getId();
        StudentDAOJsonImpl instance = new StudentDAOJsonImpl();
        instance.insert(object);
        boolean expResult = true;
        boolean result = instance.delete(data);
        assertEquals(expResult, result);
    }

}
