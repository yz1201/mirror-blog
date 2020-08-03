package cn.dbdj1201.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author yz1201
 * @date 2020-08-03 22:12
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> implements Serializable {
    //状态码
    private Integer statusCode;
    //返回信息
    private String message;
    //实体数据
    private T t;

    public CommonResult(Integer statusCode, String message) {
        this(statusCode, message, null);
    }
    
    

}
