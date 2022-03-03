package com.caveup.barcode.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author xw80329
 */
@Data
public class PrintVO {

    private String templateContent;
    private String specification;
    private String machineCode;

    @JsonFormat(pattern = "yyyyMMdd", timezone = "GMT+8")
    private Date selectedDate;
    private Integer startIndex;
    private Integer printCount;
    private String capacity;
    private String sapCode;
    private String productCode;
    private String productName;
    private String printer;
    private Integer printType;
}
