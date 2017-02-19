package pojo.valueObject.domain;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by geyao on 2017/2/19.
 */
@Entity
@Table(name = "test")
public class Test implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Cascade(CascadeType.ALL)
    private int id;

    @OneToOne(targetEntity = StudentVO.class)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private StudentVO studentVO;

    public Test() {
        super();
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                '}';
    }

    public StudentVO getStudentVO() {
        return studentVO;
    }

    public void setStudentVO(StudentVO studentVO) {
        this.studentVO = studentVO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
