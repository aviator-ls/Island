<div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
        <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
        <ul class="layui-nav layui-nav-tree"  lay-filter="menu">
            <li class="layui-nav-item layui-nav-itemed">
                <a class="" href="javascript:;">版块管理</a>
                <dl class="layui-nav-child">
                    <dd><a href="${ctx}/board/toAdd">新增版块</a></dd>
                    <dd><a href="${ctx}/board/toList">版块列表</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item layui-nav-itemed">
                <a href="javascript:;">博客管理</a>
                <dl class="layui-nav-child">
                    <dd><a href="${ctx}/post/toList">文章列表</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="">云市场</a></li>
            <li class="layui-nav-item"><a href="">发布商品</a></li>
        </ul>
    </div>
</div>