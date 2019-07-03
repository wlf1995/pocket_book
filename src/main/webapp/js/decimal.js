/**
 * Created by tanshuai on 2016/12/8.
 */


function accAdd(arg1,arg2){
    var r1,r2,m;
    try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
    m=Math.pow(10,Math.max(r1,r2))
    return (arg1*m+arg2*m)/m
}


function accSub(arg1,arg2){
    return accAdd(arg1,-arg2);
}



function accMul(arg1,arg2)
{
    var m=0,s1=arg1.toString(),s2=arg2.toString();
    try{m+=s1.split(".")[1].length}catch(e){}
    try{m+=s2.split(".")[1].length}catch(e){}
    return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
}



function accDiv(arg1,arg2){
    if(arg1==0||arg2==0){
        return 0;
    }
    var t1=0,t2=0,r1,r2;
    try{t1=arg1.toString().split(".")[1].length}catch(e){}
    try{t2=arg2.toString().split(".")[1].length}catch(e){}
    with(Math){
        r1=Number(arg1.toString().replace(".",""))
        r2=Number(arg2.toString().replace(".",""))
        return (r1/r2)*pow(10,t2-t1);
    }
}


function formatCurrency(num) {
    num = num.toString().replace(/\$|\,/g,'');
    if(isNaN(num))
        num = "0";
    sign = (num == (num = Math.abs(num)));
    num = Math.floor(num*100+0.50000000001);
    cents = num%100;
    num = Math.floor(num/100).toString();
    if(cents<10)
        cents = "0" + cents;
    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
        num = num.substring(0,num.length-(4*i+3))+','+
            num.substring(num.length-(4*i+3));
    return (((sign)?'':'-') + num + '.' + cents);
}


//加法
$.add=function (arg1, arg2) {
    return accAdd(arg1, arg2);
}
//减法
$.subtract=function (arg1, arg2) {
    return accSub(arg1, arg2);
}
//乘法
$.multiply=function (arg1, arg2) {
    return accMul(arg1, arg2);
}
//除法
$.divide=function (arg1, arg2) {
    return accDiv(arg1, arg2);
}
//比较大小
$.compare=function (arg1,arg2) {
        if(accAdd(arg1,0)>accAdd(arg2,0)){
            return 1;
        }else if(accAdd(arg1,0)<accAdd(arg2,0)){
            return -1;
        }else if(accAdd(arg1,0)==accAdd(arg2,0)){
            return 0;
        }
}

$.formatCurrency=function (num) {
    return formatCurrency(num);
}