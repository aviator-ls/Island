<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>文章列表</title>
    <link rel="stylesheet" href="${ctx}/css/layui.css">
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
<#include "./common/header.ftl">
<#include "./common/side.ftl">
    <div class="layui-body">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
            <legend>文章列表</legend>
        </fieldset>
        <ul class="layui-tab-title">
            <li class="layui-this">预览</li>
            <li>查看代码</li>
            <li>帮助</li>
        </ul>
        <table class="layui-hide" id="content" lay-filter="content"></table>
    </div>
<#include "./common/footer.ftl">
</div>
</body>
<script type="text/html" id="toolbar">
    搜索ID：
    <div class="layui-inline">
        <input class="layui-input" name="keyword" id="keyword" autocomplete="off">
    </div>
    <button id="searchByKeyword" class="layui-btn" data-type="reload">搜索</button>
</script>
<script type="text/html" id="bar">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<script src="${ctx}/js/jquery-3.3.1.min.js"></script>
<script src="${ctx}/js/layui.js"></script>
<script src="${ctx}/js/common.js"></script>
<script>
    layui.use(['element', 'table'], function () {
        var element = layui.element;
        var table = layui.table;
        tableRender(table);
        //监听行工具事件
        table.on('tool(content)', function (obj) {
            var data = obj.data;
            //console.log(obj)
            if (obj.event === 'del') {
                layer.confirm('文章删除后无法恢复，确定要删除该文章吗', function (index) {
                    var url = baseUrl + '/back/post/' + data.id;
                    ajaxDelete(url);
                    obj.del();
                    layer.close(index);
                });
            } else if (obj.event === 'edit') {
                window.open(baseUrl + '/back/post/toUpdate/' + data.id, '_self');
            }
        });
    });

    function tableRender(table) {
        var keyword = isBlank($('#keyword').val()) ? '' : $('#keyword').val();
        table.render({
            elem: '#content'
            , url: baseUrl + '/back/api/post/pageList?keyword=' + keyword
            , toolbar: '#toolbar'
            , title: '版块列表'
            , cols: [[
                {field: 'id', title: 'ID', fixed: 'left', unresize: true, sort: true}
                , {field: 'title', title: '标题'}
                , {field: 'content', title: '内容'}
                , {field: 'sourceType', title: '来源'}
                , {field: 'source', title: '来源链接'}
                , {field: 'reference', title: '翻译出处'}
                , {
                    field: 'userName', title: '发帖人', templet: function (d) {
                        var userName = '';
                        if (isNotBlank(d.user)) {
                            userName = d.user.userName;
                        }
                        return userName;
                    }
                }
                , {
                    field: 'createTime', title: '发表时间', templet: function (d) {
                        return millisecondToDate(d.createTime);
                    }
                }
                , {
                    field: 'updateTime', title: '更新时间', templet: function (d) {
                        return millisecondToDate(d.updateTime);
                    }
                }
                , {fixed: 'right', title: '操作', toolbar: '#bar'}
            ]]
            , page: true
        });
        $('#searchByKeyword').on('click', function () {
            var keyword = $('#keyword').val();
            keyword = isNotBlank(keyword) ? keyword : '';
            tableRender(table);
        });
        $('#keyword').val(keyword);
    }
</script>
</html>