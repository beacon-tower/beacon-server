package com.beacon.commons.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件操作帮助类
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/5/26
 */
public class FileUtils {

    /**
     * 文件夹是否存在
     */
    public static boolean isFolder(File folder) {
        return folder.exists() && folder.isDirectory();
    }

    /**
     * 文件是否存在
     */
    public static boolean isFile(File file) {
        return file.exists() && file.isFile();
    }

    /**
     * 新建文件夹
     */
    public static boolean createFolder(File folder) {
        if (folder.exists()) {
            if (folder.isFile()) {
                return false;
            }
            return true;
        } else {
            return folder.mkdirs();
        }
    }

    /**
     * 新建文件
     */
    public static boolean createFile(File file) {
        try {
            if (file.exists()) {
                if (file.isFile()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除文件夹或文件
     */
    public static boolean delete(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] subFiles = file.listFiles();
                if (subFiles == null) {
                    return false;
                }

                for (File subFile : subFiles) {
                    if (!delete(subFile)) {
                        return false;
                    }
                }
            }

            if (!file.delete()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 遍历文件夹或文件
     */
    public static List<File> enums(File file, String pattern, boolean isRecursive) {
        List<File> fileList = new ArrayList<File>();
        File[] subFiles = file.listFiles();
        if (subFiles == null) {
            return fileList;
        }

        for (File subFile : subFiles) {
            if (subFile.isDirectory() && isRecursive) {
                fileList.addAll(enums(subFile, pattern, isRecursive));
            }

            if (subFile.isFile() && subFile.getName().matches(pattern)) {
                fileList.add(subFile);
            }
        }
        return fileList;
    }

    /**
     * 复制文件夹或文件
     */
    public static boolean copy(File srcFile, File destFile) {
        if (!srcFile.exists()) {
            return false;
        }

        if (srcFile.isFile()) {
            return copyFile(srcFile, destFile);
        } else {
            if (!createFolder(destFile)) {
                return false;
            }

            File[] subSrcFiles = srcFile.listFiles();
            if (subSrcFiles == null) {
                return false;
            }

            for (File subFile : subSrcFiles) {
                File subDestFile = new File(destFile, subFile.getName());
                if (subFile.isFile()) {
                    if (!copyFile(subFile, subDestFile)) {
                        return false;
                    }
                } else {
                    if (!copy(subFile, subDestFile)) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    /**
     * 复制文件
     */
    public static boolean copyFile(File srcFile, File destFile) {
        if (!isFile(srcFile)) {
            return false;
        }

        if (!isFile(destFile) && !createFolder(destFile)) {
            return false;
        }

        try {
            byte[] bytes = StreamUtils.loadBinary(new FileInputStream(srcFile));
            return StreamUtils.writeBinary(bytes, new FileOutputStream(destFile));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 重命名文件
     */
    public static boolean rename(File srcFile, File destFile) {
        return srcFile.renameTo(destFile);
    }

    /**
     * 按行加载文件内容
     */
    public static List<String> loadInLine(File file, String encoding) {
        try {
            return StreamUtils.loadInLine(new FileInputStream(file), encoding);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 加载文件内容
     */
    public static String load(File file, String encoding) {
        try {
            return StreamUtils.loadText(new FileInputStream(file), encoding);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 加载二进制文件
     */
    public static byte[] loadBinary(File file) {
        try {
            return StreamUtils.loadBinary(new FileInputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 写入文件
     */
    public static boolean write(String content, File file, String encoding) {
        try {
            return StreamUtils.writeText(content, new FileOutputStream(file), encoding);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 写入二进制文件
     */
    public static boolean writeBinary(byte[] bytes, File file) {
        try {
            return StreamUtils.writeBinary(bytes, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取文件的后缀名（获取文件名最后一个“.”后的内容）
     */
    public static String getExtName(File file) {
        int pos = file.getName().lastIndexOf('.');
        if (pos == -1) {
            return "";
        } else {
            return file.getName().substring(pos + 1);
        }
    }
}
