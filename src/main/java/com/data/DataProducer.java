package com.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Random;

/**
 * @author tangj
 * @date 2018/12/2 22:28
 * @description:
 */
public class DataProducer {
    public static void main(String args[]) throws Exception{
        File file = new File("data.txt");
        BufferedWriter output = new BufferedWriter(new FileWriter(file));

        DataProducer dataProducer = new DataProducer();
        dataProducer.produceData(output);
        output.close();
    }

    private void produceData(BufferedWriter output) throws Exception{
        Random random = new Random(System.currentTimeMillis());
        for (int i=1,len = 30000 * 10000;i<=len;i++){
            String dataUnit = random.nextInt(200000000)+" ";
            output.write(dataUnit);
            if (i%10 == 0){
                output.write("\n");
            }
        }

    }
}
