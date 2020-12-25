1、修改application.properties中的netty配置
2、实现HeartBeatClientHandler中的两个TODO（鉴权和服务器下行消息处理）
3、调用HeartBeatsClient.sendMsg方法可以向服务器发送消息

简易版本的可见：com.keytop.demo.client.socketTest

项目启动后访问：
    http://localhost:8080/netty/send?msg=测试消息
  可以向服务器发送测试消息
 


类说明：
    ApplicationStarter是netty客户端启动类，每个车场独占一条socket链接。