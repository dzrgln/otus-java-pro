package ru.otus.hw3.generics;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoxTest {
    private final Box<Apple> appleBox1 = new Box<>();
    private final Box<Apple> appleBox2 = new Box<>();
    private final Box<Orange> orangeBox1 = new Box<>();
    private final Box<Orange> orangeBox2 = new Box<>();

    @BeforeEach
    public void beforeEach(){
        appleBox1.add(new Apple(1));
        appleBox1.add(new Apple(2));
        appleBox1.add(new Apple(4));

        orangeBox1.add(new Orange(3));
        orangeBox1.add(new Orange(5));

        appleBox2.add(new Apple(1.2));

        orangeBox2.add(new Orange(4.2));
        orangeBox2.add(new Orange(1));
        orangeBox2.add(new Orange(6));
    }

    @AfterEach
    public void clearBoxes(){
        appleBox1.clear();
        appleBox2.clear();
        orangeBox1.clear();
        orangeBox2.clear();
    }

    @Test
    public void testAddingFruits(){
        assertEquals(3, appleBox1.size());
        assertEquals(2, orangeBox1.size());
        assertEquals(1, appleBox2.size());
        assertEquals(3, orangeBox2.size());
    }

    @Test
    public void testBoxWeight(){
        double weightAppleBox = appleBox1.weight();
        double weightOrangeBox = orangeBox1.weight();

        assertEquals(7, weightAppleBox);
        assertEquals(8, weightOrangeBox);
    }

    @Test
    public void testPouring(){
        appleBox1.pour(appleBox2);
        orangeBox2.pour(orangeBox1);

        assertEquals(4, appleBox1.size());
        assertEquals(5, orangeBox2.size());
        assertEquals(0, appleBox2.size());
        assertEquals(0, orangeBox1.size());
    }

    @Test
    public void testPouringFromNull(){
        Box<Apple> nullBox = null;
        assertThrows(NullPointerException.class, () -> appleBox1.pour(nullBox));
    }

    @Test
    public void testPouringFromSameBox(){
        assertThrows(IllegalArgumentException.class, () -> appleBox1.pour(appleBox1));
    }

    @Test
    public void testComparing(){
        Box<Apple> sameAppleBox1 = new Box<>();
        sameAppleBox1.add(new Apple(7));

        assertTrue(appleBox1.compare(sameAppleBox1));
        assertFalse(appleBox1.compare(appleBox2));
    }

    @Test
    public void testComparingWithNull(){
        Box<Apple> nullBox = null;
        assertThrows(NullPointerException.class, () -> appleBox1.compare(nullBox));
    }
}
