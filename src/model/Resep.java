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
    
    // Constructor
    public Resep(int id, String namaResep, String kategori, String bahan, 
                 String langkah, String waktuMasak, String tingkatKesulitan) {
        this.id = id;
        this.namaResep = namaResep;
        this.kategori = kategori;
        this.bahan = bahan;
        this.langkah = langkah;
        this.waktuMasak = waktuMasak;
        this.tingkatKesulitan = tingkatKesulitan;
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
}
