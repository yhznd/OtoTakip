package com.example.yunus.ototakip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eren Özhan on 9.4.2017.
 */

public class Araba {
    private String userId;
    private String userMail;
    private String editTextPlaka;
    private String editTextModel;
    private String editTextKaskoTarihi;
    private String editTextMuayeneTarihi;
    private String editTextSigortaTarihi;
    private String editTextEmisyonTarihi;

    public Araba(){}



    public Araba(String userMail,String editTextModel,String editTextKaskoTarihi, String editTextMuayeneTarihi, String editTextSigortaTarihi,String editTextEmisyonTarihi ) {
        this.userId=userId;
        this.userMail=userMail;
        this.editTextPlaka = editTextPlaka;
        this.editTextModel = editTextModel;
        this.editTextKaskoTarihi = editTextKaskoTarihi;
        this.editTextMuayeneTarihi = editTextMuayeneTarihi;
        this.editTextSigortaTarihi = editTextSigortaTarihi;
        this.editTextEmisyonTarihi = editTextEmisyonTarihi;

    }

    public Araba()
    {

    }
    public String getEditTextPlaka() {
        return editTextPlaka;
    }

    public void setEditTextPlaka(String editTextPlaka) {
        this.editTextPlaka = editTextPlaka;
    }

    public String getEditTextModel() {
        return editTextModel;
    }

    public void setEditTextModel(String editTextModel) {
        this.editTextModel = editTextModel;
    }

    public String getEditTextKaskoTarihi() {
        return editTextKaskoTarihi;
    }

    public void setEditTextKaskoTarihi(String editTextKaskoTarihi) {
        this.editTextKaskoTarihi = editTextKaskoTarihi;
    }

    public String getEditTextMuayeneTarihi() {
        return editTextMuayeneTarihi;
    }

    public void setEditTextMuayeneTarihi(String editTextMuayeneTarihi) {
        this.editTextMuayeneTarihi = editTextMuayeneTarihi;
    }

    public String getEditTextSigortaTarihi() {
        return editTextSigortaTarihi;
    }

    public void setEditTextSigortaTarihi(String editTextSigortaTarihi) {
        this.editTextSigortaTarihi = editTextSigortaTarihi;
    }

    public String getEditTextEmisyonTarihi() {
        return editTextEmisyonTarihi;
    }

    public void setEditTextEmisyonTarihi(String editTextEmisyonTarihi) {
        this.editTextEmisyonTarihi = editTextEmisyonTarihi;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

}
