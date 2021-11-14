package eu.ase.chirita_andrei.proiect.zocdocclone.models;

import java.io.Serializable;
import java.util.Date;

import eu.ase.chirita_andrei.proiect.zocdocclone.util.GenderType;

public class User implements Serializable {

    private String email;
    private String confirmEmail;
    private String password;
    private String name;
    private Date dataBirth;
    private GenderType genderType;

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public User(String email, String confirmEmail, String password, String name, Date dataBirth, GenderType genderType) {
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

    public Date getDataBirth() {
        return dataBirth;
    }

    public void setDataBirth(Date dataBirth) {
        this.dataBirth = dataBirth;
    }

    public GenderType getGenderType() {
        return genderType;
    }

    public void setGenderType(GenderType genderType) {
        this.genderType = genderType;
    }
}
