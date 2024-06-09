package com.violetks.fileUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 读取学生提交代码
 */
public class CodeOutput {
    //MARK
    final String codePath = "F://practice_system-master//src//main//resources//codeStr/";

    public String readFile(int sid, int qid) {
        String str = "无";
        String s = "";

        try {
            FileReader file = new FileReader(codePath + sid + "/" + qid + ".txt");

            for(BufferedReader br = new BufferedReader(file); (str = br.readLine()) != null; s = s + str + "\n") {
            }

            return s;
        } catch (FileNotFoundException e) {
            str = "无";
        } catch (IOException e) {
            str = "无内容";
        }

        return str;
    }
}
