var baseUrl = getRealPath();
var defaultProfilePic = baseUrl + '/img/defaultProfilePic.jpg';
var defaultPageNum = 1;
var defaultPageSize = 10;
var defaultShowPageCount = 5;

function getRealPath() {
    var localObj = window.location;
    var contextPath = localObj.pathname.split("/")[1];
    var basePath = localObj.protocol + "//" + localObj.host + "/" +
        contextPath;
    return basePath;
}

$(function () {
    importHeaderPage();
    importFooterPage();
    initTooltip();
    breadcrumbs();
});

function initTooltip() {
    $("[data-toggle='tooltip']").tooltip({trigger: 'manual'});
}

function breadcrumbs() {
    $('.breadcrumbs-home').attr('href', baseUrl);
    $('.breadcrumbs-post').attr('href', baseUrl + '/toPostList').attr('target', '_self');
}

/** head */
function importHeaderPage() {
    var headerDiv = $('<div></div>');
    headerDiv.prependTo('body');
    headerDiv.load(baseUrl + '/view/desk/common/header.html', null, function () {
        afterHeaderPage();
    });
    $('head').append('<link href="' + baseUrl + '/css/non-responsive.css" rel="stylesheet"/>')
}

function afterHeaderPage() {
    $('#projectHome').attr('href', baseUrl).attr('target', '_self');
    $('#home').attr('href', baseUrl).attr('target', '_self');
    $('#toPostList').attr('href', baseUrl + '/toPostList').attr('target', '_blank');
    $('#toAskList').attr('href', baseUrl + '/toAskList').attr('target', '_blank');
    $('#toRegister').attr('href', baseUrl + '/toRegister').attr('target', '_self');
    $('#toLogin').attr('href', baseUrl + '/toLogin').attr('target', '_self');
    $('#toPost').attr('href', baseUrl + '/toAddPost').attr('target', '_blank');
    $('#toAsk').attr('href', baseUrl + '/toAddAsk').attr('target', '_blank');
    $('#toPost').hide();
    $('#toAsk').hide();
    $.ajax({
        url: baseUrl + '/api/user',
        success: function (res) {
            var code = res.responseCode;
            if (code == '0000') {
                var user = res.data;
                var nickName = user.nickName;
                var credit = user.credit;
                $('#credit').text(credit);
                $('#user').html(nickName + "<span class=\"caret\"></span>");
                $('#toLogin').hide();
                $('#toRegister').hide();
                $('#toPost').show();
                $('#toAsk').show();
            }
        }
    })
}

function outLogin() {
    $.ajax({
        url: baseUrl + '/api/user/outLogin'
    });
    window.open(baseUrl + '/index', '_self');
}

/** footer */
function importFooterPage() {
    var headerDiv = $('<div></div>');
    headerDiv.appendTo('body');
    headerDiv.load(baseUrl + '/view/desk/common/footer.html');
}

/** common function */
function isBlank(text) {
    if (text == null || text == undefined || text.length < 1) {
        return true;
    }
    if (text instanceof String && text.replace(" ", "") == "") {
        return true;
    }
    return false;
}

function isNotBlank(text) {
    return !isBlank(text);
}

function isFunction(func) {
    return typeof func == 'function';
}

function toJson(data) {
    return JSON.stringify(data);
}

function Tree(text, icon, selectedIcon, href, selectable, state, color, backColor, tags, nodes) {
    this.text = text;// 列表树节点上的文本
    this.icon = icon;// 列表树节点上的图标，通常是节点左边的图标
    this.selectedIcon = selectedIcon;// 当某个节点被选择后显示的图标，通常是节点左边的图标
    this.href = href;// 结合全局enableLinks选项为列表树节点指定URL
    this.selectable = selectable;// 指定列表树的节点是否可选择。设置为false将使节点展开，并且不能被选择
    this.state = state;// 一个节点的初始状态
    this.color = color;// 节点的前景色，覆盖全局的前景色选项
    this.backColor = backColor;// 节点的背景色，覆盖全局的背景色选项
    this.tags = tags;// 通过结合全局showTags选项来在列表树节点的右边添加额外的信息
    this.nodes = nodes;
}

function State(checked, disabled, expanded, selected) {
    this.checked = checked;// 指示一个节点是否处于checked状态，用一个checkbox图标表示
    this.disabled = disabled;// 指示一个节点是否处于disabled状态
    this.expanded = expanded;// 指示一个节点是否处于展开状态
    this.selected = selected;// 指示一个节点是否可以被选择
}

function tooltipFade(tag) {
    var obj = $(tag);
    obj.tooltip('show');
    setTimeout(function () {
        obj.tooltip('hide')
    }, 1500);
}

