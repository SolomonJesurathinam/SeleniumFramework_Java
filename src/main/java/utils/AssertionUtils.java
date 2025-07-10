package utils;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Field;

public class AssertionUtils {

    private static final LoggerWrapper logger = new LoggerWrapper(AssertionUtils.class);

    /**
     * Hard assert equality with logging.
     */
    public static <T> void assertEquals(T actual, T expected, String description) {
        logger.info(String.format("Verifying: %s", description));
        try {
            Assert.assertEquals(actual, expected);
            logger.info(String.format("Assertion passed: %s | Expected: %s | Actual: %s", description, expected, actual));
        } catch (AssertionError e) {
            logger.error(String.format("Assertion failed: %s | Expected: %s | Actual: %s", description, expected, actual));
            throw e;
        }
    }

    /**
     * Hard assert condition is true.
     */
    public static void assertTrue(boolean condition, String description) {
        logger.info(String.format("Checking condition: %s", description));
        try {
            Assert.assertTrue(condition);
            logger.info(String.format("Condition is true: %s", description));
        } catch (AssertionError e) {
            logger.error(String.format("Condition failed: %s", description));
            throw e;
        }
    }

    /**
     * Assertion Failure.
     */
    public static void assertFail(String description) {
        logger.info(String.format("Assertion Failed: %s", description));
        Assert.fail(description);
    }

    /*
    API ASSERTION
     */

    public static void assertMatchingNonNullFieldsSoft(Object actual, Object expected) {
        SoftAssert softAssert = new SoftAssert();
        assertMatchingNonNullFieldsSoft(actual, expected, "", softAssert);
        try {
            softAssert.assertAll();
            logger.info("All assertions passed successfully.");
        } catch (AssertionError e) {
            logger.error(String.format("Some assertions failed. %s", e));
            throw e;
        }
    }

    private static void assertMatchingNonNullFieldsSoft(Object actual, Object expected, String parentField, SoftAssert softAssert) {
        if (actual == null || expected == null) {
            logAndAssertEquals(softAssert, actual, expected, "Mismatch at " + parentField);
            return;
        }

        Class<?> clazz = expected.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object expValue = field.get(expected);
                Object actValue = field.get(actual);

                String fieldName = parentField.isEmpty() ? field.getName() : parentField + "." + field.getName();

                if (expValue == null || isDefaultValue(field.getType(), expValue)) {
                    continue; // skip fields not set in expected
                }

                if (isCustomClass(field.getType())) {
                    assertMatchingNonNullFieldsSoft(actValue, expValue, fieldName, softAssert);
                } else {
                    logAndAssertEquals(softAssert, actValue, expValue, "Mismatch at " + fieldName);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Unable to access field: " + field.getName(), e);
            }
        }
    }

    private static boolean isDefaultValue(Class<?> type, Object value) {
        if (type.isPrimitive()) {
            if (type == boolean.class) return Boolean.FALSE.equals(value);
            if (type == int.class) return ((int) value) == 0;
            if (type == long.class) return ((long) value) == 0L;
            if (type == double.class) return ((double) value) == 0.0;
            // add others as needed
        }
        return false;
    }

    private static boolean isCustomClass(Class<?> type) {
        return !(type.isPrimitive() || type.getName().startsWith("java.") || type.isEnum());
    }

    private static void logAndAssertEquals(SoftAssert softAssert, Object actual, Object expected, String description) {
        logger.info(String.format("Checking condition: %s", description));
        try {
            softAssert.assertEquals(actual, expected, description);
            logger.info(String.format("Condition passed: %s", description));
        } catch (AssertionError e) {
            logger.error(String.format("Condition failed: %s", description));
            throw e;
        }
    }

}
