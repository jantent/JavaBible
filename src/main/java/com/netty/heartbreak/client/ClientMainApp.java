package com.netty.heartbreak.client;

import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;
import java.util.UUID;

/**
 * @author: tangJ
 * @Date: 2018/9/26 09:28
 * @description:
 */
public class ClientMainApp {


    public static void main(String args[]) throws Exception {

        HeartBeatClient client = new HeartBeatClient();
        HeartBeatClientHandler handler = new HeartBeatClientHandler(client);

        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入命令：");
        while (true) {
            String cmdStr = scanner.nextLine();
            switch (cmdStr) {
                case "start":
                    client.start();
                    break;
                case "send":
                    System.out.println("请输入待发送的消息");
                    String sendMsg = scanner.nextLine();
                    if (StringUtils.isNotBlank(sendMsg) && sendMsg.equals("exit")) {
                        System.exit(-1);
                    } else {
                        String reqId = UUID.randomUUID().toString();
                    }
                    break;
                case "exit" :
                    System.exit(-1);
                    break;
                default:
                    break;
            }
        }
    }
}
