var total;
var cp = 1;
var rows = 10;
var vm;

function load() {
    vm = new Vue({
        el: "#systemmenu",
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
                    url: '/systemmenu/list',
                    params: data
                }).then(function (response) {
                    console.log(response.data.data.content);
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
                    content: ['/webView/base/systemmenu_update.html'],
                    success: function (layero, index) {
                        var iframe = window['layui-layer-iframe' + index];
                        iframe.getdata(id);
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
        area: ['620px', '450px'],
        content: ['/webView/base/systemmenu_save.html'],
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
            options: [],
            parents: [],
            loading: false,
            typeval: '',
            parentval: {},
        },
        created: function () {
            axios({
                method: 'post',
                url: '/systemmenu/get',
                params: {
                    id: id,
                }
            }).then(function (response) {
                debugger
                getdata.data = response.data.data;
                getdata.options = getEnums('EnumMenuType');
                getdata.typeval = response.data.data.type.index;
                if (response.data.data.parentMenu && response.data.data.parentMenu.id != 0) {
                    getdata.parentval = response.data.data.parentMenu.id;
                    getdata.parents = [
                        {
                            id: response.data.data.parentMenu.id,
                            name: response.data.data.parentMenu.name
                        }
                    ];
                    getdata.changeparentval();
                }
                getdata.changetypeval();

            })
        }
        , updated: function () {

        }
        , methods: {
            remoteMethod: function (query) {
                if (query) {
                    axios({
                        method: 'post',
                        url: '/systemmenu/getParent',
                        params: {
                            name: query,
                            id: 0
                        }
                    }).then(function (response) {
                        getdata.parents = response.data.data;
                    })
                }
            },
            changetypeval: function () {
                $('#typeIndex').val(getdata.typeval);
            },
            changeparentval: function () {
                if (!getdata.parentval) {
                    $('#parentMenu').val(0);
                    return;
                }
                $('#parentMenu').val(getdata.parentval);
            },

        }
    })
}

function selectparent(name, id) {
    if (!id) {
        id = 0;
    }
    var parentopt = new Vue({
        el: "#parent",
        data: {
            options: [],
            value: '',
            loading: false
        },
        methods: {
            remoteMethod: function (query) {
                if (query) {
                    axios({
                        method: 'post',
                        url: '/systemmenu/getParent',
                        params: {
                            name: query,
                            id: id
                        }
                    }).then(function (response) {
                        parentopt.options = response.data.data;
                        console.log(parentopt.options)
                    })
                }
            },
            changeval: function () {
                $('#parentMenu').val(parentopt.value);
            }
        }
    });
}

function selectType(index) {
    if (!index) {
        index = 0;
    }
    var selecttype = new Vue({
        el: "#selecttype",
        data: {
            value: '',
            options: getEnums('EnumMenuType')
        },
        created: function () {
            // selecttype.options = getEnums('EnumMenuType');
            // console.log(this.options )
        },
        methods: {
            changeval: function () {
                $('#typeIndex').val(selecttype.value);
            }
        }
    })

}
