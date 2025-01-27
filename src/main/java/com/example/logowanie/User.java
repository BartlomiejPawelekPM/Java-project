package com.example.logowanie;

import java.math.BigDecimal;

public class User {
    private int id;
    private String username;
    private String email;
    private String imie;
    private String nazwisko;
    private String adres;
    private BigDecimal saldo;

    public User(int id, String username, String email, String imie, String nazwisko, String adres, BigDecimal saldo) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.adres = adres;
        this.saldo = saldo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}

