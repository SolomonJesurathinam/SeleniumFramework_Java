package utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

public class AllureHelper {

    private LoggerWrapper logger;

    public AllureHelper(){
        logger = new LoggerWrapper(AllureHelper.class);
    }

    public void cleanAllureResults() {
        File resultsDir = new File("target/allure-results");
        if (resultsDir.exists()) {
            deleteDirectory(resultsDir);
            logger.info("Cleared existing allure-results folder");
        }
    }

    private void deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file); // recursive delete
            }
        }
        directoryToBeDeleted.delete();
    }

    public void generateAllureReports() {
        createAllureEnvironment();
        try {
            String allureExecutable = resolveAllureExecutable();
            if (allureExecutable == null) {
                logger.error("Allure CLI not found in environment PATH.");
                return;
            }

            File resultsDir = new File("target/allure-results");
            if (!resultsDir.exists() || !resultsDir.isDirectory()) {
                logger.error(String.format("Allure results directory does not exist: %s", resultsDir.getAbsolutePath()));
                return;
            }

            String timestamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
            String outputDirPath = "target/allure-report/Report_" + timestamp;
            File outputDir = new File(outputDirPath);

            String command = String.format("%s generate --single-file %s -o %s --clean",allureExecutable,resultsDir.getAbsolutePath(),outputDir.getAbsolutePath());
            ProcessBuilder pb = isWindows()
                    ? new ProcessBuilder("cmd.exe", "/c", command)
                    : new ProcessBuilder("bash", "-c", command);

            pb.redirectErrorStream(true);
            Process process = pb.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    logger.info(String.format("[Allure] %s",line));
                }
            }
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                logger.info(String.format("Allure report generated at: %s" ,outputDir.getAbsolutePath()));
            } else {
                logger.error(String.format("Failed to generate Allure report. Exit code: %s",exitCode));
            }
        } catch (Exception e) {
            logger.error(String.format("Error generating Allure report: %s",e.getMessage()));
        }
    }

    private String resolveAllureExecutable() {
        try {
            String path = System.getenv("PATH");
            if (path != null) {
                String[] paths = path.split(File.pathSeparator);
                for (String dir : paths) {
                    File allureFile = new File(dir, isWindows() ? "allure.bat" : "allure");
                    if (allureFile.exists() && allureFile.canExecute()) {
                        return allureFile.getAbsolutePath();
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Could not resolve allure binary: " + e.getMessage());
        }
        return null;
    }

    private boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    private void createAllureEnvironment() {
        try {
            File resultsDir = new File("target/allure-results");
            if (!resultsDir.exists()) {
                resultsDir.mkdir();
            }

            File envFile = new File(resultsDir, "environment.properties");
            Properties props = new Properties();

            // 1. Add system properties
            props.setProperty("OS", System.getProperty("os.name"));
            props.setProperty("OS.Version", System.getProperty("os.version"));
            props.setProperty("Java.Version", System.getProperty("java.version"));
            props.setProperty("User", System.getProperty("user.name"));

            // 2. Add environment variables (a few examples)
            Map<String, String> env = System.getenv();
            if (env.containsKey("BROWSER")) {
                props.setProperty("Browser", env.get("BROWSER"));
            }
            if (env.containsKey("ENVIRONMENT")) {
                props.setProperty("Environment", env.get("ENVIRONMENT"));
            }

            // 4. Save to file
            try (FileWriter writer = new FileWriter(envFile)) {
                props.store(writer, "Allure environment.properties");
            }

            logger.info("Allure environment.properties generated automatically.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
