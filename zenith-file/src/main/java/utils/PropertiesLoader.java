package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Properties;

/**
 *
 * 读取Property文件的工具，支持读取jar文件中的properties文件，线程安全
 *
 * @author  chenyifu qijunmi@163.com
 * @date    2019/10/7 15:30
*/
public class PropertiesLoader {
  private Properties properties = new Properties();

  public PropertiesLoader(File file) throws Exception {
    if (!file.exists())  throw new FileNotFoundException(file.getAbsolutePath());
    if (!file.isFile())  throw new Exception("Source is not a file!");
    if (!file.canRead()) throw new Exception("Read permission is not allowed!");

    try (FileInputStream reader = new FileInputStream(file)) {
      this.properties.load(reader);
    }
  }

  public PropertiesLoader(String filePath) throws Exception {
  	this(new File(filePath));
  }

  public String getString(String key) { return this.properties.getProperty(key); }

  public Integer getInteger(String key) { return getNumeric(key, Integer.class); }

  /**
   * 通过key获取对应整型值，如果获取失败则返回fallbackValue
   *
   * @param key property的key
   * @param fallbackValue 获取结果失败时返回该值
   * @return
   */
  public Integer getInteger(String key, Integer fallbackValue) {
    Integer result = getNumeric(key, Integer.class);
    return null==result?fallbackValue:result;
  }

  public Long getLong(String key) { return getNumeric(key, Long.class); }

  /**
   * 通过key获取对应Long值，如果获取失败则返回fallbackValue
   *
   * @param key property的key
   * @param fallbackValue 获取结果失败时返回该值
   * @return
   */
  public Long getLong(String key, Long fallbackValue) {
    Long result = getNumeric(key, Long.class);
    return null==result?fallbackValue:result;
  }

  public Double getDouble(String key) { return getNumeric(key, Double.class); }

  /**
   * 通过key获取对应Double值，如果获取失败则返回fallbackValue
   *
   * @param key property的key
   * @param fallbackValue 获取结果失败时返回该值
   * @return
   */
  public Double getDouble(String key, Double fallbackValue) {
    Double result = getNumeric(key, Double.class);
    return null==result?fallbackValue:result;
  }

  private <T extends Number> T getNumeric(String key, Class<T> clazz) {
    String tValueStr = this.properties.getProperty(key);
    if (null == tValueStr) return null;
    if (0 == tValueStr.length()) return null;

    Object tNumber = null;

    try {
      if (clazz == Integer.class) tNumber = Integer.valueOf(tValueStr);
      if (clazz == Double.class) tNumber = Double.valueOf(tValueStr);
      if (clazz == Float.class) tNumber = Float.valueOf(tValueStr);
      if (clazz == Long.class) tNumber = Long.valueOf(tValueStr);
      if (clazz == Short.class) tNumber = Short.valueOf(tValueStr);
    } catch (Exception e) {
      return null;
    }

    return (T)tNumber;
  }

  public <T extends Number> Optional<T> getOptionalNumeric(String key, Class<T> clazz) {
    return Optional.ofNullable(getNumeric(key, clazz));
  }
}
