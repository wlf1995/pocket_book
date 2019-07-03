var total;
var cp = 1;
var rows = 10;
var vm;

function load() {
    vm = new Vue({
        el: "#bangonqu",
        data: {
            datas: []
        },
        created: function () {
            this.list();
            $('#search').click(function () {
                cp = 1;
                vm.list();
            });
        },
        updated: function () {
            page();
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
                    url: '/bangongqu/list',
                    params: data
                }).then(function (response) {
                    vm.datas = response.data.data.content;
                    total = response.data.total;
                })
                layer.close(load);
            },
            openupdate: function (id) {
                layer.open({
                    type: 2,
                    shadeClose: false, //开启遮罩关闭
                    title: false,
                    closeBtn: 0,
                    maxmin: true,
                    area: ['60%', '40%'],
                    content: ['/webView/base/bangongqu_update.html'],
                    success: function (layero, index) {
                        var iframe = window['layui-layer-iframe' + index];
                        iframe.getdata(id);
                    },
                    end: function () {
                        vm.list();
                    }
                });
            },
            deleteOk: function (id) {
                axios({
                    method: 'post',
                    url: '/bangongqu/deleteOK',
                    params: {id:id}
                }).then(function (response) {
                    vm.list();
                })

            }
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
    layer.open({
        type: 2,
        shadeClose: false, //开启遮罩关闭
        title: false,
        closeBtn: 0,
        maxmin: true,
        area: ['60%', '40%'],
        content: ['/webView/base/bangongqu_save.html'],
        end: function () {
            vm.list();
        }
    });
}



function getdata(id) {
    var getdata = new Vue({
        el: "#formDIV",
        data: {
            data: []
        },
        created: function () {
            axios({
                method: 'post',
                url: '/bangongqu/get',
                params: {
                    id: id,
                }
            }).then(function (response) {
                getdata.data = response.data.data;
            })
        }
    })
}




