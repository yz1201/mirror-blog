package cn.dbdj1201.user.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: dbdj1201
 * @Date: 2020-08-04 11:33
 */
@Data
public class LoginDto {

    @NotBlank(message = "昵称不可以为空")
    private String username;

    @NotBlank(message = "密码不可以为空")
    private String password;
}
