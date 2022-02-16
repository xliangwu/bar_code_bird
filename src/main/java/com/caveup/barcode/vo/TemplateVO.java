package com.caveup.barcode.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author xw80329
 * @since 2022-01-09
 */
@Data
public class TemplateVO {
    private Integer id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;
}
