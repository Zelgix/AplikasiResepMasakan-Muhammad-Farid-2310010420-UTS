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
                + "tingkat_kesulitan TEXT"
                + ");";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabel 'resep' berhasil dibuat atau sudah ada.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
