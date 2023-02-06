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
public class Xuong implements Serializable{
    private String maX, ten;
    private int heSo;

    public Xuong() {
    }

    public Xuong(String maX, String ten, int heSo) {
        this.maX = maX;
        this.ten = ten;
        this.heSo = heSo;
    }

    public String getMaX() {
        return maX;
    }

    public void setMaX(String maX) {
        this.maX = maX;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getHeSo() {
        return heSo;
    }

    public void setHeSo(int heSo) {
        this.heSo = heSo;
    }
    
    public Object[] toObject(){
        return new Object[]{
            maX, ten, heSo
        };
    }
}
