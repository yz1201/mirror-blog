package cn.dbdj1201.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @Author: dbdj1201
 * @Date: 2020-08-04 8:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class CommonResult<T> implements Serializable {

    //状态码
    private int statusCode;
    //回执消息
    private String message;
    //实体数据
    private T data;

    public CommonResult(int statusCode, String message) {
        this(statusCode, message, null);
    }

    public CommonResult(String message) {
        this.message = message;
    }

    public CommonResult(T data) {
        this.data = data;
    }
}
