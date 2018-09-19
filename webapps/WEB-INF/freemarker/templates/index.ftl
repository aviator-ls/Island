<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>后台管理</title>
    <link rel="stylesheet" href="${ctx}/css/layui.css">
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">

<#include "./common/header.ftl">
<#include "./common/side.ftl">

    <div class="layui-body">
        <!-- 内容主体区域 -->
    <#--<div class="layui-tab-content clildFrame">-->
    <#--<div class="layui-tab-item layui-show">-->
    <#--<iframe src="board/toBoardList"></iframe>-->
    <#--</div>-->
    <#--</div>-->
        <div>welcome!</div>
    </div>

<#include "./common/footer.ftl">
</div>
</body>
<script src="${ctx}/js/layui.js"></script>
<script src="${ctx}/js/common.js"></script>
<script>
    //JavaScript代码区域
    layui.use('element', function () {
        var element = layui.element;
    });
</script>
</html>
