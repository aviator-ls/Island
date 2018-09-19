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

function outLogin() {
    $.ajax({
        url: baseUrl + '/api/user/outLogin'
    })
    window.open(baseUrl + '/index', '_self');
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

function toJson(data) {
    return JSON.stringify(data);
}

function html_encode(str) {
    var s = "";
    if (str.length == 0)
        return "";
    s = str.replace(/&/g, "&gt;");
    s = s.replace(/</g, "&lt;");
    s = s.replace(/>/g, "&gt;");
    s = s.replace(/ /g, "&nbsp;");
    s = s.replace(/\'/g, "&#39;");
    s = s.replace(/\"/g, "&quot;");
    s = s.replace(/\n/g, "<br>");
    return s;
}

function html_decode(str) {
    var s;
    if (str.length == 0)
        return "";
    s = str.replace(/&gt;/g, "&");
    s = s.replace(/&lt;/g, "<");
    s = s.replace(/&gt;/g, ">");
    s = s.replace(/&nbsp;/g, " ");
    s = s.replace(/&#39;/g, "\'");
    s = s.replace(/&quot;/g, "\"");
    s = s.replace(/<br>/g, "\n");
    return s;
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

function ajaxGet(url, sucCallback, errorCallback) {
    $.ajax({
        url: url,
        success: function () {
            if(isNotBlank(sucCallback)){
                sucCallback();
            }
        },
        error: function () {
            if(isNotBlank(errorCallback)){
                errorCallback();
            }
        }
    });
}

function ajaxPost(url, data, sucCallback, errorCallback) {
    $.ajax({
        url: url,
        type: 'post',
        contentType: "application/json",
        data: toJson(data),
        success: function () {
            if(isNotBlank(sucCallback)){
                sucCallback();
            }
        },
        error: function () {
            if(isNotBlank(errorCallback)){
                errorCallback();
            }
        }
    });
}

function ajaxDelete(url, sucCallback, errorCallback) {
    $.ajax({
        url: url,
        type: 'delete',
        success: function () {
            if(isNotBlank(sucCallback)){
                sucCallback();
            }
        },
        error: function () {
            if(isNotBlank(errorCallback)){
                errorCallback();
            }
        }
    });
}

function ajaxPut(url, data, sucCallback, errorCallback) {
    $.ajax({
        url: url,
        type: 'put',
        contentType: "application/json",
        data: toJson(data),
        success: function () {
            if(isNotBlank(sucCallback)){
                sucCallback();
            }
        },
        error: function () {
            if(isNotBlank(errorCallback)){
                errorCallback();
            }
        }
    });
}
