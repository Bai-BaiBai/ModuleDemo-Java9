package sms.persistence;

import sms.model.Entity;

import java.util.List;
import java.util.Optional;

/**
 * 持久层接口
 */
public interface PersistenceService {
    //范型T需要继承自Entity才被处理

    /**
     * 列出<T>的条目
     * @param type
     * @param <T>
     * @return List<T>
     * @throws PersistenceException
     */
    <T extends Entity> List<T> list(Class<T> type) throws PersistenceException;

    /**
     * get出${id}的信息
     * Optional : 返回值为空时返回 Option类型的Empty，避免了空指针问题
     * @param type
     * @param id
     * @param <T>
     * @return Optional<T>
     * @throws PersistenceException
     */
    <T extends Entity> Optional<T> get(Class<T> type, String id) throws PersistenceException;

    /**
     * 创建和更新
     * @param entity
     * @throws PersistenceException
     */
    void save(Entity entity) throws PersistenceException;

    /**
     * 删除
     * @param type
     * @param id
     * @param <T>
     * @throws PersistenceException
     */
    <T extends Entity> void delete(Class<T> type, String id) throws PersistenceException;
}
