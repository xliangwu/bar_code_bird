package com.caveup.barcode.vo;

import lombok.Data;

import java.util.List;

/**
 * @author xw80329
 */
@Data
public class TreeNode {
    private String label;
    private String value;
    private List<TreeNode> children;
}
