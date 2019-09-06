package com.aura.bdp22.g3.hbase.entities.base;

import java.io.Serializable;
import java.util.List;

public class Entity implements Serializable {

    private String name ;
    private String rowkey ;
    private List<Family> families ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRowkey() {
        return rowkey;
    }

    public void setRowkey(String rowkey) {
        this.rowkey = rowkey;
    }

    public List<Family> getFamilies() {
        return families;
    }

    public void setFamilies(List<Family> families) {
        this.families = families;
    }

}
