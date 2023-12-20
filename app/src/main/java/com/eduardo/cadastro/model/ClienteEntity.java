package com.eduardo.cadastro.model;

import java.io.Serializable;

public class ClienteEntity implements Serializable {

    private Long id;
    private String name;
    private String password;
    private String date;
    private String sex;
    private String cpfOrCnpj;
    private String email;
    private String adress;
    private String picture;

    public ClienteEntity() {
    }

    public ClienteEntity(Long id, String name, String password, String date, String sex, String cpfOrCnpj, String email, String adress, String picture) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.date = date;
        this.sex = sex;
        this.cpfOrCnpj = cpfOrCnpj;
        this.email = email;
        this.adress = adress;
        this.picture = picture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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


    @Override
    public String toString() {
        return "ModelCliente{" +
                "codigo=" + id +
                ", nome='" + name + '\'' +
                ", senha='" + password + '\'' +
                ", sexo='" + sex + '\'' +
                ", cpfOuCnpj='" + cpfOrCnpj + '\'' +
                ", email='" + email + '\'' +
                ", endereço='" + adress + '\'' +
                ", foto='" + picture + '\'' +
                '}';
    }
}
