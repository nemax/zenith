package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Properties;

/**
 * @author test
 * @date 2019/01/04
 * */
public class PropertiesLoader {
  private Properties properties = new Properties();


  public PropertiesLoader(String filePath) throws Exception {
  	this(filePath, false);
  }


  public PropertiesLoader(String filePath, boolean inJarFile) throws Exception {
    File tFile = new File(filePath);

    if (!inJarFile) {
      if (!tFile.exists())  throw new FileNotFoundException(filePath);
      if (!tFile.isFile())  throw new Exception("Source is not a file!");
      if (!tFile.canRead()) throw new Exception("Read permission is not allowed!");

    }
    try (FileInputStream reader = new FileInputStream(tFile)) {
      this.properties.load(reader);
    }
  }


  public String getString(String key) { return this.properties.getProperty(key); }


  public <T extends Number> T getNumeric(String key, Class<T> clazz) {
    String tValueStr = this.properties.getProperty(key);
    if (null == tValueStr) return null;
    if (0 == tValueStr.length()) return null;

    Object tNumber = null;

    try {
      if (clazz == Integer.class) tNumber = Integer.valueOf(tValueStr);
      if (clazz == Double.class) tNumber = Double.valueOf(tValueStr);
      if (clazz == Float.class) tNumber = Float.valueOf(tValueStr);
      if (clazz == Long.class) tNumber = Long.valueOf(tValueStr);
    } catch (Exception e) {
      return null;
    }

    return (T)(Number)tNumber;
  }

  public <T extends Number> Optional<T> getOptionalNumeric(String key, Class<T> clazz) {
    return Optional.ofNullable(getNumeric(key, clazz));
  }
}
