# 注释汇总 (Annotations extracted)

本文件将 `server/data/DataAble.java`、`server/data/StudentDAO.java` 和 `server/data/file/FileOperation.java` 中的中文注释与 JavaDoc 提取汇总，便于查阅。

---

## 文件：`server/data/DataAble.java`

/**
 * DataAble 接口定义了数据访问的契约。
 * 实现类负责根据给定的学生代码(code)返回对应的成绩字符串。
 */
public interface DataAble {
    /**
     * 根据学生代码返回成绩字符串。
     * 实现约定：若未找到可返回 null 或特定字符串（例如 "None"），由实现方决定。
     *
     * @param code 学生学号或代码（作为查找键）
     * @return 成绩字符串（或表示未找到的值）
     */
    String getGrade(String code);
}

---

## 文件：`server/data/StudentDAO.java`

/**
 * StudentDAO: 数据访问对象（DAO），封装学生数据的读取操作。
 * 目前通过 FileOperation 从文本文件中读取学生信息。
 */
public class StudentDAO {
    // 使用接口类型以便解耦具体实现（可替换为数据库实现等）
    private DataAble fp;

    /**
     * 构造器：当前实现使用文件实现 FileOperation，并指定文件名为 "student.txt"。
     * 如果需要读取其他文件或使用不同的数据源，可在此替换为其它 DataAble 实现。
     */
    public StudentDAO(){
        fp = new FileOperation("student.txt");
    }

    /**
     * 根据学生代码委托给底层数据实现获取成绩。
     * @param code 学号或学生代码
     * @return 成绩字符串，具体未找到时的返回值取决于底层实现（FileOperation 返回 "None"）
     */
    public String getGrade(String code){
        return fp.getGrade(code);
    }
}

---

## 文件：`server/data/file/FileOperation.java`

/**
 * FileOperation: 从文本文件中按行读取学生记录，按学号(code)查找并返回成绩。
 * 假定每行用逗号分隔，格式为: id,name,other,grade
 */
public class FileOperation implements DataAble {
    // 要读取的文件名（相对或绝对路径）
    private String name;

    /**
     * 构造函数：保存文件名
     * @param name 要打开的文件名，例如 "student.txt"
     */
    public FileOperation(String name) {
        this.name = name;
    }

    /**
     * 根据学号(code)返回成绩字符串。
     * 若未找到返回 "None"。
     * @param code 学生学号或代码
     * @return 成绩（字符串）或 "None"
     */
    public String getGrade(String code) {
        // 默认返回值，表示未找到
        String grade = "None";

        try {
            // 使用 BufferedReader 包装 FileReader，以便按行读取
            BufferedReader br = new BufferedReader(new FileReader(name));
            String tempLine = ""; // 临时保存每行内容

            // 逐行读取文件直到 EOF（readLine 返回 null）
            while ((tempLine = br.readLine()) != null) {
                // 去掉首尾空白后跳过空行
                if (tempLine.trim().length() > 0) {
                    // 假设行以逗号分隔，拆分为字段数组
                    String[] term = tempLine.split(",");
                    // 期望有 4 个字段：id,name,other,grade
                    if (term.length == 4) {
                        // 比较第 0 列（学号）是否与目标 code 匹配
                        if (code.equals(term[0].trim())) {
                            // 匹配成功，取第 3 列为成绩并去除空白
                            grade = term[3].trim();
                        }
                    }
                }
            }
            // 读取结束后关闭流（注意：若在读取过程中抛出异常，这行不会执行）
            br.close();
        }
        catch (FileNotFoundException e) {
            // 文件不存在时打印堆栈信息（开发时有用）
            e.printStackTrace();
        }
        catch (IOException e) {
            // 读取过程中遇到 I/O 错误时打印堆栈信息
            e.printStackTrace();
        }

        // 返回查找到的成绩，或默认的 "None"
        return grade;
    }
}

---

