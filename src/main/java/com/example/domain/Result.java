package com.example.domain;

import lombok.Data;

@Data
public class Result {
    private Integer status;//请求状态
    private Object data;//数据
    private String msg;//信息

    public Result() {
    }

    public Result(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Result(Integer status, Object data) {
        this.status = status;
        this.data = data;
    }

    public Result(Integer status, Object data, String msg) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }
}
