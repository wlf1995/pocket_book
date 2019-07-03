var total;
var cp = 1;
var rows = 10;
var vm;

function load() {
    vm = new Vue({
        el: "#systemrole",
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
                    url: '/systemrole/list',
                    params: data
                }).then(function (response) {
                    vm.datas = response.data.data.content;
                    total = response.data.data.total;
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
                    area: ['620px', '200px'],
                    content: ['/webView/base/systemrole_update.html'],
                    success: function (layero, index) {
                        var iframe = window['layui-layer-iframe' + index];
                        iframe.getdata(id);
                    },
                    end: function () {
                        vm.list();
                    }
                });
            },
            openshouquan: function (id) {
                layer.open({
                    type: 2,
                    shadeClose: false, //开启遮罩关闭
                    title: false,
                    closeBtn: 0,
                    maxmin: true,
                    area: ['100%', '100%'],
                    content: ['/webView/base/systemrole_authorized.html'],
                    success: function (layero, index) {
                        var iframe = window['layui-layer-iframe' + index];
                        iframe.autho(id);
                    },
                    end: function () {
                        vm.list();
                    }
                });
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
        area: ['620px', '200px'],
        content: ['/webView/base/systemrole_save.html'],
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
                url: '/systemrole/get',
                params: {
                    id: id,
                }
            }).then(function (response) {
                getdata.data = response.data.data;
            })
        }
    })
}

function autho(id) {
    var ve=new Vue({
        el: "#tree",
        data: {
             data:[],
            defaultProps: {
                children: 'childs',
                label: 'name'
            },
            checks:[]
        },
        created:function () {
            this.load(id);
        },
        methods:{
            load:function (id) {
                axios({
                    method: 'post',
                    url: '/systemrole/getAuthoData',
                    params: {
                        id:id
                    }
                }).then(function (response) {
                    ve.data=response.data.data.menus;
                    if(response.data.data.checks){
                        $('#ids').val(response.data.data.checks.join(","));
                    }
                    ve.checks=response.data.data.checks;
                })
                $('#roleid').val(id);
            },
            getCheckedKeys:function () {
                console.log(this.$refs.tree.getCheckedKeys());
                $('#ids').val(this.$refs.tree.getCheckedKeys());
            }
        }
    });
}


