package utils;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Field;
import java.util.List;

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
            logAndAssertEquals(softAssert, actual, expected, parentField);
            return;
        }

        Class<?> clazz = expected.getClass();

        // Handle lists (or collections) explicitly
        if (expected instanceof List<?> expectedList && actual instanceof List<?> actualList) {
            int size = expectedList.size();
            if (actualList.size() < size) {
                logAndAssertEquals(softAssert, actualList.size(), size, "List size at "+parentField);
                return; // no point checking elements if size is less
            }
            for (int i = 0; i < size; i++) {
                Object expElem = expectedList.get(i);
                Object actElem = actualList.get(i);
                String indexedField = parentField + "[" + i + "]";
                assertMatchingNonNullFieldsSoft(actElem, expElem, indexedField, softAssert);
            }
            return; // done comparing list elements
        }

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object expValue = field.get(expected);
                Object actValue = field.get(actual);

                String fieldName = parentField.isEmpty() ? field.getName() : parentField + "." + field.getName();

                if (expValue == null || isDefaultValue(field.getType(), expValue)) {
                    continue; // skip fields not set in expected
                }

//                if (isCustomClass(field.getType())) {
//                    assertMatchingNonNullFieldsSoft(actValue, expValue, fieldName, softAssert);
//                } else {
//                    logAndAssertEquals(softAssert, actValue, expValue, "Mismatch at " + fieldName);
//                }

                if (actValue != null && isCustomClass(actValue.getClass())) {
                    assertMatchingNonNullFieldsSoft(actValue, expValue, fieldName, softAssert);
                } else {
                    logAndAssertEquals(softAssert, actValue, expValue, fieldName);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Unable to access field: " + field.getName(), e);
            }
        }
    }

    private static boolean isDefaultValue(Class<?> type, Object value) {
        if (value == null) return true;

        if (type == boolean.class || type == Boolean.class) {
            return !(Boolean) value;
        } else if (type == int.class || type == Integer.class) {
            return (Integer) value == 0;
        } else if (type == long.class || type == Long.class) {
            return (Long) value == 0L;
        } else if (type == double.class || type == Double.class) {
            return (Double) value == 0.0;
        } else if (type == float.class || type == Float.class) {
            return (Float) value == 0f;
        } else if (type == String.class) {
            return ((String) value).isEmpty();
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
