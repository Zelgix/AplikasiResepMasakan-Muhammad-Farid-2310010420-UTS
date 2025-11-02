/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Resep;
import model.ResepDAO;
import java.sql.SQLException;
import java.util.List;

public class ResepController {
    private ResepDAO resepDAO;
    
    public ResepController() {
        resepDAO = new ResepDAO();
    }
    
    // Method mengambil semua data resep
    public List<Resep> getAllResep() throws SQLException {
        return resepDAO.getAllResep();
    }
    
    // Method menambah resep
    public void addResep(String namaResep, String kategori, String bahan, 
                        String langkah, String waktuMasak, String tingkatKesulitan) 
                        throws SQLException {
        Resep resep = new Resep(0, namaResep, kategori, bahan, langkah, 
                               waktuMasak, tingkatKesulitan);
        resepDAO.addResep(resep);
    }
    
    // Method mengupdate resep
    public void updateResep(int id, String namaResep, String kategori, String bahan, 
                           String langkah, String waktuMasak, String tingkatKesulitan) 
                           throws SQLException {
        Resep resep = new Resep(id, namaResep, kategori, bahan, langkah, 
                               waktuMasak, tingkatKesulitan);
        resepDAO.updateResep(resep);
    }
    
    // Method menghapus resep
    public void deleteResep(int id) throws SQLException {
        resepDAO.deleteResep(id);
    }
    
    // Method pencarian resep
    public List<Resep> searchResep(String keyword) throws SQLException {
        return resepDAO.searchResep(keyword);
    }
    
    // Method mengecek duplikasi
    public boolean isDuplicateResep(String namaResep, Integer excludeId) throws SQLException {
        return resepDAO.isDuplicateResep(namaResep, excludeId);
    }
}

