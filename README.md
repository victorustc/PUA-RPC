## FuBao-RPC (福报RPC)

自己实现的RPC框架，和Spring做了集成。
希望能通过RPC的方式将福报传达到分布式系统的每一个角落

使用示例参见toy\rpc\demo目录下的用例

### TODO

- RPC的同时带上福报参数，比如整个链路的日志级别、AB Test/切流方案等
- 支持自动代理，不需要显示调用RpcProxy取代理对象
- 支持zk
- 支持长连接、TCP拆包 
- client支持异步调用(返回Future)
- client同步阻塞调用支持设置超时时间
- client支持Forking Cluster 并行调用多个服务器，只要一个成功即返回
- url自动获取
- 日志优化