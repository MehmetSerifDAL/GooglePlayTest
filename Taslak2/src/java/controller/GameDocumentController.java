/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.GameDocumentdao;
import entity.GameDocument;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

//birçoğu deneme amaçlı eklenmiştir.
@Named
@SessionScoped
/**
 *
 * @author Cyber Micro
 */
public class GameDocumentController extends PaginitonController implements Serializable,Validator{
   private GameDocument document;
    private GameDocumentdao docDao;
    private List<GameDocument> docList;
    private Part part;
    // bu yolu degistiriniz uploadlar calismasi icin 
    private final String uploadTo ="C:/Users/CyberMicro/OneDrive/Masaüstü/taslak2/uploads/";
     private int sayfaSize = 8;
    private int sayfaAdet = 3;
    private int sonuc;
           private String eror_mesage = null;
    @Inject
    private GameController gameController;

     public void upload(){
        try {
            if(this.getPart() != null){
            InputStream input = this.getPart().getInputStream();
            File file = new File(uploadTo+part.getSubmittedFileName());             
            Files.copy(input,file.toPath(),StandardCopyOption.REPLACE_EXISTING);
            SimpleDateFormat uploadTime = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss");
                String d = uploadTime.format(new Date());
                String newName = d + "&&" + file.getName();
                File newFile = new File(uploadTo + newName);
                file.renameTo(newFile);
            this.document = this.getDocment();            
            document.setFileName(newName);
            document.setFile_size(getPart().getSize());
            document.setFilePath(file.getParent());
            document.setFileType(part.getContentType());            
            sonuc =  this.getDocDao().insert(document); 
            this.document = new GameDocument();
            this.game_reset();
            }
        } catch (IOException ex) {
            
        }
    }
    public boolean size(GameDocument doc){
        if(doc.getFile_size() < 3000000)
            return true;
        else
            return false;
    }
    public void game_reset(){
        this.setPart(null);
        this.setEror_mesage(null);
    }
    public void updatePath(){       
        try {               
            if(this.getPart() != null){
            InputStream input = this.part.getInputStream();
            File file = new File(uploadTo+this.part.getSubmittedFileName());             
            Files.copy(input,file.toPath(),StandardCopyOption.REPLACE_EXISTING);
            SimpleDateFormat uploadTime = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss");
                String d = uploadTime.format(new Date());
                String newName = d + "&&" + file.getName();
                File newFile = new File(uploadTo + newName);
                file.renameTo(newFile);
            document.setFileName(newName);
            document.setFile_size(getPart().getSize());
            document.setFilePath(file.getParent());
            document.setFileType(this.part.getContentType());
            sonuc =  this.getDocDao().update(document);
            this.document = new GameDocument();  
            this.game_reset();
            }
        } catch (IOException ex) {
            
        }
    }
    public void back(){
        this.document = new GameDocument();
        this.sonuc = -1 ;
         this.game_reset();
    }
    public void editForm(GameDocument sarkiDoc){
        this.document = sarkiDoc;
        this.game_reset();
        this.sonuc = -1;
    }
    public void update(){
        if(this.getEror_mesage() == null){
            if(this.getPart() != null)
                this.updatePath();
            else{
        gameController.updateForm(this.getDocment().getGame());
       gameController.update();       
       this.sonuc = gameController.getSonuc();
       this.document = new GameDocument(); 
        this.game_reset();
            }
        }
    }
    public void delete(){
        this.sonuc = this.getDocDao().delete(this.getDocment());
        this.document = new GameDocument(); 
        this.game_reset();
        
    }
     public List<GameDocument> getDocList() {
         this.docList = this.getDocDao().findAll(getPage(),getSayfa_Size(),getSearchTerimi(),getId());
        return docList;
    }

    public GameDocument getDocment() {
        if(this.document == null)
            this.document = new GameDocument();
        return document;
    }


    public GameDocumentdao getDocDao() {
        if(this.docDao == null)
            this.docDao = new GameDocumentdao();
        return docDao;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public Part getPart() {
        return part;
    }

    public void setEror_mesage(String eror_mesage) {
        this.eror_mesage = eror_mesage;
    }

    public String getEror_mesage() {
        return eror_mesage;
    }
    
    public  String getUploadTo() {
        return uploadTo;
    }

    @Override
    public void validate(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {            
        Part p = (Part)arg2;
        if(p == null)
            this.setEror_mesage("Lutfen bir dosya seciniz..");
            //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"lutfen bir dosya seciniz","lutfen bir dosya seciniz"));       
        else if(p.getSize() > 20971520 ){
            this.setEror_mesage("max boyut 20 MB");
          //  throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"max boyut 20 MB","max boyut 20 MB"));
        
        }else if(!"audio/mp3".equals(p.getContentType()) && !"/apk".equals(p.getContentType())&& !"tar".equals(p.getContentType()) ){
            this.setEror_mesage("gecersiz bir dosya uzantisi yuklediniz");
          //  throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"gecersiz bir dosya uzantisi yuklediniz","gecersiz bir dosya uzantisi yuklediniz"));      
        }
    }

    public GameController getGamecontrol() {
        return gameController;
    }

    public void setSonuc(int sonuc) {
        this.sonuc = sonuc;
    }

    public int getSonuc() {
        return sonuc;
    }


    
    
    
    
}
