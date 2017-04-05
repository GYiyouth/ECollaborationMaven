package pojo.valueObject.domain;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import tool.BeanFactory;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Created by geyao on 2017/2/19.
 */
public class Test{
    public static void main(String[] args) {
        String str = "将会被final";
        A a = new A() {
            @Override
            public void foo() {
                System.out.println(str);
            }
        };
    }
}
interface A{
    void foo();
}