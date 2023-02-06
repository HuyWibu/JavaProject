/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class BangChamCong implements Serializable{
    private CongNhan congNhan;
    private Xuong xuong; 
    private int soNgay;

    public BangChamCong() {
    }

    public BangChamCong(CongNhan congNhan, Xuong xuong, int soNgay) {
        this.congNhan = congNhan;
        this.xuong = xuong;
        this.soNgay = soNgay;
        
    }

    public CongNhan getCongNhan() {
        return congNhan;
    }

    public void setCongNhan(CongNhan congNhan) {
        this.congNhan = congNhan;
    }

    public Xuong getXuong() {
        return xuong;
    }

    public void setXuong(Xuong xuong) {
        this.xuong = xuong;
    }

    public int getSoNgay() {
        return soNgay;
    }

    public void setSoNgay(int soNgay) {
        this.soNgay = soNgay;
    }
    
    
    
    public Object[] toObject(){
        return new Object[]{
            congNhan.getMaCN(), congNhan.getHoTen(),
            xuong.getMaX(), soNgay
        };
    };
    
    
}
