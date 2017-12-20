package com.beacon.commons.utils;

import com.beacon.commons.base.BaseResCode;
import com.beacon.commons.exception.ResException;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * Assertion utility class that assists in validating arguments.
 * <p>
 * <p>Useful for identifying programmer errors early and clearly at runtime.
 * <p>
 * <p>For example, if the contract of a public method states it does not
 * allow {@code null} arguments, {@code Assert} can be used to validate that
 * contract. Doing this clearly indicates a contract violation when it
 * occurs and protects the class's invariants.
 * <p>
 * <p>Typically used to validate method arguments rather than configuration
 * properties, to check for cases that are usually programmer errors rather
 * than configuration errors. In contrast to configuration initialization
 * code, there is usually no point in falling back to defaults in such methods.
 * <p>
 * <p>This class is similar to JUnit's assertion library. If an argument value is
 * deemed invalid, an {@link IllegalArgumentException} is thrown (typically).
 * For example:
 * <p>
 * <pre class="code">
 * Assert.notNull(clazz, "The class must not be null");
 * Assert.isTrue(i > 0, "The value must be greater than zero");
 * </pre>
 * <p>
 * Assert utils class
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/19
 */
public final class AssertUtils {

    /**
     * Assert args is null, if not null throw new {@link ResException}
     * use resCode {@link BaseResCode}
     *
     * @param resCode response info
     * @param args        need Assert args value
     */
    public static void isNull(BaseResCode resCode, Object... args) {
        if (null == args) {
            return;
        }
        for (Object arg : args) {
            if (null != arg) {
                if (arg instanceof String && !StringUtils.isEmpty(arg.toString())) {
                    ExceptionUtils.throwResponseException(resCode);
                }
                if (arg instanceof Collection && ((Collection<?>) arg).size() > 0) {
                    ExceptionUtils.throwResponseException(resCode);
                }
                if (arg instanceof Map && ((Map<?, ?>) arg).size() > 0) {
                    ExceptionUtils.throwResponseException(resCode);
                }
                ExceptionUtils.throwResponseException(resCode);
            }
        }
    }

    /**
     * Assert args is null, if not null throw new {@link ResException}
     * use resCode {@link BaseResCode}
     *
     * @param resCode response info
     * @param args        need Assert args value
     */
    public static void isNull(BaseResCode resCode, String[] messageStr, Object... args) {
        if (null == args) {
            return;
        }
        int i = 0;
        for (Object arg : args) {
            String msgStr = messageStr[i];
            if (null != arg) {
                if (arg instanceof String && !StringUtils.isEmpty(arg.toString())) {
                    ExceptionUtils.throwResponseException(resCode, msgStr);
                }
                if (arg instanceof Collection && ((Collection<?>) arg).size() > 0) {
                    ExceptionUtils.throwResponseException(resCode, msgStr);
                }
                if (arg instanceof Map && ((Map<?, ?>) arg).size() > 0) {
                    ExceptionUtils.throwResponseException(resCode, msgStr);
                }
                ExceptionUtils.throwResponseException(resCode, msgStr);
            }
            i++;
        }
    }

    /**
     * Assert args is not null,if null throw new {@link ResException}
     * use resCode {@link BaseResCode}
     *
     * @param resCode response info
     * @param args        need Assert args value
     */
    public static void notNull(BaseResCode resCode, Object... args) {
        if (null == args) {
            ExceptionUtils.throwResponseException(resCode);
        }
        for (Object arg : args) {
            if (null == arg) {
                ExceptionUtils.throwResponseException(resCode);
            }
            if (arg instanceof String && StringUtils.isEmpty(arg.toString())) {
                ExceptionUtils.throwResponseException(resCode);
            }
            if (arg instanceof Collection && ((Collection<?>) arg).size() == 0) {
                ExceptionUtils.throwResponseException(resCode);
            }
            if (arg instanceof Map && ((Map<?, ?>) arg).size() == 0) {
                ExceptionUtils.throwResponseException(resCode);
            }
        }
    }

    /**
     * Assert args is not null,if null throw new {@link ResException}
     * use resCode {@link BaseResCode}
     *
     * @param resCode response info
     * @param args        need Assert args value
     */
    public static void notNull(BaseResCode resCode, String[] messageStr, Object... args) {
        if (null == args) {
            ExceptionUtils.throwResponseException(resCode);
        }
        int i = 0;
        for (Object arg : args) {
            String msgStr = messageStr[i];
            if (null == arg) {
                ExceptionUtils.throwResponseException(resCode, msgStr);
            }
            if (arg instanceof String && StringUtils.isEmpty(arg.toString())) {
                ExceptionUtils.throwResponseException(resCode, msgStr);
            }
            if (arg instanceof Collection && ((Collection<?>) arg).size() == 0) {
                ExceptionUtils.throwResponseException(resCode, msgStr);
            }
            if (arg instanceof Map && ((Map<?, ?>) arg).size() == 0) {
                ExceptionUtils.throwResponseException(resCode, msgStr);
            }
            i++;
        }
    }

    /**
     * Assert a boolean condition, throwing {@link ResException}
     * if the test context is {@code false}.
     * <pre class="code">Assert.isTrue(i &gt; 0, "The value must be greater than zero");</pre>
     *
     * @param resCode response info
     * @param condition  need Assert boolean condition
     */
    public static void isTrue(BaseResCode resCode, boolean condition) {
        if (!condition) {
            ExceptionUtils.throwResponseException(resCode);
        }
    }

