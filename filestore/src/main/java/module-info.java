//持久层的实现模块
//为persistence模块中的接口提供实现类
module sms.filestore {
    requires sms.model;
    requires sms.persistence;
    //需要在模块配置文件中声明出来
    provides sms.persistence.PersistenceService with sms.filestore.PersistenceServiceImpl;
}