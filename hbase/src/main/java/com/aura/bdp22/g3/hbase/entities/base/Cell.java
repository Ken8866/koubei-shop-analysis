package com.aura.bdp22.g3.hbase.entities.base;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import java.io.Serializable;
import java.util.Map;

public class Cell implements Serializable {

    private String qualifier ;
    private String value ;
    private Long version ;

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
