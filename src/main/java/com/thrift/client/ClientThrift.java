package com.thrift.client;

import com.thrift.auto.FileData;
import com.thrift.auto.FileService;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;

/**
 * @author: tangJ
 * @Date: 2018/10/24 16:44
 * @description:
 */
public class ClientThrift {
    private static String ip = "10.0.90.50";

    private static int port = 5368;

    private static int timeout = 5 * 1000;

    private static String picFile = "F:\\cert\\cert.jpg";

    private static String fileName = "test.jpg";

    public static void main(String args[]) throws Exception {
        TTransport tTransport = new TFramedTransport(new TSocket(ip, port, timeout));
        // 协议要和服务端一致
        TProtocol protocol = new TBinaryProtocol(tTransport);
        tTransport.open();
        FileService.Client client = new FileService.Client(protocol);
        FileData fileData = new FileData();

        byte[] bytes = toByteArray(picFile);
        fileData.setFileName(fileName);
        fileData.setBuff(ByteBuffer.wrap(bytes));
        client.uploadFile(fileData);
        tTransport.close();
    }

    /**
     * 文件转化为字节数组
     */
    private static byte[] toByteArray(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

}
