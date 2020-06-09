/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Date;

/**
 *
 * @author Cyber Micro
 */
public class PrintDocument {
     private Long doc_id;
    private String fileName;
    private String filePath;
    private String fileType;
    private Date last_update;

    public PrintDocument() {
    }

    public PrintDocument(Long doc_id, String fileName, String filePath, String fileType, Date last_update) {
        this.doc_id = doc_id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.last_update = last_update;
    }

    public Long getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(Long doc_id) {
        this.doc_id = doc_id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Date getLast_update() {
        return last_update;
    }

    public void setLast_update(Date last_update) {
        this.last_update = last_update;
    }
    
}
