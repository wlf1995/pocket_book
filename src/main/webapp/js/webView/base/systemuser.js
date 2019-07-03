var total;
var cp = 1;
var rows = 10;
var vm;

function load() {
    vm = new Vue({
        el: "#user",
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
                    url: '/user/list',
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
                    area: ['620px', '450px'],
                    content: ['/webView/base/systemuser_update.html'],
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
                    content: ['/webView/base/systemuser_authorized.html'],
                    success: function (layero, index) {
                        var iframe = window['layui-layer-iframe' + index];
                        iframe.autho(id);
                    }
                });
            },
            openshouquancompany:function (id) {
                layer.open({
                    type: 2,
                    shadeClose: false, //开启遮罩关闭
                    title: false,
                    closeBtn: 0,
                    maxmin: true,
                    area: ['100%', '100%'],
                    content: ['/webView/base/systemuser_authorizedCompany.html'],
                    success: function (layero, index) {
                        var iframe = window['layui-layer-iframe' + index];
                        iframe.authoCompany(id);
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
        area: ['620px', '450px'],
        content: ['/webView/base/systemuser_save.html'],
        end: function () {
            vm.list();
        }
    });
}



function getdata(id) {
    var getdata = new Vue({
        el: "#formDIV",
        data: {
            data: [],
            statusoption:[],
            value:''
        },
        created: function () {
            axios({
                method: 'post',
                url: '/user/get',
                params: {
                    id: id,
                }
            }).then(function (response) {
                getdata.data = response.data.data;
                getdata.statusoption = getEnums('EnumUserStatus');
                console.log(getdata.statusoption);
                getdata.value = response.data.data.userStatus.index;
                getdata.changeval();
            })
        },
        methods:{
            changeval:function () {
                $('#userStatusIndex').val(getdata.value);
            }
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
                    url: '/user/authoUser',
                    params: {
                        id:id
                    }
                }).then(function (response) {
                    ve.data=response.data.data.roles;
                    if(response.data.data.checks){
                        $('#ids').val(response.data.data.checks.join(","));
                    }
                    ve.checks=response.data.data.checks;
                })
                $('#userid').val(id);
            },
            getCheckedKeys:function () {
                $('#ids').val(this.$refs.tree.getCheckedKeys());
            }
        }
    });
}

function userStatus() {
    var selecttype = new Vue({
        el: "#selectuserstatus",
        data: {
            value:'',
            options: []
        },
        created: function () {
            axios({
                method: 'post',
                url: '/selectEnum',
                params: {
                    name:'EnumUserStatus',
                    index:0
                }
            }).then(function (response) {
                selecttype.options=response.data;

            })
        },
        methods:{
            changeval:function () {
                $('#userStatusIndex').val(selecttype.value);
            }
        }
    })
}


function authoCompany(id) {
    console.log(id);
    var ve=new Vue({
        el: "#companys",
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
                    url: '/user/authoCompany',
                    params: {
                        id:id
                    }
                }).then(function (response) {
                    ve.data=response.data.data.companys;
                    ve.checks=response.data.data.checks;

                })
                $('#userid').val(id);
            },
            getCheckedKeys:function () {
                $('#ids').val(ve.$refs.companys.getCheckedKeys());
            }
        }
    });
}

