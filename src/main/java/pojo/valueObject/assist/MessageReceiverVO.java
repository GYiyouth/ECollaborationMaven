package pojo.valueObject.assist;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import pojo.valueObject.domain.MessageVO;
import pojo.valueObject.domain.UserVO;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 这里注意，如果注释@Cascade.ALL，会先插入级联的主表记录（比如user表），再插入关系表
 * 这显然是无法接受的，所以这里不加级联
 * CascadeType.REMOVE的效果是删除这个关系，同时把对应的user表的记录也删除，这更无法接受
 * 所以，一定要确保user表有相应的记录了，这边才能添加关系，否则，抛出异常：
 * TransientObjectException
 * Created by geyao on 2017/2/19.
 */
@Entity
@Table(name = "message_receiver")
public class MessageReceiverVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = MessageVO.class)
    @JoinColumn(name = "messageId",
        referencedColumnName = "id")
    private MessageVO messageVO;

    @ManyToOne(targetEntity = UserVO.class)
    @JoinColumn(name = "receiverId",
        referencedColumnName = "id")
    private UserVO receiverUserVO;

    private boolean readFlag;

    public MessageReceiverVO() {
        super();
    }

    @Override
    public String toString() {
        return "MessageReceiverVO{" +
                "id=" + id +
                ", messageVO=" + messageVO.getId() +
                ", receiverUserVO=" + receiverUserVO.getId() +
                ", readFlag=" + readFlag +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MessageVO getMessageVO() {
        return messageVO;
    }

    public void setMessageVO(MessageVO messageVO) {
        this.messageVO = messageVO;
    }

    public UserVO getReceiverUserVO() {
        return receiverUserVO;
    }

    public void setReceiverUserVO(UserVO receiverUserVO) {
        this.receiverUserVO = receiverUserVO;
    }

    public boolean isReadFlag() {
        return readFlag;
    }

    public void setReadFlag(boolean readFlag) {
        this.readFlag = readFlag;
    }
}
