# StudentGrade 项目

一个简单的基于 Socket 的学生成绩查询示例，包含客户端与服务端两部分。

## 结构
```
client/            客户端代码（视图、网络）
server/            服务端代码（网络、解析器）
```

客户端通过 `client.view.CmdView` 展示命令行菜单，输入后拼接指令如 `A01-<学号>`，通过 `client.net.SimpleClient` 发送到服务器。服务器由 `server.net.SimpleServer` 接收并调用 `server.parser.Parser` 解析返回类似 `A01-78` 的成绩数据。

## 运行步骤
### 启动服务端
```bash
javac server/net/SimpleServer.java server/parser/Parser.java server/test/TestServer.java
java server.test.TestServer
```
### 启动客户端
另开终端：
```bash
javac client/net/SimpleClient.java client/view/CmdView.java client/test/TestClient.java
java client.test.TestClient
```

输入对应学号后获取成绩。

## 开发建议
- 添加更多指令例如 A02 查询不同学生
- 增加异常处理和协议格式校验
- 使用数据库或文件持久化成绩

## 注释文档
项目中部分类的注释已提取为单独文档：`docs/ANNOTATIONS.md`，包含 `DataAble`、`StudentDAO`、`FileOperation` 的说明与注释，便于查阅。

## 版权
示例学习项目，不包含生产级功能。
