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
public class GameDocument {
    private Long doc_id;
    private String fileName;
    private Long file_size;
    private String filePath;
    private String fileType;
    private Game game;

    public GameDocument() {
    }

    public GameDocument(Long doc_id, String fileName, Long file_size, String filePath, String fileType, Game game) {
        this.doc_id = doc_id;
        this.fileName = fileName;
        this.file_size = file_size;
        this.filePath = filePath;
        this.fileType = fileType;
        this.game = game;
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

    public Long getFile_size() {
        return file_size;
    }

    public void setFile_size(Long file_size) {
        this.file_size = file_size;
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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
    

   
    
    
}
