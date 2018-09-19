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
import java.util.Set;

/**
 * Created by aviator_ls on 2018/8/24.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "t_ask")
@Setter
@Getter
@Accessors(chain = true)
public class Ask extends BaseEntity {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id")
    private String id;// 问题id

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "ask_type", columnDefinition = "tinyint")
    private int type = 1;// 问题状态 1:未解决 2:已解决 3:已过期

    @Column(name = "create_time")
    private Date createTime = new Date();// 发布时间

    @Column(name = "update_time")
    private Date updateTime = new Date();// 更新时间

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User askUser;// 提问者

    @ManyToOne(targetEntity = Board.class)
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(targetEntity = Answer.class, mappedBy = "ask")
    private Set<Answer> answerSet;// 回答

    @ManyToMany(targetEntity = Tag.class)
    @JoinTable(name = "t_ask_tag", joinColumns = @JoinColumn(name = "ask_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tagSet;
}
