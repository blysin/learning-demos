netty demo ：https://www.bbsmax.com/A/KE5QjjLjdL/

断线重连由客户端来实现：
    客户端故障断线重连：ClientHandler#channelInactive
    服务端故障断线重连：ClientFutureListener#operationComplete