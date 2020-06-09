
 package controller;


import dao.UserDao;
import entity.User;
import entity.Game;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.validator.Validator;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

@Named
@SessionScoped
public class NewSignController extends PaginitonController implements Serializable{

    private User kullanici;
    private User updateToUser;   // tmp nesne kullanici tarafinda profile bilgilerini guncellenemek icin.
    private List<User> UserList;
    private UserDao kullaniciDao;
    private int sonuc;  // database tarafindaan bize return olacak sonuc
    private int i,j,k;     // giris yapan bilgilerini sadece ilk seferde kullanmak için geçici değişkenlerdir
    private boolean edit;  // profile sayfasinda edit button aktiflesitirme kontrolu
    private String tmpUser;
    private String tmpPass;
    
    @Inject
    private UserControllers girisYapan; // giris yapan kullanicinin bilgilerine erişmek için
    @Inject 
    private PrintDocumentController kullanici_resmi;

    public NewSignController(){   
        
        setDao(this.getUserDao());
        setSayfa_Adet(2);
        setSayfa_Size(6);    
        this.setYetki_saviye(0);
        this.sonuc = -1;
            i=1;
            j=1;
            k = 1;
    }
    public List<User> getUserList() {
        this.UserList = this.getUserDao().findAll(getPage(),getSayfa_Size(),0,getSearchTerimi());        
        return UserList;
    }

    public void updateForm(User kullanici) {     
        this.kullanici = kullanici;
        this.sonuc = -1;
    }

     public void updateForm2() {// kullanici tarafinda profile bilgilerini guncellemek icin
        this.sonuc = -1;
        this.edit = true;
    }
     
    public void clearForm() {
        this.kullanici = new User();
        this.sonuc = -1;
    }

    public void create() {
        if(this.kullanici_resmi.getEror_mesage() == null){
          this.sonuc = this.getUserDao().create(this.getUsert());
          this.kullanici_resmi.upload(this.getUserDao().getUser_bilgi_id());     
          this.getGirisYapan().getUser().getYetki().setResim(this.kullanici_resmi.getDocment());
          //this.getUpdateToUser().getYetki().setResim(this.kullanici_resmi.getDocment());  // null pointer exp dusuyor
          if(this.sonuc == 1)
          this.kullanici = new User();  
          this.kullanici_resmi.setEror_mesage(null);
        }        
    }
    public void update() { // admin tarafinda olan kullanici icinde olan uyelerin guncellemesi icin        
        this.sonuc = this.getUserDao().update(this.getUsert());
        this.kullanici = new User();
    }
    
    public void update2() {
        if(this.kullanici_resmi.getEror_mesage() == null){            
        this.sonuc = this.getUserDao().update(this.getUpdateToUser());
        // sonuc = 1 ise islem dogru ve update işlemi yapilsin hataliysa listedeki bilgiler etkilenmesin        
            if(sonuc==1){        
            this.updateToUser = this.getUserDao().find(this.getUpdateToUser().getUsername(), this.getUpdateToUser().getPassword());
            this.setTmpUser(this.updateToUser.getUsername());
            this.setTmpPass(this.updateToUser.getPassword());  
            if(this.getUpdateToUser().getYetki().getResim().getDoc_id() == null){
                this.getUser_resmi().upload(this.getUpdateToUser().getYetki().getId());
                this.getUpdateToUser().getYetki().setResim(this.kullanici_resmi.getDocment());
                this.updateToUser = this.getUserDao().find(this.getUpdateToUser().getUsername(), this.getUpdateToUser().getPassword());
            }
            else
            this.getUser_resmi().updatePath(this.getUpdateToUser()); 
            this.kullanici_resmi.setEror_mesage(null);
            this.setEdit(false);
           }
        }        
    }
    public void delete() {
        this.sonuc = this.getUserDao().delete(this.getUsert());
        this.kullanici = new User();
    }  
    public User getUpdateToUser() {
        // ilk sefer giris yapan kullanicin bilgilerini gostereceğiz ama sonra 
        //password bilgisi guncellenebildiğinden dolayi profile bilgilerin getUser() ile çağiramayiz
        //o yüzden updatetoUserda olan bilgiler ile yeni bir sorgulama yapip return yapacağız
        // bu sorgulama update2()'de yeniden UserDao'da find() metod ile yapacağız
        if(i == 1 ){
       this.updateToUser = this.getGirisYapan().getUser(); //**
        i=0;
        }        
        return updateToUser;
    }
    public String getTmpUser() {
        if(j == 1){
            j = 0;
            this.tmpUser = this.getUpdateToUser().getUsername();
        }
        return tmpUser;
    }
    public String getTmpPass() {
        if(k == 1){
            k = 0;
            this.tmpPass = this.getUpdateToUser().getPassword();
        }
        return tmpPass;
    }
    public void setUpdateToUser(User updateToUser) {
        this.updateToUser = updateToUser;
    }
    public void setTmpPass(String tmpPass) {
        this.tmpPass = tmpPass;
    }
    public void setTmpUser(String tmpUser) {
        this.tmpUser = tmpUser;
    }
    public int getSonuc() {
        return sonuc;
    }
    public void setSonuc(int sonuc) {
        this.sonuc = sonuc;
    }
    public boolean isEdit() {        
        return edit;
    }
    public void setEdit(boolean edit) {
        this.kullanici_resmi.setPart(null);
        this.kullanici_resmi.setEror_mesage(null);
        this.edit = edit;
    }

    public UserControllers getGirisYapan() {
        return girisYapan;
    }
     public User getUsert() {
        if (this.kullanici == null) {
            this.kullanici = new User();
        }
        return kullanici;
    }
    public UserDao getUserDao() {
        if (this.kullaniciDao == null) {
            this.kullaniciDao = new UserDao();
        }
        return kullaniciDao;
    }
    public void setUserList(List<User> UserList) {
        this.UserList = UserList;
    }

    public PrintDocumentController getUser_resmi() {
        return kullanici_resmi;
    }    
}
 