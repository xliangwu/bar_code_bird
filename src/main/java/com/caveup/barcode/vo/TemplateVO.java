package com.caveup.barcode.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotBlank
    private String type;

    @NotNull
    private String p1CodeFontSize;

    @NotNull
    private String p2CodeFontSize;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;
}
