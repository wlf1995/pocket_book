package com.ibicn.hr.util;


import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 输出类的所有set方法或者存放到map中的字符串
 * object.setId(object1.getId);
 */
public class PrintClassMethodsNameUtil {

    //输出对象的所有set方法

    /**
     * @param clazz       对象的类
     * @param objectName  需要设置 值的对象的名字
     * @param objectName1 get值的对象的名字
     */
    public static void printBySet(Class clazz, String objectName, String objectName1) {
        Method[] mths = clazz.getDeclaredMethods();
        for (int i = 0; i < mths.length; i++) {
            String methodName = mths[i].getName();
            String methodName1 = methodName.substring(1);
            if (methodName.contains("get")) {
                String typename = mths[i].getReturnType().getName();
                if (typename.contains("java") || typename.contains("Enum") || typename.length() < 7) {
                    System.out.println(objectName + ".s" + methodName1 + "(" + objectName1 + ".g" + methodName1 + "());");
                }
            }
        }

        for (int i = 0; i < mths.length; i++) {
            String methodName = mths[i].getName();
            String methodName1 = methodName.substring(1);
            if (methodName.contains("get")) {
                String typename = mths[i].getReturnType().getName();
                if (!typename.contains("java") && !typename.contains("void") && !typename.contains("Enum") && typename.length() > 6) {
                    System.out.println("if(" + objectName1 + ".g" + methodName1 + "()!=null){");
                    System.out.println("    " + objectName + ".s" + methodName1 + "(" + objectName1 + ".g" + methodName1 + "());");
                    System.out.println("}");
                }
            }
        }
    }

    //输出 把对象属性放到map中 的字符串

    /**
     * @param clazz   对象的类
     * @param objName 对象名
     */
    public static void printByMap(Class clazz, String objName) {
        Method[] mths = clazz.getDeclaredMethods();
        Map<String, Object> map = new HashMap<>();
        System.out.println("Map<String,Object> map=new HashMap<>();");
        for (int i = 0; i < mths.length; i++) {
            String methodName = mths[i].getName();
            String methodName1 = methodName.substring(1);
            String fieldName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
            //输出返回值类型为类库类型的
            if (methodName.contains("get")) {
                String typename = mths[i].getReturnType().getName();
                if (typename.contains("java") || typename.contains("Enum") || typename.length() < 7) {
                    System.out.println("map.put(\"" + fieldName + "\"," + objName + ".g" + methodName1 + "());");
                }

            }


        }
        System.out.println();
        System.out.println();
        System.out.println();

        for (int i = 0; i < mths.length; i++) {
            String methodName = mths[i].getName();
            String methodName1 = methodName.substring(1);
            String fieldName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
            //输出返回值类型为对象类型的
            if (methodName.contains("get")) {
                String typename = mths[i].getReturnType().getName();
                if (!typename.contains("java") && !typename.contains("void") && !typename.contains("Enum") && typename.length() > 6) {
                    System.out.println();
                    String[] split = typename.split("\\.");
                    String className = split[split.length - 1];

                    System.out.println(className + " " + fieldName + " =" + objName + ".g" + methodName1 + "();");
                    System.out.println("    " + className + " " + fieldName + "1 =new " + className + "();");
                    System.out.println("if(" + fieldName + "!=null){");
                    System.out.println();
                    System.out.println("}");
                    System.out.println("    map.put(\"" + fieldName + "\"," + fieldName + "1);");
                }
            }
        }

    }

