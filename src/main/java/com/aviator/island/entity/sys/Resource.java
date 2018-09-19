package com.aviator.island.entity.sys;

import com.aviator.island.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by 18057046 on 2018/7/27.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "t_sys_resource")
@Setter
@Getter
public class Resource extends BaseEntity {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id;

    @Column(name = "resource_name")
    private String name;

    @Column(name = "resource_type")
    private String type;

    @Column(name = "priority", columnDefinition = "tinyint")
    private int priority;

    @Column(name = "parent_id")
    private String parentId;

    @Column(name = "parent_ids")
    private String parentIds;

    @Column(name = "permission")
    private String permission;

    @Column(name = "available", columnDefinition = "tinyint")
    private int available;

    @ManyToMany(targetEntity = Role.class)
    @JoinTable(name = "t_sys_role_resource", joinColumns = @JoinColumn(name = "resource_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roleSet;
}
