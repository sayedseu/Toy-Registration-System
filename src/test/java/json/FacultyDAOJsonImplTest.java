/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import java.util.List;
import java.util.Random;
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
public class FacultyDAOJsonImplTest {

    public FacultyDAOJsonImplTest() {
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
        Faculty object = new Faculty(getRandomString(4), getRandomString(15), getRandomString(10));
        FacultyDAOJsonImpl instance = new FacultyDAOJsonImpl();
        Faculty expResult = object;
        Faculty result = instance.insert(object);
        assertEquals(expResult, result);
    }

    @Test
    public void testRetrieveAll() {
        System.out.println("retrieve");
        FacultyDAOJsonImpl instance = new FacultyDAOJsonImpl();
        List<Faculty> result = instance.retrieve();
        assertEquals(instance.count(), result.size());

    }

    @Test
    public void testRetrieve_String() {
        System.out.println("retrieve");
        Faculty object = new Faculty(getRandomString(4), getRandomString(15), getRandomString(10));
        String data = object.getInitial();
        FacultyDAOJsonImpl instance = new FacultyDAOJsonImpl();
        instance.insert(object);
        Faculty expResult = object;
        Faculty result = instance.retrieve(data);
        assertEquals(expResult, result);
    }

    @Test
    public void testUpdate() {
        System.out.println("update");
        Faculty object = new Faculty(getRandomString(4), getRandomString(15), getRandomString(10));
        Faculty object1 = new Faculty(getRandomString(4), getRandomString(15), getRandomString(10));
        String data = object.getInitial();
        FacultyDAOJsonImpl instance = new FacultyDAOJsonImpl();
        instance.insert(object);
        Faculty expResult = object1;
        Faculty result = instance.update(data, object1);
        assertEquals(expResult, result);
    }

    @Test
    public void testDelete() {
        System.out.println("delete");
        Faculty object = new Faculty(getRandomString(4), getRandomString(15), getRandomString(10));
        String data = object.getInitial();
        FacultyDAOJsonImpl instance = new FacultyDAOJsonImpl();
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
