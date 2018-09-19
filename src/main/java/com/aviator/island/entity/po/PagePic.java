package com.aviator.island.entity.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.aviator.island.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "t_page_pic")
@Setter
@Getter
@Accessors(chain = true)
public class PagePic extends BaseEntity {

    public interface PIC_TYPE {
        int CAROUSEL = 1;// 首页轮播图片
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JSONField(serialize = false)
    private int id;

    @Column(name = "img_path")
    private String imgPath;// 图片路径

    @Column(name = "alt")
    private String alt;

    @Column(name = "img_type", columnDefinition = "tinyint")
    private int type;//图片类型 1：首页轮播图片

    @Column(name = "img_desc")
    private String desc;// 图片描述
}
