var total;
var cp = 1;
var rows = 10;
var vm;

function load() {
    vm = new Vue({
        el: "#sysdept",
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
                    url: '/systemdept/list',
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
                    area: ['60%', '80%'],
                    content: ['/webView/base/systemdept_update.html'],
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
                    url: '/systemdept/deleteOK',
                    params: {id: id}
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
        area: ['60%', '80%'],
        content: ['/webView/base/systemdept_save.html'],
        end: function () {
            vm.list();
        }
    });
}


function getdata(id) {
    var getdata = new Vue({
        el: "#formDIV",
        data: {
            data: {},
            parentoptions:[],
            useroptions:[],
            sysUserId:'',
            parentDeptId:''
        },
        created: function () {
            axios({
                method: 'post',
                url: '/systemdept/get',
                params: {
                    id: id,
                }
            }).then(function (response) {
                // console.log(response.data.data)
                getdata.data = response.data.data;
                getdata.sysUserId=response.data.data.sysUserId
                getdata.parentDeptId=response.data.data.parentDeptId
                getdata.useroptions=[{
                    id: response.data.data.sysUserId,
                    realName: response.data.data.sysUserName
                }]
                console.log(getdata.useroptions)
            })
            this.getlist()
        },
        methods: {
            getlist: function () {
                axios({
                    method: 'post',
                    url: '/systemdept/getByDict',
                    params: {}
                }).then(function (response) {
                    getdata.parentoptions = response.data.data;
                })
            },
            remoteMethod: function (query) {
                if (query) {
                    axios({
                        method: 'post',
                        url: '/user/getuser',
                        params: {
                            realName: query,
                            id: 0
                        }
                    }).then(function (response) {
                        getdata.useroptions = response.data.data;
                        console.log(getdata.useroptions)
                    })
                }
            },
            changeval: function () {
                $('#sysUser').val(getdata.sysUserId);
            }
        }
    })
}


function selectparent() {
    var parentopt = new Vue({
        el: "#parent",
        data: {
            options: [],
            value: '',
        },
        created: function () {
            this.getlist();
        },
        methods: {
            getlist: function () {
                axios({
                    method: 'post',
                    url: '/systemdept/getByDict',
                    params: {}
                }).then(function (response) {
                    parentopt.options = response.data.data;
                })

            },
            changeval: function () {
                $('#parentDept').val(parentopt.value);
            }
        }
    });
}

function selectuser(name) {
    var useropt = new Vue({
        el: "#lingdao",
        data: {
            options: [],
            value: '',
        },
        created: function () {
            this.remoteMethod();
        },
        methods: {
            remoteMethod: function (query) {
                if (query) {
                    axios({
                        method: 'post',
                        url: '/user/getuser',
                        params: {
                            realName: query,
                            id: 0
                        }
                    }).then(function (response) {
                        useropt.options = response.data.data;
                    })
                }
            },
            changeval: function () {
                $('#sysUser').val(useropt.value);
            }
        }
    });
}
