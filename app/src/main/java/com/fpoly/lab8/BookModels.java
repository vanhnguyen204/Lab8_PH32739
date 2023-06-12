package com.fpoly.lab8;

import java.io.Serializable;

public class BookModels implements Serializable {
    private String tenSach;
    private int namXuatBan;
    private String tacGia;

    public BookModels(String tenSach, int namXuatBan, String tacGia) {
        this.tenSach = tenSach;
        this.namXuatBan = namXuatBan;
        this.tacGia = tacGia;
    }
    public BookModels(){}

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getNamXuatBan() {
        return namXuatBan;
    }

    public void setNamXuatBan(int namXuatBan) {
        this.namXuatBan = namXuatBan;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }
}
