package cn.dbdj1201.shiro.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author yz1201
 * @date 2020-08-07 23:25
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> implements Serializable {
    //响应状态码
    private Integer code;
    //回执信息
    private String message;
    //实体数据
    private T data;


}
