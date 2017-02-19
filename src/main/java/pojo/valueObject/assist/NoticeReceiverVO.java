package pojo.valueObject.assist;

import javax.persistence.*;

/**
 * Created by geyao on 2017/2/19.
 */
@Entity
@Table(name = "notice_receiver")
public class NoticeReceiverVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public NoticeReceiverVO() {
        super();
    }

    @Override
    public String toString() {
        return "NoticeReceiverVO{" +
                "id=" + id +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