    /**
     * Assert a boolean condition, throwing {@link ResException}
     * if the test context is {@code false}.
     * <pre class="code">Assert.isTrue(i &gt; 0, "The value must be greater than zero");</pre>
     *
     * @param resCode response info
     * @param condition  need Assert boolean condition
     */
    public static void isTrue(BaseResCode resCode, String msg, boolean condition) {
        if (!condition) {
            ExceptionUtils.throwResponseException(resCode, msg);
        }
    }

    /**
     * Assert text must be match the patter
     * if the match the patter context false.
     * throw new {@link ResException}
     *
     * @param resCode response info
     * @param args        need Assert args value
     */
    public static void isNumber(BaseResCode resCode, String... args) {
        if (null == args) {
            ExceptionUtils.throwResponseException(resCode);
        }
        for (String arg : args) {
            if (StringUtils.isEmpty(arg) || !RegexUtils.isNumber(arg)) {
                ExceptionUtils.throwResponseException(resCode);
            }
        }
    }

    /**
     * Assert text must be match the patter
     * if the match the patter context false.
     * throw new {@link ResException}
     *
     * @param resCode response info
     * @param args        need Assert args value
     */
    public static void isNumber(BaseResCode resCode, String[] messageStr, String... args) {
        if (null == args) {
            ExceptionUtils.throwResponseException(resCode);
        }
        int i = 0;
        for (String arg : args) {
            String msgStr = messageStr[i];
            if (StringUtils.isEmpty(arg) || !RegexUtils.isNumber(arg)) {
                ExceptionUtils.throwResponseException(resCode, msgStr);
            }
            i++;
        }
    }

    /**
     * Assert mobile must be match the patter
     * if the match the patter context false.
     * throw new {@link ResException}
     *
     * @param resCode response info
     * @param args        Assert args
     */
    public static void isMobile(BaseResCode resCode, String... args) {
        if (null == args) {
            ExceptionUtils.throwResponseException(resCode);
        }
        for (String arg : args) {
            if (StringUtils.isEmpty(arg) || !RegexUtils.isMobileNumber(arg)) {
                ExceptionUtils.throwResponseException(resCode);
            }
        }
    }

    /**
     * Assert id card must be match the patter
     * if the match the patter context false.
     * throw new {@link ResException}
     *
     * @param resCode response info
     * @param args        Assert args
     */
    public static void isIdCard(BaseResCode resCode, String... args) {
        if (null == args) {
            ExceptionUtils.throwResponseException(resCode);
        }
        for (String arg : args) {
            if (StringUtils.isEmpty(arg) || !RegexUtils.isIdCard(arg)) {
                ExceptionUtils.throwResponseException(resCode);
            }
        }
    }

    /**
     * Assert tel phone must be match the patter
     * if the match the patter context false.
     * throw new {@link ResException}
     *
     * @param resCode response info
     * @param args        Assert args
     */
    public static void isTelPhone(BaseResCode resCode, String... args) {
        if (null == args) {
            ExceptionUtils.throwResponseException(resCode);
        }
        for (String arg : args) {
            if (StringUtils.isEmpty(arg) || !RegexUtils.isTelPhone(arg)) {
                ExceptionUtils.throwResponseException(resCode);
            }
        }
    }

    /**
     * Assert zip code must be match the patter
     * if the match the patter context false.
     * throw new {@link ResException}
     *
     * @param resCode response info
     * @param args        Assert args
     */
    public static void isZipCode(BaseResCode resCode, String... args) {
        if (null == args) {
            ExceptionUtils.throwResponseException(resCode);
        }
        for (String arg : args) {
            if (StringUtils.isEmpty(arg) || !RegexUtils.isZipCode(arg)) {
                ExceptionUtils.throwResponseException(resCode);
            }
        }
    }

    /**
     * Assert Chinese characters must be match the patter
     * if the match the patter context false.
     * throw new {@link ResException}
     *
     * @param resCode response info
     * @param args        Assert args
     */
    public static void isChinese(BaseResCode resCode, String... args) {
        if (null == args) {
            ExceptionUtils.throwResponseException(resCode);
        }
        for (String arg : args) {
            if (StringUtils.isEmpty(arg) || !RegexUtils.isChinese(arg)) {
                ExceptionUtils.throwResponseException(resCode);
            }
        }
    }

    /**
     * Assert email must be match the patter
     * if the match the patter context false.
     * throw new {@link ResException}
     *
     * @param resCode response info
     * @param args        Assert args
     */
    public static void isEmail(BaseResCode resCode, String... args) {
        if (null == args) {
            ExceptionUtils.throwResponseException(resCode);
        }
        for (String arg : args) {
            if (StringUtils.isEmpty(arg) || !RegexUtils.isEmail(arg)) {
                ExceptionUtils.throwResponseException(resCode);
            }
        }
    }

    /**
     * Assert args length must be equal length
     * if not equal.
     * throw new {@link ResException}
     *
     * @param resCode response info
     * @param args        Assert args
     */
    public static void length(BaseResCode resCode, int length, String... args) {
        if (null == args) {
            ExceptionUtils.throwResponseException(resCode);
        }
        for (String arg : args) {
            if (StringUtils.isEmpty(arg) || arg.length() != length) {
                ExceptionUtils.throwResponseException(resCode);
            }
        }
    }

    /**
     * Assert args length must be between minLength and maxLength
     * if not between.
     * throw new {@link ResException}
     *
     * @param resCode response info
     * @param args        Assert args
     */
    public static void length(BaseResCode resCode, int minLength, int maxLength, String... args) {
        if (null == args) {
            ExceptionUtils.throwResponseException(resCode);
        }
        for (String arg : args) {
            if (StringUtils.isEmpty(arg) || arg.length() < minLength || arg.length() > maxLength) {
                ExceptionUtils.throwResponseException(resCode);
            }
        }
    }
}
