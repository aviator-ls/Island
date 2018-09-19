<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>版块列表</title>
    <link rel="stylesheet" href="${ctx}/css/layui.css">
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">

<#include "./common/header.ftl">
<#include "./common/side.ftl">
    <div class="layui-body">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
            <legend>版块列表</legend>
        </fieldset>
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
                layer.confirm('真的删除行么', function (index) {
                    var url = baseUrl + '/back/board/' + data.id;
                    ajaxDelete(url);
                    obj.del();
                    layer.close(index);
                });
            } else if (obj.event === 'edit') {
                window.open(baseUrl + '/back/board/toUpdate/' + data.id, '_self');
            }
        });
    });

    function tableRender(table) {
        var keyword = isBlank($('#keyword').val()) ? '' : $('#keyword').val();
        table.render({
            elem: '#content'
            , url: baseUrl + '/back/api/board/pageList?keyword=' + keyword
            , toolbar: '#toolbar'
            , title: '版块列表'
            , cols: [[
                {field: 'id', title: 'ID', fixed: 'left', unresize: true, sort: true}
                , {field: 'name', title: '版块名称'}
                , {field: 'desc', title: '版块描述'}
                , {field: 'level', title: '版块层级', sort: true}
                , {field: 'parentBoardId', title: '父版块id'}
                , {field: 'parentBoardName', title: '父版块名称'}
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