package pojo.valueObject.domain;

import tool.BeanFactory;

/**
 * Created by geyao on 2017/3/7.
 */
public class Test3 {
    public static void main(String[] args) {
        Test2 test2 = (Test2)BeanFactory.getBean("son1");
        System.out.println(test2.getAge());
    }
}
