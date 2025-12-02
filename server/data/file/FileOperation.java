package server.data.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import server.data.DataAble;

/**
 * FileOperation 是 `DataAble` 的文件实现：从文本文件中读取学生记录并查找成绩。
 * 文件格式假定每行以逗号分隔，字段顺序例如：id,name,other,grade
 */
public class FileOperation implements DataAble {
    // 要读取的文件名（相对或绝对路径）
    private String name;

    /**
     * 构造器：接收文件名，例如 "student.txt"
     * @param name 要打开的文件路径或名称
     */
    public FileOperation(String name) {
        this.name = name;
    }

    /**
     * 根据学号（code）查找并返回成绩。
     * 若未找到返回字符串 "None"（这是当前实现的约定）。
     *
     * @param code 学生学号或代码
     * @return 成绩字符串或 "None"
     */
    public String getGrade(String code) {
        String grade = "None";

        try {
            BufferedReader br = new BufferedReader(new FileReader(name));
            String tempLine = "";
            // 按行读取文件
            while ((tempLine = br.readLine()) != null) {
                // 跳过空行或只含空白的行
                if (tempLine.trim().length() > 0) {
                    // 假设以逗号分隔字段
                    String[] term = tempLine.split(",");
                    // 预期有 4 个字段：id,name,other,grade
                    if (term.length == 4) {
                        // 比较第一列（学号）是否匹配
                        if (code.equals(term[0].trim())) {
                            // 匹配成功，取第四列为成绩
                            grade = term[3].trim();
                        }
                    }
                }
            }
            br.close();
        }
        catch (FileNotFoundException e) {
            // 未找到文件时打印信息（可改为日志）
            e.printStackTrace();
        }
        catch (IOException e) {
            // 读写异常时打印信息（可改为更健壮的错误处理）
            e.printStackTrace();
        }

        return grade;
    }

}
