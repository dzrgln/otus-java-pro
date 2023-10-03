package ru.otus.hw5;

import ru.otus.hw5.annotations.After;
import ru.otus.hw5.annotations.Before;
import ru.otus.hw5.annotations.Test;

public class AnyServiceTest {

    @Before
    public void before1() {
        System.out.println("-- Before test 1");
    }

    @Before
    public void before2() {
        System.out.println("-- Before test 2");
    }

    @Test
    public void test1() {
        System.out.println("Do test 1");
    }

    @Test
    public void test2() {
        System.out.println("Do test 2");

    }

    @Test
    public void testFail(){
        throw new NullPointerException();
    }

    @After
    public void after(){
        System.out.println("-- After test");
    }
}
