package com.proc;

import org.apache.commons.exec.*;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

/**
 * @author: tangJ
 * @Date: 2018/10/8 17:37
 * @description:
 */
public class ProcUtil {


    private static long timeout = 60 * 1000;

    String cmd = "ping www.koal.com";

    /**
     * 执行cmd命令 并返回结果
     *
     * @param cmd
     * @return 返回命令的执行结果
     * @throws Exception
     */
    public static String execWithResult(String cmd) throws Exception{
        // 正常结果流
        ByteArrayOutputStream ops = new ByteArrayOutputStream();
        // 异常结果流
        ByteArrayOutputStream errOps = new ByteArrayOutputStream();

        CommandLine commandLine = CommandLine.parse(cmd);
        DefaultExecutor exec = new DefaultExecutor();
        exec.setExitValues(null);

        // 设置一分钟超时
        ExecuteWatchdog watchdog = new ExecuteWatchdog(timeout);
        exec.setWatchdog(watchdog);

        PumpStreamHandler streamHandler = new PumpStreamHandler(ops,errOps);
        exec.setStreamHandler(streamHandler);
        exec.execute(commandLine);

        String out = ops.toString("GBK");
        String error = errOps.toString("GBK");
        return out+error;
    }

    public static void execWithOutResult(String cmd) throws Exception{
        CommandLine commandLine = new CommandLine(cmd);
        DefaultExecutor executor = new DefaultExecutor();
        executor.setExitValue(1);
        // 设置一分钟超时
        ExecuteWatchdog watchdog = new ExecuteWatchdog(timeout);
        executor.setWatchdog(watchdog);

        DefaultExecuteResultHandler handler = new DefaultExecuteResultHandler();
        executor.execute(commandLine,handler);
        //命令执行返回前一直阻塞
        handler.waitFor();
    }



    @Test
    public void testCmd() throws Exception{
        String result = execWithResult(cmd);
        System.out.println(result);
//        execWithOutResult("shutdown -s -t 00");
    }
}
