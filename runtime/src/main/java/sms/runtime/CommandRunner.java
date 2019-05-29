package sms.runtime;

import sms.service.StudentService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 命令行执行
 */
public class CommandRunner {

    private Map<String, Object> services = new HashMap<>();//服务名称到对象的映射关系
    private Map<String, Map<String, Method>> methods = new HashMap<>();//方法名称到方法的映射
    private Map<String, Class<?>> serviceTypes = Map.of("student", StudentService.class);//服务类

    public CommandRunner(){
        //遍历服务类型Map，将服务装载进services中，并将服务的方法装载到methods中
        serviceTypes.forEach((type, clazz) ->
                ServiceLoader.load(clazz).findFirst().ifPresent(obj -> {
                    services.put(type, obj);
                    methods.put(type, Stream.of(((Object) obj).getClass().getDeclaredMethods()).collect(Collectors.toMap(Method::getName, Function.identity())));
                })
        );
    }

    //service为用户要执行的服务类型，task为执行的服务方法，args为参数，假设用户输入的都是正确命令
    public void run(String service, String task, List<Object> args){
        Object serviceObj = services.get(service);
        Method method = methods.get(service).get(task);
        try {
            Object result = method.invoke(serviceObj, args.toArray());
            System.out.println(result);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
