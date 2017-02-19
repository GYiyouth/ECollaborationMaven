package pojo.valueObject.assist;

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
public class Message_ReceiverVO {
}
