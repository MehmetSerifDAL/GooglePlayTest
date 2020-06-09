/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.PrintDocumentDao;
import entity.User;
import entity.PrintDocument;
import entity.GameDev;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

@Named
@SessionScoped
/**
 *
 * @author Cyber Micro
 */
public class PrintDocumentController implements Serializable, Validator {

    private PrintDocument docment;
    private PrintDocumentDao resimdDocDao;
    private List<PrintDocument> docList;
    private Part part;
    // bu yolu degistiriniz uploadlar calismasi icin 
    private final String uploadTo = "C:/Users/CyberMicro/OneDrive/Masaüstü/GooglePlay/photos/";
    private String eror_mesage;
    private int sonuc;

    @Inject
    private NewSignController uye;

    public PrintDocumentController() {

    }

    public void resim_reset() {
        this.setEror_mesage(null);
        this.setPart(null);
    }

    public void upload(Long id) {
        try {
            if (this.getPart() != null) {
                InputStream input = getPart().getInputStream();
                File file = new File(uploadTo + getPart().getSubmittedFileName());
                Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                SimpleDateFormat uploadTime = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss");
                String d = uploadTime.format(new Date());
                String newName = d + "&&" + file.getName();
                File newFile = new File(uploadTo + newName);
                file.renameTo(newFile);
                this.docment = this.getDocment();
                docment.setDoc_id(id);
                docment.setFileName(newName);
                docment.setFilePath(file.getParent());
                docment.setFileType(getPart().getContentType());
                sonuc = this.getResimdDocDao().insert(docment);
                this.setEror_mesage(null);
            }
        } catch (IOException ex) {

        }
    }

    public void updatePath(Object o) {
        try {
            if (this.getPart() != null) {
                InputStream input = this.getPart().getInputStream();
                File file = new File(uploadTo + this.getPart().getSubmittedFileName());
                Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                SimpleDateFormat uploadTime = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss");
                String d = uploadTime.format(new Date());
                String newName = d + "&&" + file.getName();
                File newFile = new File(uploadTo + newName);
                file.renameTo(newFile);

                if (o instanceof User) {
                    docment = ((User) o).getYetki().getResim();
                } else if (o instanceof GameDev) {
                    docment = ((User) o).getYetki().getResim();//burası hatalı olabilir tam olarak çözemedim ide hatalı olabilir.
                }
                docment.setFileName(newName);
                docment.setFilePath(file.getParent());
                docment.setFileType(this.getPart().getContentType());
                sonuc = this.getResimdDocDao().update(docment);
                this.setEror_mesage(null);
            }
        } catch (IOException ex) {

        }
    }

    public void delete() {
        this.setPart(null);
        if (this.uye.getUpdateToUser().getYetki().getResim().getDoc_id() != null) {
            this.getResimdDocDao().delete(this.uye.getUpdateToUser().getYetki().getResim().getDoc_id());
        }
        this.uye.getUpdateToUser().getYetki().setResim(null);
        this.setEror_mesage(null);

    }

    @Override
    public void validate(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
        Part p = (Part) arg2;
        if (p == null) {
            this.setEror_mesage("lutfen bir dosya seciniz");
        } else if (p.getSize() > 3145728) {
            this.setEror_mesage("max boyut 3 MB");
        } else if (!"image/jpeg".equals(p.getContentType()) && !"image/png".equals(p.getContentType())) {
            this.setEror_mesage("dosya turu sadece jpeg olacak");
        }
    }

    public PrintDocument getDocment() {
        if (this.docment == null) {
            this.docment = new PrintDocument();
        }
        return docment;
    }

    public PrintDocumentDao getResimdDocDao() {
        if (this.resimdDocDao == null) {
            this.resimdDocDao = new PrintDocumentDao();
        }
        return resimdDocDao;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public Part getPart() {
        return part;
    }

    public String getUploadTo() {
        return uploadTo;
    }

    public void setEror_mesage(String eror_mesage) {
        this.eror_mesage = eror_mesage;
    }

    public String getEror_mesage() {
        return eror_mesage;//Error mesajı alma olasaıgımıza karşı eklenmıstır.
    }

    public void setSonuc(int sonuc) {
        this.sonuc = sonuc;
    }

    public int getSonuc() {
        return sonuc;
    }
}
