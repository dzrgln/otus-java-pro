package ru.otus.hw5;

import lombok.RequiredArgsConstructor;
import ru.otus.hw5.annotations.After;
import ru.otus.hw5.annotations.Before;
import ru.otus.hw5.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {

    public static void main(String[] args) {
        runTest(AnyServiceTest.class);
    }

    private static void runTest(Class<?> classForTest) {
        TestState testState = new TestState();
        prepareTestState(classForTest, testState);
        runAllTests(classForTest, testState);
        printTestStatistic(testState);
    }

    private static void prepareTestState(Class<?> testClass, TestState testState) {
        for (Method method : testClass.getMethods()) {
            validateAnnotations(method);
            if (method.isAnnotationPresent(Before.class)) {
                testState.beforeMethods.add(method);
            } else if (method.isAnnotationPresent(After.class)) {
                testState.afterMethods.add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                testState.testsMethods.add(method);
            }
        }
    }

    private static void runAllTests(Class<?> classForTest, TestState testState) {
        for (Method method : testState.testsMethods) {
            Object object = createObject(classForTest);
            boolean wasError = false;
            try {
                for (Method beforeMethod : testState.beforeMethods) {
                    beforeMethod.invoke(object);
                }
                method.invoke(object);
                for (Method afterMethod : testState.afterMethods) {
                    afterMethod.invoke(object);
                }
            } catch (Exception e) {
                wasError = true;
            }
            if (wasError) {
                testState.registerFailedTest();
            }
        }
    }

    private static void printTestStatistic(TestState testState) {
        int allTests = testState.testsMethods.size();
        int successfulTests = allTests - testState.failedTests;

        System.out.printf("Всего тестов %d: \n", allTests);
        System.out.printf("Тестов пройдено: %d \n", successfulTests);
        System.out.printf("Тестов упало %d: \n", testState.failedTests);
    }

    private static void validateAnnotations(Method method) {
        if (method.getAnnotations().length > 1) {
            throw new IllegalArgumentException(String.format("Для метода %s указано больше одной аннотации", method.getName()));
        }
    }

    private static Object createObject(Class<?> classForTest) {
        Object object = null;
        try {
            object = classForTest.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    @RequiredArgsConstructor
    public static class TestState {
        private final List<Method> beforeMethods = new ArrayList<>();
        private final List<Method> afterMethods = new ArrayList<>();
        private final List<Method> testsMethods = new ArrayList<>();
        private int failedTests;

        void registerFailedTest() {
            failedTests++;
        }
    }
}
