

[从零开始构建前后端分离的后台管理系统 - 1.（后端篇）搭建后端Spring Boot项目 - 知乎](https://zhuanlan.zhihu.com/p/661386392)

## 日志配置文件

首先，在resources目录下创建配置文件logback-spring.xml，Spring Boot会默认加载该配置文件。
该文件中，我们会配置日志的格式，并且将不同级别的日志记录到相应的文件中，
比如ERROR级别的日志记录到文件error.log， 而DEBUG级别日志记录到debug.log。
该配置文件内容比较繁杂，具体内容可以查看logback-spring.xml

然后，在application.yml文件中指定日志文件目录和默认日志级别（生产环境可以配置在application-prod.yml文件中）：
```yaml
logging:
    file:
        path: logs
    level:
        root: DEBUG
```

