var total;
var cp = 1;
var rows = 10;
var vm;
var table;
var selects;

function load() {
    layui.use(['table'], function () {
        table = layui.table;
    });
    selects = new Vue({
        el: "#searchs",
        data: {
            addTime: '',
            companys: [],
            companysValue: '',
            loading: false,
        },
        created: function () {
            axios({
                method: 'post',
                url: '/company/getCompanyByUser',
            }).then(function (response) {
                selects.companys = response.data.data;
            })
        }, methods: {
            remoteMethodCompanys: function (query) {
                if (query) {
                    axios({
                        method: 'post',
                        url: '/company/getCompanyByUser',
                        params: {
                            text: query,
                        }
                    }).then(function (response) {
                        selects.companys = response.data.data;
                    })
                }
            },
            changeval: function (type) {
                if (type == 'companys') {
                    $('#company').val(selects.companysValue);
                }
            }
        }
    });
    vm = new Vue({
        el: "#upFiletable",
        data: {
            datas: [],

        },
        created: function () {
            this.list();
        },
        updated: function () {
            page();
            table.init('upFiletable', {});
        },
        methods: {
            list: function () {
                var load = layer.load(1);
                var str = "'page':'" + cp + "' ,'limit' :'" + rows + "'";
                $.each($('[search=true]'), function (i, n) {
                    if ($(this).val()) { //如果值不为空，则传
                        str += ",'" + $(this).attr('name') + "'" + ":" + "'" + $(this).val() + "'";
                    }
                });
                str = "{" + str + "}";
                var data = eval('(' + str + ')');
                axios({
                    method: 'post',
                    url: '/UploadFile/list',
                    params: data
                }).then(function (response) {
                    vm.datas = response.data.data.result;
                    total = response.data.data.recordCount;
                    layer.close(load);
                })
            },
        }
    })
}

function page() {
    layui.laypage.render({
        elem: 'page'
        , limit: rows
        , count: total
        , groups: 3
        , hash: 'fenye' //自定义hash值
        , jump: function (obj, first) {
            if (first) {
                return;
            }
            cp = obj.curr;
            vm.list(obj.curr);
        }
        , curr: cp
        , layout: ['count', 'prev', 'page', 'next', 'skip']
    });
}


function opensave() {
    var perContent = layer.open({
        type: 2,
        shadeClose: false, //开启遮罩关闭
        title: false,
        closeBtn: 0,
        area: ['620px', '800px'],
        content: ['/webView/base/upload_file_save.html'],
        end: function () {
            vm.list();
        }
    });
    layer.full(perContent);
}

function openupdate(id) {
    layer.open({
        type: 2,
        shadeClose: false, //开启遮罩关闭
        title: false,
        closeBtn: 0,
        maxmin: true,
        area: ['100%', '100%'],
        content: ['/webView/base/upload_file_update.html'],
        success: function (layero, index) {
            var iframe = window['layui-layer-iframe' + index];
            iframe.openUpdateLoad(id);
        },
        end: function () {
            vm.list();
        }
    });
}

function opensaveLoad() {
    sel = new Vue({
        el: "#saveUpLoad",
        data: {
            companys: [],
            companysValue: '',
            loading: false,
            addTime: '',
        },
        created: function () {
            axios({
                method: 'post',
                url: '/company/getCompanyByUser',
            }).then(function (response) {
                sel.companys = response.data.data;
            })
        },
        methods: {
            remoteMethodCompanys: function (query) {
                if (query) {
                    axios({
                        method: 'post',
                        url: '/company/getCompanyByUser',
                        params: {
                            text: query,
                        }
                    }).then(function (response) {
                        sel.companys = response.data.data;
                    })
                }
            },
            changeval: function (type) {
                if (type == 'companys') {
                    $('#company').val(sel.companysValue);
                }
            }
        }
    })
}

function opensaveOK(obj) {
    axios({
        method: 'post',
        url: '/UploadFile/saveOk',
        params: obj.field
    }).then(function (response) {
        layer.msg("添加完成");
        //关闭窗口
        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        parent.layer.close(index);
    })

}

function delData(obj) {
    axios({
        method: 'post',
        url: '/UploadFile/delOk',
        params: {
            id: obj.data.id
        }
    }).then(function (response) {
        layer.msg("删除成功");
        vm.list();
    })
}

var sel;

function openUpdateLoad(id) {
    sel = new Vue({
        el: "#updateUpLoad",
        data: {
            datas: {},
            companys: [],
            companysValue: '',
            loading: false,
        },
        created: function () {
            this.initData();
        },
        methods: {
            initData: function () {
                axios({
                    method: 'post',
                    url: '/UploadFile/list',
                    params: {id: id}
                }).then(function (response) {
                        sel.datas = response.data.data.result[0];
                        $("[name=note]").val(sel.datas.note);
                        axios({
                            method: 'post',
                            url: '/company/getCompanyByUser',
                        }).then(function (response) {
                            sel.companys = response.data.data;
                            sel.companysValue = sel.datas.company.companyName;
                            $('#company').val(sel.datas.company.companyId);
                        })

                        fileurl(sel.datas.fileUrls.split(","));
                    }
                )
            },
            remoteMethodCompanys: function (query) {
                if (query) {
                    axios({
                        method: 'post',
                        url: '/company/getCompanyByUser',
                        params: {
                            text: query,
                        }
                    }).then(function (response) {
                        sel.companys = response.data.data;
                    })
                }
            },
            changeval: function (type) {
                if (type == 'companys') {
                    $('#company').val(sel.companysValue);
                }
            }
        }

    })
}

function openUpdateOK(obj) {
    axios({
        method: 'post',
        url: '/UploadFile/updateOk',
        params: obj.field
    }).then(function (response) {
        layer.msg("修改成功");
        //关闭窗口
        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        parent.layer.close(index);
    })

}

//回显上传文件
function fileurl(fileUrls) {
    console.log(fileUrls);
    for (var i = 0; i < fileUrls.length; i++) {
        if (fileUrls[i].length < 1) {
            continue;
        }
        var fileurl = '<div class="layui-btn-group fujianClass" id="fujian' + (i + 1) + '" ' +
            'url="' + fileUrls[i] + '"> ' +
            '<a class="layui-btn layui-btn-primary layui-btn-sm"' +
            ' href="' + fileUrls[i] + '" ' +
            'target="_blank">附件' + (i + 1) + '.' + fileUrls[i].toLowerCase().split('.').splice(-1)[0] + '</a> ' +
            '<a onclick="removeMe(this)" class="layui-btn layui-btn-primary layui-btn-sm">' +
            '<i class="layui-icon"></i></a> </div>';
        $("#fujianDIV").append(fileurl);
    }
}

//删除文件
function removeMe(obj) {
    $(obj).parent().remove();
}