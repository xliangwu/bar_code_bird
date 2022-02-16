package com.caveup.barcode.vo;

import com.caveup.barcode.model.CustomerStorageEntity;
import com.caveup.barcode.model.SysMachineEntity;
import com.caveup.barcode.model.TemplateEntity;
import lombok.Data;

import java.util.List;

/**
 * @author xw80329
 */
@Data
public class MetaData {

    private List<SysMachineEntity> machines;
    private List<CustomerStorageEntity> commodities;
    private List<TemplateEntity> templates;
}
