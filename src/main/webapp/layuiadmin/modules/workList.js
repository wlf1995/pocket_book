/**
 * 项目管理扩展JS
 */
 
layui.define(function(exports){
    var $ = layui.$
        ,form = layui.form
        ,admin = layui.admin
        ,table = layui.table;

    var obj = {
        addItem: function(ret){
            //重新加载下table
            table.reload('gongdanListTable');
        }
    };

    //输出test接口
    exports('workList', obj);
});