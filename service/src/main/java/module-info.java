//服务层模块
module sms.service {
    //java的模块系统依赖于maven对路径的解析，需要在该模块的pom文件中添加dependency

    exports sms.service;
    requires sms.model;//传递依赖,使依赖service的模块也依赖model
    requires sms.persistence;
    uses sms.persistence.PersistenceService;
    provides sms.service.StudentService with sms.service.impl.StudentServiceImpl;//将service接口暴露出来
}