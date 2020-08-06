package com.example.realminserview;

import io.realm.RealmObject;

public class KisiBilgileri extends RealmObject {
    private String kullanici;
    private String sifre;
    private String cinsiyet;
    private String isim;

    public KisiBilgileri(String kullanici, String sifre, String cinsiyet, String isim) {
        this.kullanici = kullanici;
        this.sifre = sifre;
        this.cinsiyet = cinsiyet;
        this.isim = isim;
    }

    public String getKullanici() {
        return kullanici;
    }

    public void setKullanici(String kullanici) {
        this.kullanici = kullanici;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getCinsiyet() {
        return cinsiyet;
    }

    public void setCinsiyet(String cinsiyet) {
        this.cinsiyet = cinsiyet;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    @Override
    public String toString() {
        return "KisiBilgileri{" +
                "kullanici='" + kullanici + '\'' +
                ", sifre='" + sifre + '\'' +
                ", cinsiyet='" + cinsiyet + '\'' +
                ", isim='" + isim + '\'' +
                '}';
    }
}