如果你还需要把这些注释按更易读的结构（例如标题+要点摘要）重新组织，或把该文件放到 `docs/` 目录下并在 README 中添加链接，我可以继续帮你调整。

---

## 补充注释（深入说明与示例）

下面为 `DataAble`、`StudentDAO`、`FileOperation` 三个类型补充更详细的说明、典型调用示例、边界情况与建议改进，便于在工程中实际使用或扩展。

### `DataAble`（接口）
- 目的：抽象 "获取成绩" 这一能力，屏蔽具体的数据来源（文件/数据库/缓存）。
- 方法约定：`String getGrade(String code)`。实现应明确未找到时的返回策略（推荐使用 `Optional<String>` 或明确的占位字符串）。
- 示例（接口实现替换）：
    ```java
    // 测试时可使用 stub
    DataAble stub = code -> "99"; // 固定返回
    StudentDAO dao = new StudentDAO(stub);
    ```
- 注意：修改接口签名会影响所有实现，慎重变更并同步更新文档。

### `StudentDAO`（DAO 层）
- 作用：作为业务层与数据实现之间的适配器，当前封装了 `DataAble` 的使用。
- 当前实现：无参构造中硬编码 `new FileOperation("student.txt")`，每次创建 `StudentDAO` 都会创建对应的 `FileOperation`。
- 典型改进：使用构造器注入或单例复用以便测试与性能优化：
    ```java
    // 构造器注入示例
    public StudentDAO(DataAble fp) { this.fp = fp; }
    // 在生产中： new StudentDAO(new FileOperation("student.txt"));
    // 在测试中： new StudentDAO(code -> "95");
    ```
- 注意：若 `StudentDAO` 在每次请求中被频繁创建，建议改为复用实例或把数据缓存到内存中。

### `FileOperation`（文件实现）
- 作用：从文本文件按行读取并按学号匹配返回成绩，文件格式假定 `id,name,other,grade`。
- 行为要点：
    - 忽略空行；按逗号分割字段并检查 `term.length == 4`；匹配 `term[0]` 返回 `term[3]`。
    - 异常处理当前为 `printStackTrace()`，建议改为日志或抛出异常上层处理。
- 路径与编码：`new FileReader(name)` 使用平台默认编码，若文件为 UTF-8，请改用 `InputStreamReader(new FileInputStream(name), StandardCharsets.UTF_8)`。
- 性能：当前每次查询都会扫描整个文件；若文件较大或访问频繁，建议在服务启动时把文件加载到 `Map<String,String>`（id -> grade）缓存。

### 典型一次查询的调用链（含示例值）
1. 客户端发送：`"A01-2019001"`。
2. 服务器 `Parser.run` 解析为 `command="A01"`、`para="2019001"` 并调用 `commandProcess`。
3. `commandProcess` 新建 `StudentDAO`（或使用注入的实例），调用 `getGrade("2019001")`。
4. `StudentDAO` 委托 `DataAble`（当前为 `FileOperation`）执行 `getGrade` 并返回成绩（例如 `"95"` 或 `"None"`）。
5. `Parser` 拼接响应 `"A01-95"` 并返回，服务器将该字符串写回客户端。

### 边界情况与处理建议
- 文件不存在或不可读：当前实现会打印异常并返回 `"None"` —— 更好的做法是记录错误并在响应中返回明确的错误码。
- 格式不匹配：跳过 `term.length != 4` 行；建议在日志记录首次遇到的异常行，便于人工排查数据问题。
- 并发与多连接：当前 `SimpleServer` 设计为处理单次连接后结束；若需并发请在接收循环中为每个连接创建独立线程或使用线程池。

如需，我可以直接：
- 把 `StudentDAO` 改为构造器注入并更新 `Parser` 的使用位置；或
- 把 `FileOperation.getGrade` 改为 try-with-resources 并支持指定字符集；或
- 在 `SimpleServer` 启动时创建并复用一个 `StudentDAO`（并在启动时加载缓存）。

告诉我你要我直接修改哪一项，我会继续实现并运行编译验证。