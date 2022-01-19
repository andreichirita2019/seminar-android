package eu.ase.chirita_andrei.proiect.zocdocclone.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import eu.ase.chirita_andrei.proiect.zocdocclone.util.GenderType;

//o inregistrare din users va fi un obiect de tip User
public class  User implements Serializable {

    private String idFirebase;
    private String email;
    private String confirmEmail;
    private String password;
    private String name;
    private String dataBirth;
    private String genderType;

    public User(){

    }

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public User(String email, String confirmEmail, String password, String name, String dataBirth, String genderType) {
        this.email = email;
        this.confirmEmail = confirmEmail;
        this.password = password;
        this.name = name;
        this.dataBirth = dataBirth;
        this.genderType = genderType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmEmail() {
        return confirmEmail;
    }

    public void setConfirmEmail(String confirmEmail) {
        this.confirmEmail = confirmEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataBirth() {
        return dataBirth;
    }

    public void setDataBirth(String dataBirth) {
        this.dataBirth = dataBirth;
    }

    public String getGenderType() {
        return genderType;
    }

    public void setGenderType(String genderType) {
        this.genderType = genderType;
    }

    public String getIdFirebase() {
        return idFirebase;
    }

    public void setIdFirebase(String idFirebase) {
        this.idFirebase = idFirebase;
    }

}
