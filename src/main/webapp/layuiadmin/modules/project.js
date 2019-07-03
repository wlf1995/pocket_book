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
            table.reload('tablelayui');
        },
        showData: function (projectId) {
            //回显数据
            admin.req({
                url : "/project/get",
                type:"POST",
                data:{
                    projectId : projectId
                },
                done: function(ret){
                    $("#projectName").val(ret.data.projectName);
                    $("#fenpairenBianhao").val(ret.data.fenpairenBianhao);

                    //工程师回显，循环判断
                    $("input:checkbox[name='userBianhaos']").each(function() { // 遍历多选框
                        for (var i = 0 ; i < ret.data.userBianhaoArray.length; i++) {
                            if ($(this).val() == ret.data.userBianhaoArray[i].userBianhao) {
                                $(this).attr("checked","true");
                            }
                        }
                    });

                    form.render(null,'formDIV');
                }
                ,error: function(){
                    location.href = "/common/error.html";
                }
            });
        }
    };

    //输出test接口
    exports('project', obj);
});