
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

public class MapExerciseTest {
    static Lab me;

    @BeforeClass
    public static void setUp() {
        me = new Lab();
    }

    /**
     * When a map is created, it should not be null.
     */
    @Test
    public void createMapTest() {
        Map<Integer, String> map = me.createMap();
        Assert.assertNotNull(map);
    }

    /**
     * When a map is first created, its size should be 0.
     */
    @Test
    public void getSizeTest1() {
        Map<Integer, String> map = me.createMap();
        if (map == null) {
            Assert.fail();
        } else {
            Assert.assertEquals(0, me.getSize(map));
        }
    }

    /**
     * When a key value pair is inserted into an empty map, its size should be 1.
     */
    @Test
    public void getSizeTest2() {
        Map<Integer, String> map = me.createMap();
        if (map == null) {
            Assert.fail();
        } else {
            me.addKeyValuePair(map, 1, "banana");
            Assert.assertEquals(1, me.getSize(map));
        }
    }

    /**
     * When a key value pair is inserted into an empty map, the value should be
     * retrievable using the key.
     */
    @Test
    public void addKeyValuePairTest1() {
        Map<Integer, String> map = me.createMap();
        if (map == null) {
            Assert.fail();
        } else {
            me.addKeyValuePair(map, 1, "banana");
            Assert.assertEquals("banana", me.getValueFromKey(map, 1));
        }
    }

    /**
     * When two key value pairs are inserted into an empty map, all the values
     * should be retrievable.
     */
    @Test
    public void addKeyValuePairTest2() {
        Map<Integer, String> map = me.createMap();
        if (map == null) {
            Assert.fail();
        } else {
            me.addKeyValuePair(map, 1, "banana");
            me.addKeyValuePair(map, 2, "pear");
            Assert.assertEquals("banana", me.getValueFromKey(map, 1));
            Assert.assertEquals("pear", me.getValueFromKey(map, 2));
        }
    }

    /**
     * When a key value pair from a map is removed, the key should no longer be
     * accessible.
     */
    @Test
    public void removeKeyValuePairTest1() {
        Map<Integer, String> map = me.createMap();
        if (map == null) {
            Assert.fail();
        } else {
            me.addKeyValuePair(map, 1, "banana");
            me.addKeyValuePair(map, 2, "pear");
            me.removeKeyValuePair(map, 2);
            Assert.assertFalse(map.containsKey(2));
        }
    }

    /**
     * When the value associated with a certain key is overwritten, retrieving the
     * value of that key should produce
     * the new value.
     */
    @Test
    public void overwriteValueTest1() {
        Map<Integer, String> map = me.createMap();
        if (map == null) {
            Assert.fail();
        } else {
            me.addKeyValuePair(map, 1, "banana");
            me.addKeyValuePair(map, 2, "pear");
            me.overwriteValue(map, 2, "apple");
            Assert.assertTrue(map.containsKey(2));
            Assert.assertEquals("apple", me.getValueFromKey(map, 2));
        }

    }
}
