package sms.filestore;

import sms.model.Entity;
import sms.persistence.PersistenceException;
import sms.persistence.PersistenceService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 将数据存放到文件系统的data目录中
 * 每个实体类型对应一个单独的子目录
 * 每个实体类对应一个数据文件
 *
 * 逻辑类中抛出异常，在对外实现中捕获抛出自定义的PersistenceExpection，方便统一异常处理
 */
public class PersistenceServiceImpl implements PersistenceService {

    //data为信息的存放目录，每个实体都有一个自己的子目录
    private final Path dataPath = Paths.get(".", "data");

    //通过实体类型获取该实体的目录
    private Path getEntitiesPath(Class<?> type){
        return dataPath.resolve(type.getSimpleName());
    }

    //获取到每个实体的具体文件路径
    //文件名是id，类型是bin
    private Path getEntitiyPath(Class<?> type, String id){
        return getEntitiesPath(type).resolve(String.format("%s.bin", id));
    }

    @Override
    public <T extends Entity> List<T> list(Class<T> type) throws PersistenceException{
        List<T> results = new ArrayList<>();
        //在该实体类型的文件夹中，搜索以.bin后缀的所有文件，将它们的路径保存到流中
        try(DirectoryStream<Path> directoryStream = Files.newDirectoryStream(getEntitiesPath(type), "*.bin")){
            for (Path path : directoryStream){
                results.add(readEntity(path));//逐个读取流中路径的实体类
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenceException(e);
        }
        return null;
    }

    @Override
    public <T extends Entity> Optional<T> get(Class<T> type, String id) throws PersistenceException {
        try {
             return Optional.ofNullable(readEntity(getEntitiyPath(type, id)));
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void save(Entity entity) throws PersistenceException {
        try {
            saveEntity(entity);
        } catch (IOException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public <T extends Entity> void delete(Class<T> type, String id) throws PersistenceException {
        try {
            deleteEntity(type, id);
        } catch (IOException e) {
            throw new PersistenceException(e);
        }
    }

    /*
        辅助函数
     */

    //读取实体信息
    private < T extends Entity> T readEntity(Path path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(path))){
            return (T)inputStream.readObject();
        }
    }

    //存储实体信息
    private void saveEntity(Entity entity) throws IOException {
        Path path = getEntitiyPath(entity.getClass(), entity.getId());
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(path))){
            outputStream.writeObject(entity);
        }
    }

    //删除实体信息
    private void deleteEntity(Class<?> type, String id) throws IOException {
        Files.deleteIfExists(getEntitiyPath(type, id));
    }
}
