package client.view;

import java.util.Scanner;
import client.net.SimpleClient;

public class CmdView{
    public void run(){
boolean flag=true;
while(flag){
display();
int n=input();
flag=process(n);
}
}


private void display(){
    System.out.println("学生成绩查询菜单:");
    System.out.println("1.Get Student Grade");
    System.out.println("2.xueshengxinxi");
    System.out.println("0.Exit");
}



private int input(){
    Scanner sc = new Scanner(System.in);
    return sc.nextInt();
}

private boolean process(int n){
    boolean flag=true;
    if(n == 1){
    String grade = getGrade();
    System.out.println("grade="+grade);
}
    else if(n == 2){

}

    else if(n == 0){
    flag=false;
}
    else{
    System.out.println("The choice:0-2,please input again!");
}
    return flag;
}

private String getGrade(){
    String code = "A01";
    String commandStr = getCommandCode(code);

    SimpleClient sc = new SimpleClient("127.0.0.1", 4330, commandStr);

    String responsStr = sc.run();

    String result = getStudentGrade(responsStr);
    return result;
}
private String getCommandCode(String code){
    System.out.println("Input student code:");
    Scanner sc = new Scanner(System.in);
    String sid = sc.nextLine();
    String result = code + "-" + sid;

    return result;
}

private String getStudentGrade(String cmdStr){
    String gradeStr = "None";

    String[] result = cmdStr.split("-");
    if(result.length != 2){
        gradeStr = "";
    }
    else{
        if("A01".equals(result[0])){
            gradeStr = result[1].trim();
        }
        }
    return gradeStr;
    }

}


