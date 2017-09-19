package com.hdong.upms.dao.model;

import java.io.Serializable;

public class SysDict implements Serializable {
    /**
     * 应用名
     *
     * @mbg.generated
     */
    private String dictApp;

    /**
     * 类型,对应枚举类名
     *
     * @mbg.generated
     */
    private String dictType;

    /**
     * 值，对应枚举val
     *
     * @mbg.generated
     */
    private String dictVal;

    /**
     * 对应枚举名字
     *
     * @mbg.generated
     */
    private String dictName;

    /**
     * 描述，对应枚举的desc
     *
     * @mbg.generated
     */
    private String dictDesc;

    private static final long serialVersionUID = 1L;

    public String getDictApp() {
        return dictApp;
    }

    public void setDictApp(String dictApp) {
        this.dictApp = dictApp;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getDictVal() {
        return dictVal;
    }

    public void setDictVal(String dictVal) {
        this.dictVal = dictVal;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictDesc() {
        return dictDesc;
    }

    public void setDictDesc(String dictDesc) {
        this.dictDesc = dictDesc;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", dictApp=").append(dictApp);
        sb.append(", dictType=").append(dictType);
        sb.append(", dictVal=").append(dictVal);
        sb.append(", dictName=").append(dictName);
        sb.append(", dictDesc=").append(dictDesc);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SysDict other = (SysDict) that;
        return (this.getDictApp() == null ? other.getDictApp() == null : this.getDictApp().equals(other.getDictApp()))
            && (this.getDictType() == null ? other.getDictType() == null : this.getDictType().equals(other.getDictType()))
            && (this.getDictVal() == null ? other.getDictVal() == null : this.getDictVal().equals(other.getDictVal()))
            && (this.getDictName() == null ? other.getDictName() == null : this.getDictName().equals(other.getDictName()))
            && (this.getDictDesc() == null ? other.getDictDesc() == null : this.getDictDesc().equals(other.getDictDesc()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDictApp() == null) ? 0 : getDictApp().hashCode());
        result = prime * result + ((getDictType() == null) ? 0 : getDictType().hashCode());
        result = prime * result + ((getDictVal() == null) ? 0 : getDictVal().hashCode());
        result = prime * result + ((getDictName() == null) ? 0 : getDictName().hashCode());
        result = prime * result + ((getDictDesc() == null) ? 0 : getDictDesc().hashCode());
        return result;
    }
}