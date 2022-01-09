package com.caveup.barcode.entity;

import java.io.Serializable;

/**
 * flag for domain entity
 *
 * @author xw80329
 */
public abstract class Entity<T> implements Serializable {

    /**
     * primary key
     *
     * @return
     */
    public abstract T getPrimaryKey();
}