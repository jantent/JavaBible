package com.prop;

import org.apache.log4j.Logger;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author: tangJ
 * @Date: 2018/11/29 17:05
 * @description:
 */
public abstract class ConfigStore {
    private static Logger logger = Logger.getLogger(ConfigStore.class);
    private static final Map<String, String> propMap = new LinkedHashMap<>();


    private static final String propFileName = "config.properties";

    /**
     * 1.从配置文件中加载
     * 2.反射到对象中
     *
     * @param config
     */

    public synchronized static void getFromStore(IConfig config) {
        loadFile();
        Field[] fields = config.getClass().getFields();
        try {
            for (Field field : fields) {
                // 获取常量修饰
                int mod = field.getModifiers();
                if ((!Modifier.isPublic(mod)) || Modifier.isFinal(mod) || Modifier.isStatic(mod)) {
                    continue;
                }
                String key = field.getName();
                String value = propMap.get(key);
                setObjField(config, field, value, true);
            }

        } catch (Exception e) {
            logger.error("加载配置失败", e);
        }
    }

    /**
     * 1.获取对象中的值
     * 2.反射出对象值
     *
     * @param config
     */
    public synchronized static void saveToFile(IConfig config) {
        OutputStreamWriter osw = null;
        try {
            Field[] fields = config.getClass().getFields();
            for (Field field : fields) {
                // 获取常量修饰
                int mod = field.getModifiers();
                if ((!Modifier.isPublic(mod)) || Modifier.isFinal(mod) || Modifier.isStatic(mod)) {
                    continue;
                }
                // 属性值
                String value = field.get(config).toString();
                // key值
                String key = field.getName();
                propMap.put(key, value);
            }

            Properties properties = new Properties();
            properties.putAll(propMap);
            FileWriter fileWriter = new FileWriter(new File(propFileName));
            properties.store(fileWriter,"");

        } catch (Exception e) {
            logger.error("存储配置失败", e);
        }

    }

    /**
     * 从配置文件中读取数据
     *
     * @return
     */
    private synchronized static void loadFile() {
        InputStreamReader isr = null;
        try {
            Properties prop = new Properties();

            isr = new InputStreamReader(new FileInputStream(propFileName), "UTF-8");
            prop.load(isr);
            Enumeration en = prop.propertyNames();
            while (en.hasMoreElements()) {
                String key = (String) en.nextElement();
                String value = prop.getProperty(key);
                propMap.put(key, value);
            }
        } catch (Exception e) {
            logger.error("加载配置失败", e);
        } finally {
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    logger.error(e);
                }
            }
        }
    }


    /**
     * @param obj        : 待设置的对象
     * @param field      : 待设置的对象属性
     * @param value      : 所设置的值
     * @param ignoreNull : true:忽略value = null的值
     */
    @SuppressWarnings("unchecked")
    private synchronized static final void setObjField(Object obj, Field field, Object value,
                                                       boolean ignoreNull) throws Exception {
        try {

            // 配置文件中为null的情况
            if (value == null) {
                if (!ignoreNull) {
                    //field.set(obj, null);
                    if (Boolean.TYPE == field.getType()) {
                        field.setBoolean(obj, false);
                    } else if (Byte.TYPE == field.getType()) {
                        field.setByte(obj, (byte) 0);
                    } else if (Character.TYPE == field.getType()) {
                        field.setChar(obj, (char) 0);
                    } else if (Double.TYPE == field.getType()) {
                        field.setDouble(obj, Double.parseDouble("0"));
                    } else if (Float.TYPE == field.getType()) {
                        field.setFloat(obj, Float.parseFloat("0"));
                    } else if (Integer.TYPE == field.getType()) {
                        field.setInt(obj, Integer.parseInt("0"));
                    } else if (Long.TYPE == field.getType()) {
                        field.setLong(obj, Long.parseLong("0"));
                    } else if (Short.TYPE == field.getType()) {
                        field.setShort(obj, Short.parseShort("0"));
                    } else if (field.getType().isEnum() || field.getType().equals(Date.class)) {
                        field.set(obj, null);
                    } else
                        field.set(obj, "");
                }
                return;
            }

            // 配置文件中的值不为null的情况
            String strVal = value.toString();

            if (strVal == null || strVal.length() < 1)
                return;

            if (Boolean.TYPE == field.getType()) {
                field.setBoolean(obj, Boolean.valueOf(strVal).booleanValue());
            } else if (Byte.TYPE == field.getType()) {
                field.setByte(obj, Byte.parseByte(strVal));
            } else if (Character.TYPE == field.getType()) {
                field.setChar(obj, strVal.charAt(0));
            } else if (Double.TYPE == field.getType()) {
                field.setDouble(obj, Double.parseDouble(strVal));
            } else if (Float.TYPE == field.getType()) {
                field.setFloat(obj, Float.parseFloat(strVal));
            } else if (Integer.TYPE == field.getType()) {
                field.setInt(obj, Integer.parseInt(strVal));
            } else if (Long.TYPE == field.getType()) {
                field.setLong(obj, Long.parseLong(strVal));
            } else if (Short.TYPE == field.getType()) {
                field.setShort(obj, Short.parseShort(strVal));
            } else if (field.getType().isEnum()) {
                @SuppressWarnings("rawtypes")
                Class clazz = field.getType();
                field.set(obj, Enum.valueOf(clazz, strVal));
            } else
                field.set(obj, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e);
        }
    }

}