var HtmlUtil = {
    /*1.用浏览器内部转换器实现html转码*/
    htmlEncode: function (html) {
        //1.首先动态创建一个容器标签元素，如DIV
        var temp = document.createElement("div");
        //2.然后将要转换的字符串设置为这个元素的innerText(ie支持)或者textContent(火狐，google支持)
        (temp.textContent != undefined ) ? (temp.textContent = html) : (temp.innerText = html);
        //3.最后返回这个元素的innerHTML，即得到经过HTML编码转换的字符串了
        var output = temp.innerHTML;
        temp = null;
        return output;
    },
    /*2.用浏览器内部转换器实现html解码*/
    htmlDecode: function (text) {
        //1.首先动态创建一个容器标签元素，如DIV
        var temp = document.createElement("div");
        //2.然后将要转换的字符串设置为这个元素的innerHTML(ie，火狐，google都支持)
        temp.innerHTML = text;
        //3.最后返回这个元素的innerText(ie支持)或者textContent(火狐，google支持)，即得到经过HTML解码的字符串了。
        var output = temp.innerText || temp.textContent;
        temp = null;
        return output;
    }
};

function millisecondToDate(millisecond) {
    var unixTimestamp = new Date(millisecond);
    return unixTimestamp.toLocaleString();
}

Date.prototype.toLocaleString = function () {
    return this.getFullYear() + "-" + p(this.getMonth() + 1) + "-" + p(this.getDate()) + " " + p(this.getHours()) + ":" + p(this.getMinutes()) + ":" + p(this.getSeconds());
};

// 时间补0
function p(s) {
    return s < 10 ? '0' + s : s;
}


function pagination(paginationTag, curPage, pageTotal, showPageCount, loadFuncName) {
    if (curPage < 1 || (curPage > pageTotal)) {
        curPage = 1;
    }
    if (pageTotal < 1) {
        pageTotal = 1;
    }
    if (showPageCount < 1) {
        showPageCount = 1;
    }
    var paginHtml = '<nav aria-label="Page navigation"><ul class="pagination">';
    if (curPage < 2) {
        paginHtml += '<li class="disabled"><span><span aria-hidden="true">首页</span></span></li>';
        paginHtml += '<li class="disabled"><span><span aria-hidden="true">&laquo;</span></span></li>';
    } else {
        paginHtml += '<li><a href="javascript:void(0);" aria-label="Previous" onclick="' + loadFuncName + '(' + 1 + ')"><span aria-hidden="true">首页</span></a></li>';
        paginHtml += '<li><a href="javascript:void(0);" aria-label="Previous" onclick="' + loadFuncName + '(' + (curPage - 1) + ')"><span aria-hidden="true">&laquo;</span></a></li>';
    }
    var pageArr = [];
    var startPage;
    var endPage;
    if (pageTotal < showPageCount) {
        startPage = 1;
        endPage = pageTotal;
    } else if (curPage <= Math.floor(showPageCount / 2)) {
        startPage = 1;
        endPage = showPageCount;
    } else if ((pageTotal - curPage) < Math.floor(showPageCount / 2)) {
        startPage = pageTotal - showPageCount + 1;
        endPage = pageTotal;
    } else {
        startPage = curPage - Math.floor(showPageCount / 2);
        endPage = curPage + Math.floor(showPageCount / 2);
    }
    if (startPage < 1) {
        startPage = 1;
    }
    if (endPage > pageTotal) {
        endPage = pageTotal;
    }
    for (startPage; startPage < endPage + 1; startPage++) {
        pageArr.push(startPage);
    }
    pageArr.forEach(function (item, index, arr) {
        if (item == curPage) {
            paginHtml += '<li class="active"><span>' + item + '<span class="sr-only">(current)</span></span></li>'
        } else {
            paginHtml += '<li><a href="javascript:void(0);" onclick="' + loadFuncName + '(' + item + ')">' + item + '</a></li>';
        }
    });
    if (curPage == pageTotal) {
        paginHtml += '<li class="disabled"><span><span aria-hidden="true">&raquo;</span></span></li>';
        paginHtml += '<li class="disabled"><span><span aria-hidden="true">末页</span></span></li>';
    } else {
        paginHtml += '<li><a href="javascript:void(0);" aria-label="Next" onclick="' + loadFuncName + '(' + (curPage + 1) + ')"><span aria-hidden="true">&raquo;</span></a></li>';
        paginHtml += '<li><a href="javascript:void(0);" aria-label="Next" onclick="' + loadFuncName + '(' + pageTotal + ')"><span aria-hidden="true">末页</span></a></li>';
    }
    paginHtml += '</ul></nav>';
    $(paginationTag).html(paginHtml);
}

