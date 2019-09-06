package com.aura.bdp22.g3.hbase.entities.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Family implements Serializable {

    private String name ;
    private List<Cell> cells ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }
}
