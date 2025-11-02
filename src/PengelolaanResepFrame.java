/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author USER
 */
import controller.ResepController;
import model.Resep;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;
import java.io.*;

public class PengelolaanResepFrame extends javax.swing.JFrame {
    private DefaultTableModel model;
    private ResepController controller;
    /**
     * Creates new form PengelolaanResepFrame
     */
    public PengelolaanResepFrame() {
        initComponents();
        controller = new ResepController();
        model = new DefaultTableModel(
            new String[]{"No", "Nama Resep", "Kategori", "Waktu", "Kesulitan"}, 0
        );
        tblResep.setModel(model);
        loadResep();
    }
    private void loadResep() {
        try {
            model.setRowCount(0);
            List<Resep> resepList = controller.getAllResep();
            int rowNumber = 1;
            for (Resep resep : resepList) {
                model.addRow(new Object[]{
                    rowNumber++,
                    resep.getNamaResep(),
                    resep.getKategori(),
                    resep.getWaktuMasak() + " menit",
                    resep.getTingkatKesulitan()
                });
            }
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", 
                                     JOptionPane.ERROR_MESSAGE);
    }
    
    private void addResep() {
        String namaResep = txtNamaResep.getText().trim();
        String kategori = (String) cmbKategori.getSelectedItem();
        String bahan = txtBahan.getText().trim();
        String langkah = txtLangkah.getText().trim();
        String waktuMasak = txtWaktuMasak.getText().trim();
        String tingkatKesulitan = (String) cmbKesulitan.getSelectedItem();
        
        // Validasi input
        if (namaResep.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama resep tidak boleh kosong!", 
                                         "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (bahan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bahan-bahan tidak boleh kosong!", 
                                         "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (langkah.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Langkah-langkah tidak boleh kosong!", 
                                         "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!waktuMasak.isEmpty() && !waktuMasak.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Waktu masak harus berupa angka!", 
                                         "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Cek duplikasi nama resep
            if (controller.isDuplicateResep(namaResep, null)) {
                JOptionPane.showMessageDialog(this, 
                    "Resep dengan nama ini sudah ada!", 
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            controller.addResep(namaResep, kategori, bahan, langkah, 
                               waktuMasak, tingkatKesulitan);
            loadResep();
            clearForm();
            JOptionPane.showMessageDialog(this, "Resep berhasil ditambahkan!");
        } catch (SQLException ex) {
            showError("Gagal menambahkan resep: " + ex.getMessage());
        }
    }
    
    private void clearForm() {
        txtNamaResep.setText("");
        txtBahan.setText("");
        txtLangkah.setText("");
        txtWaktuMasak.setText("");
        cmbKategori.setSelectedIndex(0);
        cmbKesulitan.setSelectedIndex(0);
    }
    
    private void editResep() {
        int selectedRow = tblResep.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Pilih resep yang ingin diperbarui!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = selectedRow + 1; // ID sama dengan nomor urut
        String namaResep = txtNamaResep.getText().trim();
        String kategori = (String) cmbKategori.getSelectedItem();
        String bahan = txtBahan.getText().trim();
        String langkah = txtLangkah.getText().trim();
        String waktuMasak = txtWaktuMasak.getText().trim();
        String tingkatKesulitan = (String) cmbKesulitan.getSelectedItem();
        
        // Validasi sama seperti tambah
        if (namaResep.isEmpty() || bahan.isEmpty() || langkah.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Semua field wajib diisi!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!waktuMasak.isEmpty() && !waktuMasak.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, 
                "Waktu masak harus berupa angka!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Cek duplikasi (exclude ID yang sedang diedit)
            if (controller.isDuplicateResep(namaResep, id)) {
                JOptionPane.showMessageDialog(this, 
                    "Resep dengan nama ini sudah ada!", 
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            controller.updateResep(id, namaResep, kategori, bahan, langkah, 
                                  waktuMasak, tingkatKesulitan);
            loadResep();
            clearForm();
            JOptionPane.showMessageDialog(this, "Resep berhasil diperbarui!");
        } catch (SQLException ex) {
            showError("Gagal memperbarui resep: " + ex.getMessage());
        }
    }
    
    private void populateForm(int selectedRow) {
        try {
            List<Resep> resepList = controller.getAllResep();
            Resep resep = resepList.get(selectedRow);
            
            txtNamaResep.setText(resep.getNamaResep());
            cmbKategori.setSelectedItem(resep.getKategori());
            txtBahan.setText(resep.getBahan());
            txtLangkah.setText(resep.getLangkah());
            txtWaktuMasak.setText(resep.getWaktuMasak());
            cmbKesulitan.setSelectedItem(resep.getTingkatKesulitan());
        } catch (SQLException ex) {
            showError("Gagal mengambil data: " + ex.getMessage());
        }
    }
    
    private void deleteResep() {
        int selectedRow = tblResep.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Pilih resep yang ingin dihapus!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Konfirmasi hapus
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Apakah Anda yakin ingin menghapus resep ini?", 
            "Konfirmasi Hapus", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                List<Resep> resepList = controller.getAllResep();
                Resep resep = resepList.get(selectedRow);
                int id = resep.getId();
                
                controller.deleteResep(id);
                loadResep();
                clearForm();
                JOptionPane.showMessageDialog(this, "Resep berhasil dihapus!");
            } catch (SQLException ex) {
                showError("Gagal menghapus resep: " + ex.getMessage());
            }
        }
    }
    
    private void searchResep() {
        String keyword = txtPencarian.getText().trim();
        
        if (keyword.isEmpty()) {
            loadResep(); // Tampilkan semua jika kosong
            return;
        }
        
        try {
            model.setRowCount(0);
            List<Resep> resepList = controller.searchResep(keyword);
            int rowNumber = 1;
            
            for (Resep resep : resepList) {
                model.addRow(new Object[]{
                    rowNumber++,
                    resep.getNamaResep(),
                    resep.getKategori(),
                    resep.getWaktuMasak() + " menit",
                    resep.getTingkatKesulitan()
                });
            }
            
            if (resepList.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Tidak ada resep ditemukan untuk: " + keyword,
                    "Hasil Pencarian", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            showError("Gagal melakukan pencarian: " + ex.getMessage());
        }
    }
    
    private void showCSVGuide() {
        String guideMessage = 
            "📋 FORMAT CSV UNTUK IMPOR DATA:\n\n" +
            "Header wajib (baris pertama):\n" +
            "ID,Nama Resep,Kategori,Bahan,Langkah,Waktu Masak,Tingkat Kesulitan\n\n" +
            "Aturan:\n" +
            "• ID boleh kosong atau angka apa saja (akan di-generate otomatis)\n" +
            "• Nama Resep, Bahan, dan Langkah WAJIB diisi\n" +
            "• Kategori: Pembuka/Utama/Dessert/Minuman/Camilan\n" +
            "• Waktu Masak: Angka dalam menit (boleh kosong)\n" +
            "• Tingkat Kesulitan: Mudah/Sedang/Sulit\n\n" +
            "Contoh isi file CSV:\n" +
            "1,Nasi Goreng,Utama,\"2 piring nasi, 2 telur\",\"Tumis bumbu\",30,Mudah\n" +
            "2,Soto Ayam,Utama,\"500g ayam, bumbu\",\"Rebus ayam\",45,Sedang\n\n" +
            "⚠️ Jika data mengandung koma, bungkus dengan tanda kutip (\")";
        
        JOptionPane.showMessageDialog(this, guideMessage, 
            "Panduan Format CSV", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private boolean validateCSVHeader(String header) {
        if (header == null) return false;
        String normalized = header.trim().replaceAll("\\s+", " ");
        return normalized.equalsIgnoreCase("ID,Nama Resep,Kategori,Bahan,Langkah,Waktu Masak,Tingkat Kesulitan") ||
               normalized.equalsIgnoreCase("ID, Nama Resep, Kategori, Bahan, Langkah, Waktu Masak, Tingkat Kesulitan");
    }
    
    private String[] parseCSVLine(String line) {
        java.util.List<String> result = new java.util.ArrayList<>();
        boolean inQuotes = false;
        StringBuilder field = new StringBuilder();
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                // Check for escaped quote
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    field.append('"');
                    i++; // Skip next quote
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                result.add(field.toString());
                field = new StringBuilder();
            } else {
                field.append(c);
            }
        }
        result.add(field.toString());
        
        return result.toArray(new String[0]);
    }
    
    private void importFromCSV() {
        showCSVGuide();
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Apakah Anda yakin file CSV yang dipilih sudah sesuai dengan format?",
            "Konfirmasi Impor CSV",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Pilih File CSV");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "CSV Files", "csv"));
        
        int userSelection = fileChooser.showOpenDialog(this);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fileChooser.getSelectedFile();
            
            try (BufferedReader reader = new BufferedReader(new FileReader(fileToOpen))) {
                String line = reader.readLine(); // Baca header
                
                if (!validateCSVHeader(line)) {
                    JOptionPane.showMessageDialog(this, 
                        "Format header CSV tidak valid!\n" +
                        "Pastikan header adalah:\n" +
                        "ID,Nama Resep,Kategori,Bahan,Langkah,Waktu Masak,Tingkat Kesulitan",
                        "Kesalahan CSV", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                int successCount = 0;
                int errorCount = 0;
                int duplicateCount = 0;
                int lineNumber = 1;
                StringBuilder errorLog = new StringBuilder("Baris dengan kesalahan:\n");
                
                while ((line = reader.readLine()) != null) {
                    lineNumber++;
                    
                    // Parse CSV line (support quoted fields)
                    String[] data = parseCSVLine(line);
                    
                    if (data.length < 7) {
                        errorCount++;
                        errorLog.append("Baris ").append(lineNumber)
                               .append(": Format kolom tidak sesuai (kurang dari 7 kolom).\n");
                        continue;
                    }
                    
                    // Extract data (skip ID karena auto-increment)
                    String namaResep = data[1].trim();
                    String kategori = data[2].trim();
                    String bahan = data[3].trim();
                    String langkah = data[4].trim();
                    String waktuMasak = data[5].trim();
                    String tingkatKesulitan = data[6].trim();
                    
                    // Validasi data wajib
                    if (namaResep.isEmpty() || bahan.isEmpty() || langkah.isEmpty()) {
                        errorCount++;
                        errorLog.append("Baris ").append(lineNumber)
                               .append(": Data wajib (Nama/Bahan/Langkah) kosong.\n");
                        continue;
                    }
                    
                    // Validasi waktu masak
                    if (!waktuMasak.isEmpty() && !waktuMasak.matches("\\d+")) {
                        errorCount++;
                        errorLog.append("Baris ").append(lineNumber)
                               .append(": Waktu masak harus berupa angka.\n");
                        continue;
                    }
                    
                    // Validasi kategori
                    if (!kategori.matches("Pembuka|Utama|Dessert|Minuman|Camilan")) {
                        errorCount++;
                        errorLog.append("Baris ").append(lineNumber)
                               .append(": Kategori tidak valid.\n");
                        continue;
                    }
                    
                    // Validasi tingkat kesulitan
                    if (!tingkatKesulitan.matches("Mudah|Sedang|Sulit")) {
                        errorCount++;
                        errorLog.append("Baris ").append(lineNumber)
                               .append(": Tingkat kesulitan tidak valid.\n");
                        continue;
                    }
                    
                    // Cek duplikasi
                    if (controller.isDuplicateResep(namaResep, null)) {
                        duplicateCount++;
                        errorLog.append("Baris ").append(lineNumber)
                               .append(": Resep sudah ada (duplikat).\n");
                        continue;
                    }
                    
                    // Simpan ke database
                    try {
                        controller.addResep(namaResep, kategori, bahan, langkah, 
                                          waktuMasak, tingkatKesulitan);
                        successCount++;
                    } catch (SQLException ex) {
                        errorCount++;
                        errorLog.append("Baris ").append(lineNumber)
                               .append(": Gagal menyimpan - ").append(ex.getMessage()).append("\n");
                    }
                }
                
                // Refresh tabel
                loadResep();
                
                // Tampilkan hasil impor
                String resultMessage = String.format(
                    "Proses impor selesai!\n\n" +
                    "✅ Berhasil: %d resep\n" +
                    "❌ Gagal: %d baris\n" +
                    "⚠️ Duplikat: %d baris",
                    successCount, errorCount, duplicateCount);
                
                if (errorCount > 0 || duplicateCount > 0) {
                    resultMessage += "\n\nDetail error:\n" + errorLog.toString();
                    JOptionPane.showMessageDialog(this, resultMessage, 
                        "Hasil Impor", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, resultMessage, 
                        "Hasil Impor", JOptionPane.INFORMATION_MESSAGE);
                }
                
            } catch (IOException ex) {
                showError("Gagal membaca file: " + ex.getMessage());
            } catch (SQLException ex) {
                showError("Kesalahan database: " + ex.getMessage());
            }
        }
    }
    
     private void exportToCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Simpan File CSV");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "CSV Files", "csv"));
        
        int userSelection = fileChooser.showSaveDialog(this);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            
            // Tambahkan ekstensi .csv jika belum ada
            if (!fileToSave.getAbsolutePath().endsWith(".csv")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".csv");
            }
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                // Header CSV
                writer.write("ID,Nama Resep,Kategori,Bahan,Langkah,Waktu Masak,Tingkat Kesulitan\n");
                
                // Data resep
                List<Resep> resepList = controller.getAllResep();
                for (Resep resep : resepList) {
                    // Escape koma dan newline dalam data
                    String nama = escapeCSV(resep.getNamaResep());
                    String kategori = escapeCSV(resep.getKategori());
                    String bahan = escapeCSV(resep.getBahan());
                    String langkah = escapeCSV(resep.getLangkah());
                    String waktu = escapeCSV(resep.getWaktuMasak());
                    String kesulitan = escapeCSV(resep.getTingkatKesulitan());
                    
                    writer.write(String.format("%d,%s,%s,%s,%s,%s,%s\n",
                        resep.getId(), nama, kategori, bahan, langkah, waktu, kesulitan));
                }
                
                JOptionPane.showMessageDialog(this, 
                    "Data berhasil diekspor ke:\n" + fileToSave.getAbsolutePath(),
                    "Ekspor Berhasil", 
                    JOptionPane.INFORMATION_MESSAGE);
                    
            } catch (IOException ex) {
                showError("Gagal menulis file: " + ex.getMessage());
            } catch (SQLException ex) {
                showError("Gagal mengambil data: " + ex.getMessage());
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelJudul = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        PanelResep = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblKategori = new javax.swing.JLabel();
        lblBahan = new javax.swing.JLabel();
        lblLangkah = new javax.swing.JLabel();
        lblWaktuMasak = new javax.swing.JLabel();
        lblTingkatKesulitan = new javax.swing.JLabel();
        txtNamaResep = new javax.swing.JTextField();
        cmbKategori = new javax.swing.JComboBox<>();
        txtWaktuMasak = new javax.swing.JTextField();
        cmbKesulitan = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtBahan = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtLangkah = new javax.swing.JTextArea();
        btnTambah = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnBersih = new javax.swing.JButton();
        PanelPencarian = new javax.swing.JPanel();
        lblPencarian = new javax.swing.JLabel();
        txtPencarian = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblResep = new javax.swing.JTable();
        btnHapus = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        btnImport = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("APLIKASI RESEP MASAKAN");

        javax.swing.GroupLayout PanelJudulLayout = new javax.swing.GroupLayout(PanelJudul);
        PanelJudul.setLayout(PanelJudulLayout);
        PanelJudulLayout.setHorizontalGroup(
            PanelJudulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelJudulLayout.createSequentialGroup()
                .addContainerGap(232, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(165, 165, 165))
        );
        PanelJudulLayout.setVerticalGroup(
            PanelJudulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelJudulLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Nama Resep");

        lblKategori.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblKategori.setText("Kategori");

        lblBahan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblBahan.setText("Bahan-Bahan");

        lblLangkah.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblLangkah.setText("Langkah-Langkah");

        lblWaktuMasak.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblWaktuMasak.setText("Waktu Masak (Menit)");

        lblTingkatKesulitan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTingkatKesulitan.setText("Tingkat Kesulitan");

        cmbKategori.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbKategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pembuka", "Utama", "Dessert", "Minuman", "Cemilan" }));

        cmbKesulitan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbKesulitan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mudah", "Sedang", "Sulit" }));

        txtBahan.setColumns(20);
        txtBahan.setLineWrap(true);
        txtBahan.setRows(5);
        jScrollPane1.setViewportView(txtBahan);

        txtLangkah.setColumns(20);
        txtLangkah.setLineWrap(true);
        txtLangkah.setRows(5);
        jScrollPane2.setViewportView(txtLangkah);

        btnTambah.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTambah.setText("TAMBAH");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnEdit.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEdit.setText("EDIT");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnBersih.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBersih.setText("BERSIHKAN FORM");
        btnBersih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBersihActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelResepLayout = new javax.swing.GroupLayout(PanelResep);
        PanelResep.setLayout(PanelResepLayout);
        PanelResepLayout.setHorizontalGroup(
            PanelResepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelResepLayout.createSequentialGroup()
                .addGroup(PanelResepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(txtNamaResep, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(PanelResepLayout.createSequentialGroup()
                        .addGroup(PanelResepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelResepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtWaktuMasak)
                                .addComponent(cmbKategori, 0, 174, Short.MAX_VALUE))
                            .addGroup(PanelResepLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(PanelResepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblBahan, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblLangkah, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(PanelResepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTingkatKesulitan, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbKesulitan, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(PanelResepLayout.createSequentialGroup()
                        .addComponent(btnTambah, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBersih)
                        .addGap(18, 18, 18)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(PanelResepLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblWaktuMasak)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelResepLayout.setVerticalGroup(
            PanelResepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelResepLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNamaResep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelResepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblKategori)
                    .addComponent(lblTingkatKesulitan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelResepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbKesulitan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblWaktuMasak)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtWaktuMasak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblBahan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLangkah)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelResepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBersih, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        lblPencarian.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPencarian.setText("Pencarian :");

        txtPencarian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPencarianKeyReleased(evt);
            }
        });

        tblResep.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblResep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblResepMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblResep);

        btnHapus.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHapus.setText("HAPUS");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnExport.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnExport.setText("EXPORT");
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        btnImport.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnImport.setText("IMPORT");
        btnImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelPencarianLayout = new javax.swing.GroupLayout(PanelPencarian);
        PanelPencarian.setLayout(PanelPencarianLayout);
        PanelPencarianLayout.setHorizontalGroup(
            PanelPencarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPencarianLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelPencarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelPencarianLayout.createSequentialGroup()
                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnImport, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblPencarian, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPencarian, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelPencarianLayout.setVerticalGroup(
            PanelPencarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPencarianLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblPencarian)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPencarian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelPencarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImport, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PanelJudul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelResep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PanelPencarian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PanelJudul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelResep, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelPencarian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        addResep();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        editResep();
    }//GEN-LAST:event_btnEditActionPerformed

    private void tblResepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblResepMouseClicked
        int selectedRow = tblResep.getSelectedRow();
    if (selectedRow != -1) {
        populateForm(selectedRow);}
    }//GEN-LAST:event_tblResepMouseClicked

    private void btnBersihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBersihActionPerformed
        clearForm();
    }//GEN-LAST:event_btnBersihActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        deleteResep();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void txtPencarianKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPencarianKeyReleased
        searchResep();
    }//GEN-LAST:event_txtPencarianKeyReleased

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        // Tampilkan pilihan format export
    String[] options = {"CSV", "JSON", "Batal"};
    int choice = JOptionPane.showOptionDialog(this,
        "Pilih format file untuk export:",
        "Export Data",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        options,
        options[0]);
    
    if (choice == 0) {
        exportToCSV();
    } else if (choice == 1) {
   }
    }//GEN-LAST:event_btnExportActionPerformed

    private void btnImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportActionPerformed
        importFromCSV();
    }//GEN-LAST:event_btnImportActionPerformed

    
    // Helper method untuk escape karakter khusus di CSV
    private String escapeCSV(String data) {
        if (data == null) return "";
        
        // Jika mengandung koma, newline, atau quote, bungkus dengan quotes
        if (data.contains(",") || data.contains("\n") || data.contains("\"")) {
            // Escape quote dengan double quote
            data = data.replace("\"", "\"\"");
            return "\"" + data + "\"";
        }
        return data;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PengelolaanResepFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PengelolaanResepFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PengelolaanResepFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PengelolaanResepFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PengelolaanResepFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelJudul;
    private javax.swing.JPanel PanelPencarian;
    private javax.swing.JPanel PanelResep;
    private javax.swing.JButton btnBersih;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnImport;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cmbKategori;
    private javax.swing.JComboBox<String> cmbKesulitan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblBahan;
    private javax.swing.JLabel lblKategori;
    private javax.swing.JLabel lblLangkah;
    private javax.swing.JLabel lblPencarian;
    private javax.swing.JLabel lblTingkatKesulitan;
    private javax.swing.JLabel lblWaktuMasak;
    private javax.swing.JTable tblResep;
    private javax.swing.JTextArea txtBahan;
    private javax.swing.JTextArea txtLangkah;
    private javax.swing.JTextField txtNamaResep;
    private javax.swing.JTextField txtPencarian;
    private javax.swing.JTextField txtWaktuMasak;
    // End of variables declaration//GEN-END:variables
}
