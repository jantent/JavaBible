package com.thrift.server.impl;

import com.thrift.auto.FileData;
import com.thrift.auto.FileService;
import org.apache.thrift.TException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.channels.FileChannel;

/**
 * @author: tangJ
 * @Date: 2018/10/24 15:54
 * @description:
 */
public class ThriftServiceImpl implements FileService.Iface{

    // 文件存放目录
    private static String dirName = "upload/";

    @Override
    public boolean uploadFile(FileData fileData) throws TException {
        FileChannel fileChannel = null;
        try {
            String filePath = getUploadFilePath()+fileData.fileName;
            System.out.println("file path :"+filePath);
            File revFile = new File(filePath);
            if (!revFile.getParentFile().exists()){
                boolean result = revFile.getParentFile().mkdirs();
                System.out.println("文件夹创建结果："+result);
            }

            if (!revFile.exists()){
                revFile.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(revFile);

            fileChannel = fos.getChannel();
            fileChannel.write(fileData.buff);

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            if (fileChannel!=null){
                try {
                    fileChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }


    // 获取文件存放地址
    public static String getUploadFilePath() {
        String path = ThriftServiceImpl.class.getProtectionDomain().getCodeSource().getLocation().getPath();
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
