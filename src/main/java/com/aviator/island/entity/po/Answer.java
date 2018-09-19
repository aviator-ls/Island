package com.aviator.island.entity.po;

import com.aviator.island.entity.BaseEntity;
import com.aviator.island.entity.sys.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by aviator_ls on 2018/8/24.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "t_answer")
@Setter
@Getter
@Accessors(chain = true)
public class Answer extends BaseEntity {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id")
    private String id;// 回答id

    @Column(name = "content")
    private String content;// 回答内容

    @Column(name = "create_time")
    private Date createTime = new Date();// 发布时间

    @Column(name = "update_time")
    private Date updateTime = new Date();// 更新时间

    @ManyToOne(targetEntity = Ask.class)
    @JoinColumn(name = "ask_id")
    private Ask ask;// 问题

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User answerUser;// 解答者
}
