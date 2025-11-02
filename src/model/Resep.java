/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author USER
 */

public class Resep {
    private int id;
    private String namaResep;
    private String kategori;
    private String bahan;
    private String langkah;
    private String waktuMasak;
    private String tingkatKesulitan;
    private int rating;
    private boolean isFavorit;
    
    // Constructor
    public Resep(int id, String namaResep, String kategori, String bahan, 
                 String langkah, String waktuMasak, String tingkatKesulitan,
                 int rating, boolean isFavorit) {
        this.id = id;
        this.namaResep = namaResep;
        this.kategori = kategori;
        this.bahan = bahan;
        this.langkah = langkah;
        this.waktuMasak = waktuMasak;
        this.tingkatKesulitan = tingkatKesulitan;
        this.rating = rating;
        this.isFavorit = isFavorit;
    }
    
    // Getters dan Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNamaResep() {
        return namaResep;
    }
    
    public void setNamaResep(String namaResep) {
        this.namaResep = namaResep;
    }
    
    public String getKategori() {
        return kategori;
    }
    
    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
    
    public String getBahan() {
        return bahan;
    }
    
    public void setBahan(String bahan) {
        this.bahan = bahan;
    }
    
    public String getLangkah() {
        return langkah;
    }
    
    public void setLangkah(String langkah) {
        this.langkah = langkah;
    }
    
    public String getWaktuMasak() {
        return waktuMasak;
    }
    
    public void setWaktuMasak(String waktuMasak) {
        this.waktuMasak = waktuMasak;
    }
    
    public String getTingkatKesulitan() {
        return tingkatKesulitan;
    }
    
    public void setTingkatKesulitan(String tingkatKesulitan) {
        this.tingkatKesulitan = tingkatKesulitan;
    }
    
    public int getRating() {
        return rating;
    }
    
    public void setRating(int rating) {
        this.rating = rating;
    }
    
    public boolean isFavorit() {
        return isFavorit;
    }
    
    public void setFavorit(boolean favorit) {
        this.isFavorit = favorit;
    }
}
