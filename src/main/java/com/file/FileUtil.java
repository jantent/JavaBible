package com.file;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author: tangJ
 * @Date: 2018/11/27 13:27
 * @description:
 */
public class FileUtil {


    /**
     * 获取该工程绝对路径下的目录
     *
     * @param dirName
     * @return
     */
    public static String getUploadFilePath(String dirName) {
        String path = FileUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = path.substring(1, path.length());
        try {
            path = URLDecoder.decode(path, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int lastIndex = path.lastIndexOf("/") + 1;
        path = path.substring(0, lastIndex);
        File file = new File("");
        return file.getAbsolutePath() + "/"+dirName;

    }
}
