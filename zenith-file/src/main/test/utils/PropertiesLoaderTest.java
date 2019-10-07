package utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PropertiesLoaderTest {

    private static String PROP_KEY_STRING = "prop.test.string";
    private static String PROP_VALUE_STRING = "test-string";

    private static String PROP_KEY_INTEGER = "prop.test.integer";
    private static Integer PROP_VALUE_INTEGER = 20;

    private File propertiesFile;

    private void createPropertiesFile() throws Exception{
        propertiesFile = FileUtil.createTempFile(new File(""), true);

        List<String> contentList = new ArrayList<>();

        contentList.add(PROP_KEY_STRING + "=" + PROP_VALUE_STRING);
        contentList.add(PROP_KEY_INTEGER + "=" + PROP_VALUE_INTEGER);

        FileUtil.appendLines(contentList, propertiesFile, CharsetUtil.UTF_8);
    }

    private void cleanAssets(){
        propertiesFile.delete();
    }

    @Before
    public void beforeTest() throws Exception {
        createPropertiesFile();
    }

    @Test
    public void loadString() throws Exception {
        PropertiesLoader loader = new PropertiesLoader(propertiesFile);
        String value = loader.getString(PROP_KEY_STRING);

        Assert.assertEquals("Expect " + PROP_VALUE_STRING + ", get " +value, PROP_VALUE_STRING, value);
    }

    @Test
    public void loadInteger() throws Exception {
        PropertiesLoader loader = new PropertiesLoader(propertiesFile);
        Integer value = loader.getInteger(PROP_KEY_INTEGER);

        Assert.assertEquals("Expect " + PROP_VALUE_INTEGER + ", get " +value, PROP_VALUE_INTEGER, value);
    }

    @Test
    public void loadIntegerWithInvalidKey() throws Exception {
        PropertiesLoader loader = new PropertiesLoader(propertiesFile);
        Integer value = loader.getInteger(UUID.randomUUID().toString());

        Assert.assertEquals("Expect " + PROP_VALUE_INTEGER + ", get " +value, null, value);
    }

    @After
    public void afterTest(){
        cleanAssets();
    }

}
