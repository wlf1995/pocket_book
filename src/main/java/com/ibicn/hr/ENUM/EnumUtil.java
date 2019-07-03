package com.ibicn.hr.ENUM;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibicnCloud.util.StringUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class EnumUtil {
    private static <T extends Enum & IntegerValuedEnum> String getDrop_downByEnum(Class<T> enumClass, IntegerValuedEnum defaultEnumObject) {
        if (enumClass == null) {
            return "";
        }
        JSONArray array=new JSONArray();
        for (IntegerValuedEnum theEnum : enumClass.getEnumConstants()) {
            JSONObject object=new JSONObject();
            object.put("index",theEnum.getIndex());
            object.put("name",theEnum.getName());
            String selected = "";
            if (defaultEnumObject != null && theEnum.getIndex() == defaultEnumObject.getIndex()) {
                object.put("selected","selected");
            }else{
                object.put("selected","");
            }
            array.add(object);
        }
        return array.toJSONString();
    }

    public static <T extends Enum & IntegerValuedEnum> String getDrop_down(Class<T> enumClass, String defaultIndex) {
        if (enumClass == null) {
            return "";
        }
        return getDrop_downByEnum(enumClass, valueOf(enumClass, defaultIndex));
    }

    public static <T extends Enum & IntegerValuedEnum> String getDrop_down(Class<T> enumClass, int defaultIndex) {
        if (enumClass == null) {
            return "";
        }
        return getDrop_downByEnum(enumClass, valueOf(enumClass, defaultIndex));
    }

    public static <T extends Enum & IntegerValuedEnum> String getDrop_down(Class<T> enumClass) {
        if (enumClass == null) {
            return "";
        }
        return getDrop_downByEnum(enumClass, null);
    }

    private static <T extends Enum & IntegerValuedEnum> IntegerValuedEnum valueOf(Class<T> enumClass, int index) {
        if (enumClass == null) {
            return null;
        }
        for (IntegerValuedEnum theEnum : enumClass.getEnumConstants()) {
            if (theEnum.getIndex() == index) {
                return theEnum;
            }
        }
        return null;
    }

    public static <T extends Enum<?> & IntegerValuedEnum> IntegerValuedEnum valueOf(Class<T> enumClass, String index) {
        if (enumClass == null) {
            return null;
        }
        if (StringUtil.format(index).equals("") || !StringUtil.isInt(index)) {
            return null;
        } else {
            int indexInt = StringUtil.parseInt(index);
            return valueOf(enumClass, indexInt);
        }
    }

    public static <T extends Enum & IntegerValuedEnum> ArrayList getVector(Class<T> enumClass) {
        if (enumClass == null) {
            return null;
        }
        ArrayList ArrayList = new ArrayList();
        for (IntegerValuedEnum theEnum : enumClass.getEnumConstants()) {
            Object[] values = new Object[2];
            values[0] = theEnum.getIndex();
            values[1] = theEnum.getName();
            ArrayList.add(values);
        }
        return ArrayList;
    }

    public static <T extends Enum & IntegerValuedEnum> String EnumName(Class<T> enumClass, String defaultIndex) {
        if (enumClass == null) {
            return "";
        }
        String str="";
        IntegerValuedEnum defaultEnumObject=valueOf(enumClass, defaultIndex);
        for (IntegerValuedEnum theEnum : enumClass.getEnumConstants()) {
            if (defaultEnumObject != null && theEnum.getIndex() == defaultEnumObject.getIndex()) {
                str=theEnum.getName();
            }
        }
        return str;
    }

    public static String formatDecimal(BigDecimal decimal){
        if(decimal==null){
            return "0";
        }
        DecimalFormat decimalFormat=new DecimalFormat("###.#########");
        return decimalFormat.format(decimal);
    }
    public static BigDecimal formatDecimalReturnBig(BigDecimal decimal){
        if(decimal==null){
            decimal=BigDecimal.ZERO;
        }
        DecimalFormat decimalFormat=new DecimalFormat("###.#########");
        return new BigDecimal(decimalFormat.format(decimal));
    }


}
