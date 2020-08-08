package cn.dbdj1201.jvmDeep;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Author: dbdj1201
 * @Date: 2020-08-05 14:18
 */
@Slf4j
public class GCRootsTest {

    public static void main(String[] args) {
        Integer[] nums = {1, 2, 3, 4, 5};
        Arrays.stream(nums).forEach(System.out::print);
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(DoSth.class);
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            log.info("i am here for seeing sth");
            Object o1 = methodProxy.invokeSuper(o, objects);
            log.info("it's all over");
            return o1;
        });

//        DoSth o = (DoSth) enhancer.create();
        Object arg = enhancer.create();
        log.info("who are u -{}", arg);
        ((DoSth) arg).doSth();

    }
}

class DoSth {

    public void doSth() {
        System.out.println("you are so handsome");
    }

}