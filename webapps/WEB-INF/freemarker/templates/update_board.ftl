<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>修改版块</title>
    <link rel="stylesheet" href="${ctx}/css/layui.css">
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">

<#include "./common/header.ftl">
<#include "./common/side.ftl">
    <div class="layui-body">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
            <legend>修改版块</legend>
        </fieldset>
        <form class="layui-form" action="${ctx}/board/add" method="post">
            <div class="layui-form-item">
                <label class="layui-form-label">版块名称<span style="color:red">*</span></label>
                <div class="layui-input-block">
                    <input type="text" name="name" lay-verify="name" autocomplete="off" placeholder="请输入版块名称" class="layui-input" <#if board??>value="${board.name!}"</#if>>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">版块层级<span style="color:red">*</span></label>
                    <div class="layui-input-inline">
                        <select name="level" lay-verify="required" lay-search="">
                            <option value="${board.level}" class="layui-this">${board.level}</option>
                            <#if board.level != 1><option value="1">1</option></#if>
                            <#if board.level != 2><option value="2">2</option></#if>
                            <#if board.level != 3><option value="3">3</option></#if>
                        </select>
                    </div>
                </div>
                <#if parentBoardList??>
                    <div class="layui-inline">
                        <label class="layui-form-label">父版块</label>
                        <div class="layui-input-inline">
                            <select name="parentBoardId" lay-search="">
                                <#list parentBoardList as parentBoard>
                                    <#if board??>
                                        <#if board.parentBoardId?? && (board.parentBoardId == parentBoard.id)>
                                            <option value="${parentBoard.id}" class="layui-this">${parentBoard.name}</option>
                                        </#if>
                                    </#if>
                                </#list>
                                <#list parentBoardList as parentBoard>
                                    <#if board??>
                                        <#if !(board.parentBoardId?? && (board.parentBoardId == parentBoard.id))>
                                            <option value="${parentBoard.id}">${parentBoard.name}</option>
                                        </#if>
                                    </#if>
                                </#list>
                            </select>
                        </div>
                    </div>
                </#if>
            </div>
            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">版块描述</label>
                <div class="layui-input-block">
                    <textarea name="desc" class="layui-textarea">
                        <#if board??>
                            ${board.desc!}
                        </#if>
                    </textarea>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit="" lay-filter="update">提交</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block"><span style="color: red">${msg!}</span></div>
            </div>
        </form>
    </div>
<#include "./common/footer.ftl">
</div>
</body>
<script src="${ctx}/js/layui.js"></script>
<script src="${ctx}/js/common.js"></script>
<script>
    layui.use('form', function(){
        var form = layui.form;
        //自定义验证规则
        form.verify({
            name: function(value){
                if(isBlank(value)){
                    return '版块名称不可为空';
                }
            }
        });
        //监听提交
        form.on('submit(update)', function(data){
            var url = baseUrl + '/back/board/${board.id!}';
            ajaxPut(url, data.field);
            return false;
        });
    });
</script>
</html>