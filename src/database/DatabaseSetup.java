/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseSetup {
      public static void main(String[] args) {
        String sql = "CREATE TABLE IF NOT EXISTS resep ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nama_resep TEXT NOT NULL,"
                + "kategori TEXT NOT NULL,"
                + "bahan TEXT NOT NULL,"
                + "langkah TEXT NOT NULL,"
                + "waktu_masak TEXT,"
                + "tingkat_kesulitan TEXT,"
                + "rating INTEGER DEFAULT 0,"
                + "is_favorit INTEGER DEFAULT 0"
                + ");";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            
            // Tambah kolom jika tabel sudah ada sebelumnya
            try {
                stmt.execute("ALTER TABLE resep ADD COLUMN rating INTEGER DEFAULT 0");
                System.out.println("Kolom 'rating' berhasil ditambahkan.");
            } catch (SQLException e) {
                System.out.println("Kolom 'rating' sudah ada atau error: " + e.getMessage());
            }
            
            try {
                stmt.execute("ALTER TABLE resep ADD COLUMN is_favorit INTEGER DEFAULT 0");
                System.out.println("Kolom 'is_favorit' berhasil ditambahkan.");
            } catch (SQLException e) {
                System.out.println("Kolom 'is_favorit' sudah ada atau error: " + e.getMessage());
            }
            
            System.out.println("Tabel 'resep' berhasil dibuat atau sudah ada.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
      }
}