package com.example.demo.common;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResponseData<T> {

    protected String status;

    protected String msg;

    protected T data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public ResponseData data(T data){
        this.data = data;
        return this;
    }

    public ResponseData status(String status){
        this.status = status;
        return this;
    }

    public ResponseData message(String messge){
        this.msg = messge;
        return this;
    }

    public ResponseData<T> toResponseData(T data) {
        final String STATUS_SUCCESS = "200";
        final String MESSAGE_SUCCESS = "Success";
        ResponseData<T> bean = new ResponseData<>();
        bean.setStatus(STATUS_SUCCESS);
        bean.setMsg(MESSAGE_SUCCESS);
        bean.setData(data);
        return bean;
    }

    public static ResponseData success() {
        final String STATUS_SUCCESS = "200";
        final String MESSAGE_SUCCESS = "Success";
        ResponseData bean = new ResponseData();
        bean.setStatus(STATUS_SUCCESS);
        bean.setMsg(MESSAGE_SUCCESS);
        return bean;
    }

    public void ok() {
        final String STATUS_SUCCESS = "200";
        final String MESSAGE_SUCCESS = "Success";
        setStatus(STATUS_SUCCESS);
        setMsg(MESSAGE_SUCCESS);
    }
}
