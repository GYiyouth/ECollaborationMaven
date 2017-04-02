package pojo.valueObject.domain;

import org.springframework.stereotype.Component;

/**
 * Created by geyao on 2017/3/7.
 */
@Component
public class Test2 {
    int a = 1;
    private  class Inner{
        private  int age = 1;
        {
            a = 1;
        }
    }
}
