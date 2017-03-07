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
    private String age;
    private Test2 test2;
    public Test() {
        super();
    }
    public Test2 getTest2() {
        return test2;
    }
    public void setTest2(Test2 test2) {
        this.test2 = test2;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
