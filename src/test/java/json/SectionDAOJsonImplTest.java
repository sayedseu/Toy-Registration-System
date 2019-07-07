package json;

import java.util.List;
import java.util.Random;
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
public class SectionDAOJsonImplTest {

    public SectionDAOJsonImplTest() {
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
        Section object = new Section(getRandomNumber(), getRandomNumber(), getRandomNumber(), getRandomNumber(), "CSE3027", "RIK");
        SectionDAOJsonImpl instance = new SectionDAOJsonImpl();
        Section expResult = object;
        Section result = instance.insert(object);
        assertEquals(expResult, result);
    }

    @Test
    public void testRetrieveAll() {
        System.out.println("retrieve");
        SectionDAOJsonImpl instance = new SectionDAOJsonImpl();
        List<Section> result = instance.retrieve();
        assertEquals(instance.count(), result.size());
    }

    @Test
    public void testRetrieveById() {
        System.out.println("retrieve");
        Section object = new Section(getRandomNumber(), getRandomNumber(), getRandomNumber(), getRandomNumber(), "CSE3027", "RIK");
        Integer data = object.getSectionID();
        SectionDAOJsonImpl instance = new SectionDAOJsonImpl();
        instance.insert(object);
        Section expResult = object;
        Section result = instance.retrieve(data);
        assertEquals(expResult, result);
    }

    @Test
    public void testUpdate() {
        System.out.println("update");
        Section object = new Section(getRandomNumber(), getRandomNumber(), getRandomNumber(), getRandomNumber(), "CSE3027", "RIK");
        Section object1 = new Section(getRandomNumber(), getRandomNumber(), getRandomNumber(), getRandomNumber(), "CSE3027", "RIK");
        Integer data = object.getSectionID();
        SectionDAOJsonImpl instance = new SectionDAOJsonImpl();
        instance.insert(object);
        Section expResult = object1;
        Section result = instance.update(data, object1);
        assertEquals(expResult, result);
    }

    @Test
    public void testDelete() {
        System.out.println("delete");
        Section object = new Section(getRandomNumber(), getRandomNumber(), getRandomNumber(), getRandomNumber(), "CSE3027", "RIK");
        Integer data = object.getSectionID();
        SectionDAOJsonImpl instance = new SectionDAOJsonImpl();
        instance.insert(object);
        boolean expResult = true;
        boolean result = instance.delete(data);
        assertEquals(expResult, result);
    }

    private int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(Integer.MAX_VALUE);
    }
}
