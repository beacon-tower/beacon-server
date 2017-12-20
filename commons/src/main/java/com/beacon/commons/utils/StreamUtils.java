package com.beacon.commons.utils;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * 流处理工具
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/19
 */
public abstract class StreamUtils {

    /**
     * 加载二进制
     */
    public static byte[] loadBinary(InputStream in) {
        try {
            BufferedInputStream buffer = new BufferedInputStream(in);
            byte[] bytes = new byte[in.available()];
            try {
                buffer.read(bytes);
            } finally {
                buffer.close();
                in.close();
            }
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 加载文本
     */
    public static String loadText(InputStream in, String encoding) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, encoding));
            StringBuilder builder = new StringBuilder();
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line + System.lineSeparator());
                }

                return builder.toString();
            } finally {
                reader.close();
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 按行加载
     */
    public static List<String> loadInLine(InputStream in, String encoding) {
        return Arrays.asList(loadText(in, encoding).split(System.lineSeparator()));
    }

    /**
     * 写入二进制
     */
    public static Boolean writeBinary(byte[] bytes, OutputStream out) {
        try {
            BufferedOutputStream buffer = new BufferedOutputStream(out);
            try {
                buffer.write(bytes);
            } finally {
                buffer.close();
                out.close();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 写入文本
     */
    public static boolean writeText(String content, OutputStream out, String encoding) {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, encoding));
            try {
                writer.write(content);
            } finally {
                writer.close();
                out.close();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
