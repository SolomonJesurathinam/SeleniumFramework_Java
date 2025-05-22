package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class AssertionUtils {

    private static final Logger logger = LogManager.getLogger(AssertionUtils.class);

    /**
     * Hard assert equality with logging.
     */
    public static <T> void assertEquals(T actual, T expected, String description) {
        logger.info("Verifying: {}", description);
        try {
            Assert.assertEquals(actual, expected);
            logger.info("Assertion passed: {} | Expected: {} | Actual: {}", description, expected, actual);
        } catch (AssertionError e) {
            logger.error("Assertion failed: {} | Expected: {} | Actual: {}", description, expected, actual);
            throw e;
        }
    }

    /**
     * Hard assert condition is true.
     */
    public static void assertTrue(boolean condition, String description) {
        logger.info("🔎 Checking condition: {}", description);
        try {
            Assert.assertTrue(condition);
            logger.info("✅ Condition is true: {}", description);
        } catch (AssertionError e) {
            logger.error("❌ Condition failed: {}", description);
            throw e;
        }
    }

}
