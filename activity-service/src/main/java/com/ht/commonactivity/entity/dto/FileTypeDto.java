package com.ht.commonactivity.entity.dto;

import java.io.Serializable;

public class FileTypeDto implements Serializable {

    public FileTypeDto() {
        super();
    }

    public FileTypeDto(String fileTypeCode, String name, String pFileTypeCode, String fileTypePath) {
        super();
        this.fileTypeCode = fileTypeCode;
        this.name = name;
        this.pFileTypeCode = pFileTypeCode;
        this.fileTypePath = fileTypePath;
    }


    private String fileTypeCode;
    private String name;
    private String pFileTypeCode;
    private String fileTypePath;

    public String getFileTypePath() {
        return fileTypePath;
    }

    public void setFileTypePath(String fileTypePath) {
        this.fileTypePath = fileTypePath;
    }

    public String getFileTypeCode() {
        return fileTypeCode;
    }

    public void setFileTypeCode(String fileTypeCode) {
        this.fileTypeCode = fileTypeCode;
    }


    public String getpFileTypeCode() {
        return pFileTypeCode;
    }

    public void setpFileTypeCode(String pFileTypeCode) {
        this.pFileTypeCode = pFileTypeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
