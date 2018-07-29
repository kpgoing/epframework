package club.xubowei.epframework.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author zhisheng
 * @date 2018/7/22.
 */
public class CastUtil {
    public static String castString(Object object) {
        return CastUtil.castString(object, "");
    }

    public static String castString(Object object, String defaultValue) {
        return object != null ? String.valueOf(object) : defaultValue;
    }

    public static double castDouble(Object object) {
        return CastUtil.castDouble(object, 0);
    }

    public static double castDouble(Object object, double defaultValue) {
        double doubleValue = defaultValue;

        if (object != null) {
            String stringValue = castString(object);
            if (StringUtils.isNotBlank(stringValue)) {
                try {
                    doubleValue = Double.parseDouble(stringValue);
                } catch (NumberFormatException e) {
                    doubleValue = defaultValue;
                }
            }
        }

        return doubleValue;
    }

    public static long castLong(Object object) {
        return castLong(object, 0);
    }

    public static long castLong(Object object, long defaultValue) {
        long longValue = defaultValue;
        if (object != null) {
            String stringValue = castString(object);
            if (StringUtils.isNotBlank(stringValue)) {
                try {
                    longValue = Long.parseLong(stringValue);
                } catch (NumberFormatException e) {
                    longValue = defaultValue;
                }
            }
        }

        return longValue;
    }

    public static int castInt(Object object) {
        return castInt(object, 0);
    }

    private static int castInt(Object object, int defaultValue) {
        int intValue = defaultValue;

        if (object != null) {
            String stringValue = castString(object);
            if (StringUtils.isNotBlank(stringValue)) {
                try {
                    intValue = Integer.parseInt(stringValue);
                } catch (NumberFormatException e) {
                    intValue = defaultValue;
                }
            }
        }

        return intValue;
    }

    public static boolean castBoolean(Object object) {
        return CastUtil.castBoolean(object, false);
    }

    private static boolean castBoolean(Object object, boolean defaultValue) {
        boolean booleanValue = defaultValue;

        if (object != null) {
            String stringValue = castString(object);
            if (stringValue != null) {
                booleanValue = Boolean.parseBoolean(stringValue);
            }
        }

        return booleanValue;
    }
}
