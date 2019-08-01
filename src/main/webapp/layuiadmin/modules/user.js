/**

 @Name：layuiAdmin 用户登入和注册等
 @Author：贤心
 @Site：http://www.layui.com/admin/
 @License: LPPL
    
 */
 
layui.define('form', function(exports){
  var $ = layui.$
  ,layer = layui.layer
  ,laytpl = layui.laytpl
  ,setter = layui.setter
  ,view = layui.view
  ,admin = layui.admin
  ,form = layui.form;

  var $body = $('body');
  
  //自定义验证
  form.verify({
    nickname: function(value, item){ //value：表单的值、item：表单的DOM对象
      if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
        return '用户名不能有特殊字符';
      }
      if(/(^\_)|(\__)|(\_+$)/.test(value)){
        return '用户名首尾不能出现下划线\'_\'';
      }
      if(/^\d+\d+\d$/.test(value)){
        return '用户名不能全为数字';
      }
    }
    
    //我们既支持上述函数式的方式，也支持下述数组的形式
    //数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
    ,pass: [
      /^[\S]{6,18}$/
      ,'密码必须6到18位，且不能出现空格'
    ] 
  });
  
  
  //发送短信验证码
  admin.sendAuthCode({
    elem: '#LAY-user-getsmscode'
    ,elemPhone: '#LAY-user-login-cellphone'
    ,elemVercode: '#LAY-user-login-vercode'
    ,ajax: {
      url: layui.setter.base + 'json/user/sms.js' //实际使用请改成服务端真实接口
    }
  });
  
  
  
  
  //更换图形验证码
  $body.on('click', '#LAY-user-get-vercode', function(){
      var othis = $(this);
      admin.req({
          url: "/getImage",
          type: "POST",
          data: {
              guid:getguid(),
          },
          done: function (ret) {
              $(othis).attr("src", ret.data);
              if (ret.code == 200) {
                  $(othis).attr("src", ret.data);
              } else {
                  layer.msg("请重试", {
                      offset: '15px'
                      , icon: 1
                      , time: 1000
                  });
              }
          }
          , error: function () {
              layer.msg("请重试", {
                  offset: '15px'
                  , icon: 1
                  , time: 1000
              });
          }
      });
  }).click();
  
  //对外暴露的接口
  exports('user', {});
});
function getguid() {
    var guid= 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
    localStorage.setItem("guid",guid);console.log(guid)
    return guid;
}