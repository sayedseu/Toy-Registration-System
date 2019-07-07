package mysql;

import java.util.Random;
import model.Student;
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
public class StudentDAOMySQLImplTest {
    
    public StudentDAOMySQLImplTest() {
        
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
        StudentDAOMySQLImpl instance = new StudentDAOMySQLImpl();
        Random random = new Random();
        Student expResult = new Student(String.format("%013d", random.nextInt(Integer.MAX_VALUE)), "Nobody");
        Student result = instance.insert(expResult);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testRetrieveAll() {
        StudentDAOMySQLImpl instance = new StudentDAOMySQLImpl();
        Random random = new Random();
        Student object1 = new Student(String.format("%013d", random.nextInt(Integer.MAX_VALUE)), "Nobody");
        Student object2 = new Student(String.format("%013d", random.nextInt(Integer.MAX_VALUE)), "Nobody");
        instance.insert(object1);
        instance.insert(object2);
        assertEquals(3, instance.retrieve().size());
    }
    
    @Test
    public void testRetrieveById() {
        StudentDAOMySQLImpl instance = new StudentDAOMySQLImpl();
        System.out.println("retrieve");
        Random random = new Random();
        Student object = new Student(String.format("%013d", random.nextInt(Integer.MAX_VALUE)), "Nobody");
        instance.insert(object);
        Student expResult = object;
        Student result = instance.retrieve(object.getId());
        assertEquals(expResult, result);
    }
    
    @Test
    public void testUpdate() {
        System.out.println("update");
        StudentDAOMySQLImpl instance = new StudentDAOMySQLImpl();
        Random random = new Random();
        Student object = new Student(String.format("%013d", random.nextInt(Integer.MAX_VALUE)), "Nobody");
        instance.insert(object);
        Student expResult = new Student(String.format("%013d", random.nextInt(Integer.MAX_VALUE)), "Nobody");
        Student result = instance.update(object.getId(), expResult);
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testDelete() {
        StudentDAOMySQLImpl instance = new StudentDAOMySQLImpl();
        Random random = new Random();
        Student object = new Student(String.format("%013d", random.nextInt(Integer.MAX_VALUE)), "Nobody");
        instance.insert(object);
        assertEquals(true, instance.delete(object.getId()));
    }
    
}
