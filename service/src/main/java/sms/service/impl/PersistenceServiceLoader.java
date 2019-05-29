package sms.service.impl;

import sms.persistence.PersistenceService;

import java.util.Optional;
import java.util.ServiceLoader;

/**
 * 服务加载器
 */
public class PersistenceServiceLoader {

    public static PersistenceService persistenceService;

    static {
        //ServiceLoader的load方法可以找到该接口所有的实现类，它返回一个流，这里寻找第一个实现，可能为null用optional接受
        final Optional<PersistenceService> optional = ServiceLoader.load(PersistenceService.class).findFirst();
        if (optional.isPresent()){
            persistenceService = optional.get();
        }else {
            throw new RuntimeException("No persistence service");
        }
    }
}
