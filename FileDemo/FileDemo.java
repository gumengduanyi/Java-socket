import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileDemo {
	  private String filePath;
	  public FileDemo(String filePath){
	  	this.filePath=filePath;
	  }
	  public void readfile(){
	  	 try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            
            // 读取文件
            line = br.readLine();
            // 按逗号分割字段
            String[] fields = line.split(",");
                
            // 解析字段（日期,姓名,年龄,分数）
            String date = fields[0].trim();
            String name = fields[1].trim();
            String age = fields[2].trim();
            String score = fields[3].trim();
                
                // 输出解析结果
            System.out.println(date+" "+name+" "+ age+ " "+score);
            
       } catch (IOException e) {
            System.err.println("文件读取异常: " + e.getMessage());
       } catch (NumberFormatException e) {
            System.err.println("数据格式错误: " + e.getMessage());
       }
    }
	  	
}
