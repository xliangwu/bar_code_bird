package com.caveup.barcode.entity;

import com.alibaba.excel.util.StringUtils;
import com.caveup.barcode.constants.InterpolateType;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

/**
 * @author xw80329
 */
@Data
public class InterpolateEntity {

    public static InterpolateEntity TEXT = new InterpolateEntity();
    private InterpolateType type;
    private String format;
    private List<String> paramKeys;

    public InterpolateEntity() {
        this.type = InterpolateType.TEXT_CODE;
        this.format = StringUtils.EMPTY;
        this.paramKeys = Lists.newArrayList();
    }
}
