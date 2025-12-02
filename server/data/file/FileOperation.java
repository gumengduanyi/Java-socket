package server.data.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import server.data.DataAble;

public class FileOperation implements DataAble {
    private String name;

    public FileOperation(String name) {
        this.name = name;
    }

    public String getGrade(String code) {
        String grade = "None";

        try {
            BufferedReader br = new BufferedReader(new FileReader(name));
            String tempLine = "";
            while ((tempLine = br.readLine()) != null) {
                if (tempLine.trim().length() > 0) {
                    String[] term = tempLine.split(",");
                    if (term.length == 4) {
                        if (code.equals(term[0].trim())) {
                            grade = term[3].trim();
                        }
                    }
                }
            }
            br.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return grade;
    }

}
