package com.aviator.island.entity.sys;

import com.aviator.island.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by aviator_ls on 2018/8/2.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "t_session")
@Setter
@Getter
public class MySession extends BaseEntity {

    @Id
    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "session_serialize")
    private String sessionSerialize;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

}