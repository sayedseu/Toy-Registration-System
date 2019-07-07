
package mysql;

import java.util.List;
import model.Section;
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
public class SectionDAOMySQLImplTest {
    
    public SectionDAOMySQLImplTest() {
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
        Section object = new Section(1,1,51,30, "CSE4047","KMH");
        SectionDAOMySQLImpl instance = new SectionDAOMySQLImpl();
        Section expResult = object;
        Section result = instance.insert(object);
        assertEquals(expResult, result);
    }

    @Test
    public void testRetrieveAll() {
        System.out.println("retrieve");
        Section object = new Section(2,2,52,31, "CSE3027","RIK");
        Section object2 = new Section(3,2,52,25, "CSE3027","RIK");
        SectionDAOMySQLImpl instance = new SectionDAOMySQLImpl();
        instance.insert(object);
        instance.insert(object2);
        List<Section> result = instance.retrieve();
        assertEquals(3, result.size());
    }

    @Test
    public void testRetrieveById() {
        System.out.println("retrieve");
        Section object = new Section(4,4,54,30, "CSE2026","SHAK");
        Integer data = 4;
        SectionDAOMySQLImpl instance = new SectionDAOMySQLImpl();
        instance.insert(object);
        Section expResult = object;
        Section result = instance.retrieve(data);
        assertEquals(expResult, result);
    }

    @Test
    public void testUpdate() {
        System.out.println("update");
        Section object = new Section(5,3,55,35, "CSE2025","SHAK");
        Section object1 = new Section(6,1,55,30, "CSE2021","SHAK");
        Integer data = 5;
        SectionDAOMySQLImpl instance = new SectionDAOMySQLImpl();
        instance.insert(object);
        Section expResult = object1;
        Section result = instance.update(data, object1);
        assertEquals(expResult, result);
    }

    @Test
    public void testDelete() {
        System.out.println("delete");
        Section object1 = new Section(7,1,55,30, "CSE2022","SHAK");
        Integer data = 7;
        SectionDAOMySQLImpl instance = new SectionDAOMySQLImpl();
        instance.insert(object1);
        boolean expResult = true;
        boolean result = instance.delete(data);
        assertEquals(expResult, result);
    }
    
}
