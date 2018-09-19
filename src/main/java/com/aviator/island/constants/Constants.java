package com.aviator.island.constants;

/**
 * Created by 18057046 on 2018/8/26.
 */
public class Constants {

    public interface CONFIG {
        String INDEX_CAROUSEL_PIC_PAGE_SIZE = "index_carousel_pic_page_size";// 首页轮播图片数量

        String HOT_POST_VIEW = "hot_post_view";// 热门文章最小浏览数

        String HOT_ASK_ANSWER = "hot_ask_answer";// 热门问答最小回复数
    }

    // 如配置为空，默认值
    public interface CONFIG_DEFAULT {
        int INDEX_CAROUSEL_PIC_PAGE_SIZE = 5;

        int HOT_POST_VIEW = 10;// 热门文章最小浏览数

        int HOT_ASK_ANSWER = 5;// 热门问答最小回复数
    }

    public interface UPLOAD_CLASSIFY {
        interface IMG {
            String DEFAULT = "default";

            String POST_CONTENT_IMG = "postContentImg";
        }
    }
}
