package utils;

import com.aventstack.chaintest.generator.ChainTestSimpleGenerator;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomGenerator extends ChainTestSimpleGenerator {

    @Override
    protected File getOutFile() {
        String timestamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
        return new File("target/chaintest/Report_" + timestamp + ".html");
    }
}
