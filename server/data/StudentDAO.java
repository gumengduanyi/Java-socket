package server.data;

import server.data.DataAble;
import server.data.file.FileOperation;

public class StudentDAO {
    private DataAble fp;

    public StudentDAO(){
        fp = new FileOperation("student.txt");
    }

    public String getGrade(String code){
        return fp.getGrade(code);
    }
    
}
