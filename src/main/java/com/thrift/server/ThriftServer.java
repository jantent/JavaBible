package com.thrift.server;

import com.thrift.auto.FileService;
import com.thrift.server.impl.ThriftServiceImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportFactory;

import java.net.InetSocketAddress;

/**
 * @author: tangJ
 * @Date: 2018/10/24 15:21
 * @description:
 */
public class ThriftServer {

    private String host = "0.0.0.0";

    private int port = 5368;

    private int threadCount = Runtime.getRuntime().availableProcessors() * 2;

    private TServer server = null;

    private ThriftServer() {
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class Inner {
        static ThriftServer instance = new ThriftServer();
    }

    public static ThriftServer getInstance() {
        return Inner.instance;
    }

    private void init() throws Exception {

        // 业务处理类 processor
        TProcessor processor = new FileService.Processor<>(new ThriftServiceImpl());

        // 创建非阻塞的 Transport
        TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(new InetSocketAddress(host, port));

        // 创建 transport factory , Nonblocking 使用 TFramedTransport
        TTransportFactory transportFactory = new TFramedTransport.Factory();

        TThreadedSelectorServer.Args args = new TThreadedSelectorServer.Args(serverTransport)
                .selectorThreads(threadCount)
                .acceptQueueSizePerThread(threadCount)
                .workerThreads(threadCount);

        args.processor(processor);

        // 最大设置为100M
        args.maxReadBufferBytes = 100 * 1024 * 1024L;
        // 使用二进制编码应用层数据
        args.protocolFactory(new TBinaryProtocol.Factory(true, true));
        args.transportFactory(transportFactory);

        server = new TThreadedSelectorServer(args);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("正在关闭thrift server交互程序。。。");
                try {
                    server.stop();
                } catch (Exception e) {
                    System.err.println("关闭失败，错误如下：" + e.getMessage());

                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    public void start() {
        System.out.println("thrift server start successfully");
        server.serve();

    }


}
