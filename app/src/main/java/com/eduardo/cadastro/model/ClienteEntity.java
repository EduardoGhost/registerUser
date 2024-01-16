package com.eduardo.cadastro.model;

import java.io.Serializable;
import java.util.Calendar;

public class ClienteEntity implements Serializable {

    private Long codeId;
    private String name;
    private String userName;
    private String password;
    private Long date;
    private String sex;
    private String cpfOrCnpj;
    private String email;
    private String adress;
    private String picture;

    public ClienteEntity() {
    }

    public ClienteEntity(Long codeId, String name, String userName, String password, Long date, String sex, String cpfOrCnpj, String email, String adress, String picture) {
        this.codeId = codeId;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.date = date;
        this.sex = sex;
        this.cpfOrCnpj = cpfOrCnpj;
        this.email = email;
        this.adress = adress;
        this.picture = picture;
    }

    public Long getCodeId() {
        return codeId;
    }

    public void setCodeId(Long codeId) {
        this.codeId = codeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCpfOrCnpj() {
        return cpfOrCnpj;
    }

    public void setCpfOrCnpj(String cpfOrCnpj) {
        this.cpfOrCnpj = cpfOrCnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public int calculateAge() {
        if (date != null) {
            Calendar dob = Calendar.getInstance();
            dob.setTimeInMillis(date);

            Calendar today = Calendar.getInstance();

            int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

            if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }

            return age;
        }

        return 0; // Se a data de nascimento não estiver definida, retorna 0.
    }

    @Override
    public String toString() {
        return "ModelCliente{" +
                "codigo=" + codeId +
                ", nome='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", senha='" + password + '\'' +
                ", sexo='" + sex + '\'' +
                ", cpfOuCnpj='" + cpfOrCnpj + '\'' +
                ", email='" + email + '\'' +
                ", endereço='" + adress + '\'' +
                ", foto='" + picture + '\'' +
                '}';
    }
}
