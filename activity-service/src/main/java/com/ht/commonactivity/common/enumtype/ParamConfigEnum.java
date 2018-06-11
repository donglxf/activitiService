package com.ht.commonactivity.common.enumtype;

public enum ParamConfigEnum {
    top("000", "根节点");

    private String val;
    private String mark;

    ParamConfigEnum(String i, String mark) {
        this.val = i;
        this.mark = mark;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
