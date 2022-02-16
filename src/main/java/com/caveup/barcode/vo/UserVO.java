package com.caveup.barcode.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author xw80329
 */
@Data
public class UserVO {

    @NotNull
    private String username;

    @NotNull
    private String password;
}
