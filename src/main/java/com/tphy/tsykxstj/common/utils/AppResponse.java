package com.tphy.tsykxstj.common.utils;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * @author chuchen
 */
@Data
public class AppResponse<T> {

    private Integer code;

    private HttpStatus status;

    private String msg;

    private T data;

    private String date = LocalDateTime.now().toString();

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public AppResponse() {
    }

    public AppResponse(Integer code, String msg, T data) {
        this.code = code;
        this.status = HttpStatus.OK;
        this.msg = msg;
        this.data = data;
    }

    public AppResponse<T> success(){
        AppResponse<T> response = new AppResponse<>();
        response.setCode(ResultCode.SUCCESS);
        response.setStatus(HttpStatus.OK);
        response.setMsg("success");
        return response;
    }

    public AppResponse<T> success(String msg){
        AppResponse<T> response = new AppResponse<T>();
        response.setCode(ResultCode.SUCCESS);
        response.setStatus(HttpStatus.OK);
        response.setMsg(msg);
        response.setData(null);
        return response;
    }

    public AppResponse<T> success(T data){
        AppResponse<T> response = new AppResponse<T>();
        response.setCode(ResultCode.SUCCESS);
        response.setStatus(HttpStatus.OK);
        response.setMsg("success");
        response.setData(data);
        return response;
    }

    public AppResponse<T> success(String msg, T data){
        AppResponse<T> response = new AppResponse<T>();
        response.setCode(ResultCode.SUCCESS);
        response.setStatus(HttpStatus.OK);
        response.setMsg(msg);
        response.setData(data);
        return response;
    }

    public AppResponse<T> error(){
        AppResponse<T> response = new AppResponse<T>();
        response.setCode(ResultCode.ERROR);
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setMsg("error");
        response.setData(null);
        return response;
    }

    public AppResponse<T> error(String msg){
        AppResponse<T> response = new AppResponse<T>();
        response.setCode(ResultCode.ERROR);
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setMsg(msg);;
        response.setData(null);
        return response;
    }

    public AppResponse<T> empty(T data){
        AppResponse<T> response = new AppResponse<T>();
        response.setCode(ResultCode.NO_DATA);
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setMsg("No Data");
        response.setData(data);
        return response;
    }

    public AppResponse<T> error(T data){
        AppResponse<T> response = new AppResponse<T>();
        response.setCode(ResultCode.ERROR);
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setMsg("error");
        response.setData(data);
        return response;
    }

    public AppResponse<T> error(String msg, T data){
        AppResponse<T> response = new AppResponse<T>();
        response.setCode(ResultCode.ERROR);
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setMsg(msg);
        response.setData(data);
        return response;
    }
}
