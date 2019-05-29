//持久层的接口模块
//持久层的实现单独写在另一个模块内是为了扩展性
module sms.persistence {
    requires transitive sms.model;
    exports sms.persistence;
}