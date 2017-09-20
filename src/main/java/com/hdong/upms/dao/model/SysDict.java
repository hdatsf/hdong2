package com.hdong.upms.dao.model;

import java.io.Serializable;

public class SysDict implements Serializable {
    /**
     * 应用名
     *
     * @mbg.generated
     */
    private String app;

    /**
     * 类型,对应枚举类名
     *
     * @mbg.generated
     */
    private String type;

    /**
     * 值，对应枚举val
     *
     * @mbg.generated
     */
    private String val;

    /**
     * 描述
     *
     * @mbg.generated
     */
    private String name;

    /**
     * 描述，对应枚举的desc
     *
     * @mbg.generated
     */
    private String desc;

    private static final long serialVersionUID = 1L;

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", app=").append(app);
        sb.append(", type=").append(type);
        sb.append(", val=").append(val);
        sb.append(", name=").append(name);
        sb.append(", desc=").append(desc);
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
        return (this.getApp() == null ? other.getApp() == null : this.getApp().equals(other.getApp()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getVal() == null ? other.getVal() == null : this.getVal().equals(other.getVal()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getDesc() == null ? other.getDesc() == null : this.getDesc().equals(other.getDesc()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getApp() == null) ? 0 : getApp().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getVal() == null) ? 0 : getVal().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getDesc() == null) ? 0 : getDesc().hashCode());
        return result;
    }
}