// 提示框弹出，定时消失
function dialogModal(content, closeTime, callback) {
    if (isBlank($('#tooltipModel'))) {
        var modelHtml = '<div id="tooltipModel" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">' +
            '<div class="modal-dialog modal-lg" role="document"><div id="tooltipModelContent" class="modal-content">' +
            '<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-label="Close">' +
            '<span aria-hidden="true">&times;</span></button><h4 class="modal-title" id="gridSystemModalLabel">提示</h4></div>' +
            '<div class="modal-body clear" id="tooltipModelContentMsg">' + content + '</div></div></div></div>'
        $('body').append(modelHtml);
    }
    var tag = $('#tooltipModel');
    $('#tooltipModelContentMsg').text(content);
    tag.modal('show');
    var time = 1500;
    if (isNotBlank(closeTime)) {
        time = closeTime;
    }
    setTimeout(function () {
        tag.modal('hide');
        if (isFunction(callback)) {
            callback();
        }
    }, time);
}

// 提示框弹出，定时消失
function dialogModalSuccess(content, closeTime, callback) {
    if (isBlank($('#tooltipModelSuccess'))) {
        var modelHtml = '<div id="tooltipModelSuccess" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">' +
            '<div class="modal-dialog modal-lg" role="document"><div id="tooltipModelSuccessContent" class="modal-content">' +
            '<div class="modal-header myModal-success-header"><button type="button" class="close" data-dismiss="modal" aria-label="Close">' +
            '<span aria-hidden="true">&times;</span></button><h4 class="modal-title" id="gridSystemModalLabel">提示</h4></div>' +
            '<div class="modal-body clear" id="tooltipModelSuccessContentMsg">' + content + '</div></div></div></div>'
        $('body').append(modelHtml);
    }
    var tag = $('#tooltipModelSuccess');
    $('#tooltipModelSuccessContentMsg').text(content);
    tag.modal('show');
    var time = 1500;
    if (isNotBlank(closeTime)) {
        time = closeTime;
    }
    setTimeout(function () {
        tag.modal('hide');
        if (isFunction(callback)) {
            callback();
        }
    }, time);
}

// 提示框弹出，定时消失
function dialogModalInfo(content, closeTime, callback) {
    if (isBlank($('#tooltipModelInfo'))) {
        var modelHtml = '<div id="tooltipModelInfo" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">' +
            '<div class="modal-dialog modal-lg" role="document"><div id="tooltipModelInfoContent" class="modal-content">' +
            '<div class="modal-header myModal-info-header"><button type="button" class="close" data-dismiss="modal" aria-label="Close">' +
            '<span aria-hidden="true">&times;</span></button><h4 class="modal-title" id="gridSystemModalLabel">提示</h4></div>' +
            '<div class="modal-body clear" id="tooltipModelInfoContentMsg">' + content + '</div></div></div></div>'
        $('body').append(modelHtml);
    }
    var tag = $('#tooltipModelInfo');
    $('#tooltipModelInfoContentMsg').text(content);
    tag.modal('show');
    var time = 1500;
    if (isNotBlank(closeTime)) {
        time = closeTime;
    }
    setTimeout(function () {
        tag.modal('hide');
        if (isFunction(callback)) {
            callback();
        }
    }, time);
}

// 提示框弹出，定时消失
function dialogModalError(content, closeTime, callback) {
    if (isBlank($('#tooltipModelError'))) {
        var modelHtml = '<div id="tooltipModelError" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">' +
            '<div class="modal-dialog modal-lg" role="document"><div id="tooltipModelErrorContent" class="modal-content">' +
            '<div class="modal-header myModal-error-header"><button type="button" class="close" data-dismiss="modal" aria-label="Close">' +
            '<span aria-hidden="true">&times;</span></button><h4 class="modal-title" id="gridSystemModalLabel">提示</h4></div>' +
            '<div class="modal-body clear" id="tooltipModelErrorContentMsg">' + content + '</div></div></div></div>'
        $('body').append(modelHtml);
    }
    var tag = $('#tooltipModelError');
    $('#tooltipModelErrorContentMsg').text(content);
    tag.modal('show');
    var time = 1500;
    if (isNotBlank(closeTime)) {
        time = closeTime;
    }
    setTimeout(function () {
        tag.modal('hide');
        if (isFunction(callback)) {
            callback();
        }
    }, time);
}

// 红色提示框
function fade(tag) {
    $(tag).addClass('border-red');
    setTimeout(function () {
        $(tag).removeClass("border-red");
    }, 1500);
}

function ajaxGet(url, callback) {
    $.ajax({
        url: url,
        success: function (res) {
            var code = res.responseCode;
            if (code == '0000') {
                if(isFunction(callback)){
                    callback(res);
                }
            } else {
                var msg = res.responseMsg;
                dialogModalError(msg, 2000);
            }
        }
    });
}

function ajaxPost(url, data, callback) {
    $.ajax({
        url: url,
        type: 'post',
        contentType: "application/json",
        data: toJson(data),
        success: function (res) {
            var code = res.responseCode;
            if (code == '0000') {
                var resData = res.data;
                if (isFunction(callback)) {
                    callback(resData);
                }
            } else {
                var msg = res.responseMsg;
                dialogModalError(msg, 1500);
            }
        }
    });
}