    /**
     * 输出控制层的基本方法
     * @param clazz 实体类 的class对象
     * @param shuoming 实体类的功能说明
     */
    public static void printController(Class clazz, String shuoming) {
        String[] classnames = clazz.getName().split("\\.");
        String className = classnames[classnames.length - 1];
        String serviceName = className.substring(0, 1).toLowerCase() + className.substring(1, className.length());
        //输出list方法
        /**  @Description:
         *  @Author: Mick
         **/
        System.out.println(" /**  @Description:  "+shuoming +"列表");
        System.out.println("   *  @Author: Mick");
        System.out.println("   **/");
        System.out.println("@RequestMapping(\"list\")");
        System.out.println("public void list(" + className + " data, BaseModel baseModel) {");
        System.out.println("PagedResult pr = " + serviceName + "Service.getList(data, baseModel);");
        System.out.println("writePage(pr);");
        System.out.println("}");
        //输出get方法
        System.out.println("");
        System.out.println(" /**  @Description:  获取"+shuoming);
        System.out.println("   *  @Author: Mick");
        System.out.println("   **/");
        System.out.println(" @RequestMapping(\"get\")");
        System.out.println(" public void get(" + className + " data){");
        System.out.println("if (data.get" + className + "Id() == null){");
        System.out.println(" writeString(\"没有对应的数据\",null);");
        System.out.println("return;");
        System.out.println("}");
        System.out.println(className + " " + serviceName + "  =" + serviceName + "Service.getById(data.get" + className + "Id());");
        System.out.println("writeString(null," + serviceName + ");");
        System.out.println("}");
        System.out.println("");
        //输出保存方法
        System.out.println(" /**  @Description:  保存"+shuoming);
        System.out.println("   *  @Author: Mick");
        System.out.println("   **/");
        System.out.println("@RequestMapping(\"saveOK\")");
        System.out.println("public void saveOK(" + className + " data){");

        //验证必填
        System.out.println("    //验证必填");
        System.out.println();
        System.out.println();
        System.out.println(className + " " + serviceName + " = new " + className + "();");

        //设置数据
        System.out.println(" //设置数据");
        System.out.println();
        System.out.println();

        System.out.println(serviceName + "Service.save(" + serviceName + ");");
        System.out.println("writeString(null,\"新增成功\");");
        System.out.println("}");

        System.out.println("");
        //输出更新方法
        System.out.println(" /**  @Description:  修改"+shuoming);
        System.out.println("   *  @Author: Mick");
        System.out.println("   **/");
        System.out.println("@RequestMapping(\"updateOK\")");
        System.out.println("public void updateOK(" + className + " data){");

        //验证必填
        System.out.println("    //验证必填");
        System.out.println();
        System.out.println();
        System.out.println(className + " " + serviceName + " = new " + className + "();");

        //设置数据
        System.out.println(" //设置数据");
        System.out.println();
        System.out.println();

        System.out.println(serviceName + "Service.update(" + serviceName + ");");
        System.out.println("writeString(null,\"修改成功\");");
        System.out.println("}");
        System.out.println("");
        //输出删除方法
        System.out.println(" /**  @Description:  删除"+shuoming);
        System.out.println("   *  @Author: Mick");
        System.out.println("   **/");
        System.out.println("@RequestMapping(\"delete\")");
        System.out.println("public void delete(" + className + " data){");

        //验证必填
        System.out.println("    //验证必填");
        System.out.println();
        System.out.println();
        System.out.println(className + " " + serviceName + " = new " + className + "();");

        //设置数据
        System.out.println(" //设置数据");
        System.out.println();
        System.out.println();

        System.out.println(serviceName + "Service.update(" + serviceName + ");");
        System.out.println("writeString(null,\"删除成功\");");
        System.out.println("}");
    }

    public static void main(String[] args) {
        //printByMap(Fahuo.class, "fahuo");
        //printController(Fahuo.class,"运费");
        String variables[] = new String[50000];
        for( int i=0;i <50000;i++){
            variables[i] = "s"+i;
        }
        long startTime0 = System.currentTimeMillis();
        for(int i=0;i<50000;i++){
            variables[i] = "hello";
        }
        long endTime0 = System.currentTimeMillis();
        System.out.println("Creation time"
                + " of String literals : "+ (endTime0 - startTime0)
                + " ms" );
        long startTime1 = System.currentTimeMillis();
        for(int i=0;i<50000;i++){
            variables[i] = new String("hello");
        }
        long endTime1 = System.currentTimeMillis();
        System.out.println("Creation time of"
                + " String objects with 'new' key word : "
                + (endTime1 - startTime1)
                + " ms");
        long startTime2 = System.currentTimeMillis();
        for(int i=0;i<50000;i++){
            variables[i] = new String("hello");
            variables[i] = variables[i].intern();
        }
        long endTime2 = System.currentTimeMillis();
        System.out.println("Creation time of"
                + " String objects with intern(): "
                + (endTime2 - startTime2)
                + " ms");
    }
}
