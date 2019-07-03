document.write("<script src='/js/jquery-2.1.4.js'></script>");
document.write("<script src='/layuiadmin/layui/layui.all.js'></script>");
document.write("<script src='/js/vue/vue.js'></script>");
document.write("<script src='/js/vue/axios.min.js'></script>");
document.write("<script src='/js/element/index.js'></script>");
document.write("<script src='/js/decimal.js'></script>");

function getUrlParameters() {
    var par = {};
    var name, value;
    var str = location.href; //取得整个地址栏
    var num = str.indexOf("?")
    str = str.substr(num + 1); //取得所有参数   stringvar.substr(start [, length ]

    var arr = str.split("&"); //各个参数放到数组里
    for (var i = 0; i < arr.length; i++) {
        num = arr[i].indexOf("=");
        if (num > 0) {
            name = arr[i].substring(0, num);
            value = arr[i].substr(num + 1);
            par[name] = value;
            if (name == "callback") {
                callback = value.split(".");
            }
        }
    }
    console.log(JSON.stringify(par));
    return par;
}


function subimitdata(type, url) {
    var load = layer.load(1);
    var str = "";
    var flag = false;

    $.each($('[' + type + '=true]'), function (i, n) {
        if (flag) {
            str += ",";
        }
        flag = true;
        str += "'" + $(this).attr('name') + "'" + ":" + "'" + $(this).val() + "'";
    });
    str = "{" + str.replace(/\n/g, "\\\\n") + "}";
    var data = eval('(' + str + ')');
    axios({
        method: 'post',
        url: url,
        params: data
    }).then(function (response) {
        layer.close(load);
        if (response.data.code == '200') {
            clowsview();
        }
        layer.msg(response.data.msg);
    })
}



function getEnums(name) {
    if (!name) {
        return [];
    }
    var options;
    var item = sessionStorage.getItem(name);
    //判断字典中存在则直接返回
    if (item) {
        console.log(JSON.parse(item))
        return JSON.parse(item);
    }
    $.ajax({
        url: "/selectEnum",
        type: "post",
        data: {"name": name},
        async: false,
        dataType: "json",
        success: function (data) {
            if(data.code==200){
                sessionStorage.setItem(name,data.data);
                options = data.data;
            }
        }
    });
    return options
}

function clowsview() {
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    parent.layer.close(index);
}

function FormatDate(datetime, fmt) {
    if (parseInt(datetime) == datetime) {
        if (datetime.length == 10) {
            datetime = parseInt(datetime) * 1000;
        } else if (datetime.length == 13) {
            datetime = parseInt(datetime);
        }
    }
    datetime = new Date(datetime);
    var o = {
        "M+": datetime.getMonth() + 1,                 //月份
        "d+": datetime.getDate(),                    //日
        "h+": datetime.getHours(),                   //小时
        "m+": datetime.getMinutes(),                 //分
        "s+": datetime.getSeconds(),                 //秒
        "q+": Math.floor((datetime.getMonth() + 3) / 3), //季度
        "S": datetime.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (datetime.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}


function closeTab() {
    parent.layui.element.tabDelete('layadmin-layout-tabs', location.pathname + location.search); //删除tab
}

/**
 * 根据用户编号进行登录
 */
function bianhaoByLogin(bianhao) {
    var user;
    $.ajax({
        type: "POST", //提交方式
        url: "/user/bianhaoBYloginOk",//路径
        async: false,
        data: {
            'userBianhao': bianhao,
        },//数据，这里使用的是Json格式进行传输
        success: function (response) {//返回数据根据结果进行相应的处理
            if (response.data.status == 0) {
                alert(response.data.errmsg)
                return
            }
            user = JSON.parse(response).data;
            localStorage.setItem("layuiAdmin", JSON.stringify(user));
        }
    });
    return user;
}

