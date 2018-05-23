package com.ht.commonactivity.common.enumtype;

public enum ActivitiSignEnum {
    equle("1", "等于"), notequle("2", "不等于"), great("3", "大于"), greatEq("4", "大于等于"), less("5", "小于"), lessEq("6", "小于等于"), like("7", "模糊匹配");

    private String val;
    private String mark;

    ActivitiSignEnum(String i, String mark) {
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
