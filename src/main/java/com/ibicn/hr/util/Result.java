package com.ibicn.hr.util;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 王立方
 * @Description //公共返回实体，code:返回状态，msg:返回信息，data:返回数据,description：描述。如有其它信息，可调用的put方法进行设置
 * @Date 9:52 2019/4/15
 * @return
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {
    private Integer code = StatusCode.SUCCESS_CODE;
    private String msg = StatusCode.SUCCESS_MSG;
    private String description = "";
    private T data;
    private Map<String, Object> otherData = new HashMap<>();

    public Object put(String key, Object data) {
        return otherData.put(key, data);
    }

    public Object get(String key) {
        return otherData.get(key);
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Result setCode(Integer code) {
        if (code != null) {
            this.code = code;
        }
        return this;
    }

    public Result setMsg(String msg) {
        if (msg != null) {
            this.msg = msg;
        }
        return this;
    }

    public T getData() {
        return this.data;
    }

    public Result setData(T data) {
        if (data != null) {
            this.data = data;
        }
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public Result setDescription(String description) {
        if (description != null) {
            this.description = description;
        }
        return this;
    }

    /**
     * @return com.ibicn.glwes.commons.VO.Result
     * @Author 王立方
     * @Description //默认错误返回
     * @Date 9:59 2019/4/15
     * @Param
     **/
    public static Result failure() {
        return getFailure();
    }

    /**
     * @return com.ibicn.glwes.commons.VO.Result
     * @Author 王立方
     * @Description //自定义错误返回信息
     * @Date 9:59 2019/4/15
     * @Param [msg]
     **/
    public static Result failure(String msg) {
        return getFailure().setMsg(msg);
    }

    /**
     * @return com.ibicn.glwes.commons.VO.Result
     * @Author 王立方
     * @Description //自定义错误返回码和错误返回信息
     * @Date 9:59 2019/4/15
     * @Param [code, msg]
     **/
    public static Result failure(int code, String msg) {
        return getFailure().setCode(code).setMsg(msg);
    }

    /**
     * @return com.ibicn.glwes.commons.VO.Result
     * @Author 王立方
     * @Description //自定义错误返回码,错误返回信息和描述
     * @Date 9:59 2019/4/15
     * @Param [code, msg]
     **/
    public static Result failure(int code, String msg, String description) {
        return getFailure().setCode(code).setMsg(msg).setDescription(description);
    }

    /**
     * @return com.ibicn.glwes.commons.VO.Result
     * @Author 王立方
     * @Description //默认成功返回
     * @Date 10:01 2019/4/15
     * @Param []
     **/
    public static Result ok() {
        return getOk(Object.class);
    }

    /**
     * @return com.ibicn.glwes.commons.VO.Result
     * @Author 王立方
     * @Description //自定义返回成功数据
     * @Date 10:01 2019/4/15
     * @Param [data]
     **/
    public static <T> Result<T> ok(T data) {
        return getOk(data).setData(data);
    }

    /**
     * @return com.ibicn.glwes.commons.VO.Result
     * @Author 王立方
     * @Description //自定义返回成功信息和数据
     * @Date 10:06 2019/4/15
     * @Param [msg, data]
     **/
    public static <T> Result<T> ok(String msg, T data) {
        return getOk(data).setMsg(msg).setData(data);
    }

    private static <T> Result<T> getOk(T data) {
        Result<T> result = new Result();
        result.setData(data);
        return result;
    }

    private static Result getFailure() {
        Result<Object> result = new Result();
        result.setCode(StatusCode.FAILURE_CODE);
        result.setMsg(StatusCode.FAILURE_MSG);
        return result;
    }
}
