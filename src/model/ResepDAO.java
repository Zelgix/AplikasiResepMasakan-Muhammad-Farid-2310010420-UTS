/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResepDAO {
     // Method untuk mengambil semua data resep
    public List<Resep> getAllResep() throws SQLException {
        List<Resep> resepList = new ArrayList<>();
        String sql = "SELECT * FROM resep";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Resep resep = new Resep(
                    rs.getInt("id"),
                    rs.getString("nama_resep"),
                    rs.getString("kategori"),
                    rs.getString("bahan"),
                    rs.getString("langkah"),
                    rs.getString("waktu_masak"),
                    rs.getString("tingkat_kesulitan"),
                    rs.getInt("rating"),
                    rs.getInt("is_favorit") == 1
                );
                resepList.add(resep);
            }
        }
        return resepList;
    }
    
    // Method untuk menambahkan resep
    public void addResep(Resep resep) throws SQLException {
        String sql = "INSERT INTO resep (nama_resep, kategori, bahan, langkah, waktu_masak, tingkat_kesulitan, rating, is_favorit) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, resep.getNamaResep());
            pstmt.setString(2, resep.getKategori());
            pstmt.setString(3, resep.getBahan());
            pstmt.setString(4, resep.getLangkah());
            pstmt.setString(5, resep.getWaktuMasak());
            pstmt.setString(6, resep.getTingkatKesulitan());
            pstmt.setInt(7, resep.getRating());
            pstmt.setInt(8, resep.isFavorit() ? 1 : 0);
            pstmt.executeUpdate();
        }
    }
    
    // Method untuk mengupdate resep
    public void updateResep(Resep resep) throws SQLException {
        String sql = "UPDATE resep SET nama_resep = ?, kategori = ?, bahan = ?, "
                   + "langkah = ?, waktu_masak = ?, tingkat_kesulitan = ?, rating = ?, is_favorit = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, resep.getNamaResep());
            pstmt.setString(2, resep.getKategori());
            pstmt.setString(3, resep.getBahan());
            pstmt.setString(4, resep.getLangkah());
            pstmt.setString(5, resep.getWaktuMasak());
            pstmt.setString(6, resep.getTingkatKesulitan());
            pstmt.setInt(7, resep.getRating());
            pstmt.setInt(8, resep.isFavorit() ? 1 : 0);
            pstmt.setInt(9, resep.getId());
            pstmt.executeUpdate();
        }
    }
    
    // Method untuk menghapus resep
    public void deleteResep(int id) throws SQLException {
        String sql = "DELETE FROM resep WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
    
    // Method untuk mencari resep
    public List<Resep> searchResep(String keyword) throws SQLException {
        List<Resep> resepList = new ArrayList<>();
        String sql = "SELECT * FROM resep WHERE nama_resep LIKE ? OR kategori LIKE ? OR bahan LIKE ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            pstmt.setString(3, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Resep resep = new Resep(
                    rs.getInt("id"),
                    rs.getString("nama_resep"),
                    rs.getString("kategori"),
                    rs.getString("bahan"),
                    rs.getString("langkah"),
                    rs.getString("waktu_masak"),
                    rs.getString("tingkat_kesulitan"),
                    rs.getInt("rating"),
                    rs.getInt("is_favorit") == 1
                );
                resepList.add(resep);
            }
        }
        return resepList;
    }
    
    // BARU: Method untuk mengambil resep favorit
    public List<Resep> getFavoriteResep() throws SQLException {
        List<Resep> resepList = new ArrayList<>();
        String sql = "SELECT * FROM resep WHERE is_favorit = 1";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Resep resep = new Resep(
                    rs.getInt("id"),
                    rs.getString("nama_resep"),
                    rs.getString("kategori"),
                    rs.getString("bahan"),
                    rs.getString("langkah"),
                    rs.getString("waktu_masak"),
                    rs.getString("tingkat_kesulitan"),
                    rs.getInt("rating"),
                    rs.getInt("is_favorit") == 1
                );
                resepList.add(resep);
            }
        }
        return resepList;
    }
    
    // BARU: Method untuk mengambil resep berdasarkan rating tertinggi
    public List<Resep> getTopRatedResep() throws SQLException {
        List<Resep> resepList = new ArrayList<>();
        String sql = "SELECT * FROM resep WHERE rating > 0 ORDER BY rating DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Resep resep = new Resep(
                    rs.getInt("id"),
                    rs.getString("nama_resep"),
                    rs.getString("kategori"),
                    rs.getString("bahan"),
                    rs.getString("langkah"),
                    rs.getString("waktu_masak"),
                    rs.getString("tingkat_kesulitan"),
                    rs.getInt("rating"),
                    rs.getInt("is_favorit") == 1
                );
                resepList.add(resep);
            }
        }
        return resepList;
    }
    
    // Method untuk mengecek duplikasi nama resep
    public boolean isDuplicateResep(String namaResep, Integer excludeId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM resep WHERE nama_resep = ?" 
                   + (excludeId != null ? " AND id != ?" : "");
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, namaResep);
            if (excludeId != null) {
                pstmt.setInt(2, excludeId);
            }
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
}

