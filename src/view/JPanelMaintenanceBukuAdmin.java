/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import java.awt.List;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import main.JFrameHome;
import main.PanelUpdateListener;

/**
 *
 * @author Hp
 */
public class JPanelMaintenanceBukuAdmin extends javax.swing.JPanel {
    private Runnable updateListener;
    DefaultTableModel tabelmodel; 
    Connection con=null; 
    Statement stat; 
    ResultSet res; 
    PreparedStatement pst = null; 
    String cekconeks; 
    private Map<String, String> kategoriMap = new HashMap<>();
    private Map<String, String> rakMap = new HashMap<>();
    JPanelViewBukuAdmin view = new JPanelViewBukuAdmin();
    
    /**
     * Creates new form JPanelMaintenanceBuku
     */
    public JPanelMaintenanceBukuAdmin() {
        initComponents();
        koneksi();
        dataToComboBoxKategori();
        dataToComboBoxRak();
        datatojtable();
         jComboKategori.addActionListener(e -> {
        if (jComboKategori.getSelectedItem() != null) {
            // Update data terkait kategori jika diperlukan
        }
    });
    
    jComboRak.addActionListener(e -> {
        if (jComboRak.getSelectedItem() != null) {
            // Update data terkait rak jika diperlukan
        }
    });
    }
    
    public void setUpdateListener(Runnable listener) {
        this.updateListener = listener;
    }
    
    private void triggerUpdate() {
        if (updateListener != null) {
            updateListener.run();
        }
    }
    
    private PanelUpdateListener comboBoxUpdateListener;
    
    public void setComboBoxUpdateListener(PanelUpdateListener listener) {
        this.comboBoxUpdateListener = listener;
    }
    
    // Method untuk refresh combo box
     public void refreshData() {
        datatojtable();
        dataToComboBoxKategori();
        dataToComboBoxRak();
    }
    
    private void koneksi(){ 
            try { 
               Class.forName("com.mysql.cj.jdbc.Driver"); 
     con=DriverManager.getConnection("jdbc:mysql://localhost/dbperpustakaan", "root", ""); 
               stat=con.createStatement(); 
             } catch (ClassNotFoundException| SQLException e) { 
               JOptionPane.showMessageDialog(null, e); 
             } 
      }
    
    public void dataToComboBoxKategori() {
    try {
        String sql = "SELECT IdKategori, NamaKategori FROM tkategori";
        pst = con.prepareStatement(sql);
        res = pst.executeQuery();
        
        jComboKategori.removeAllItems();
        kategoriMap.clear();
        
        while (res.next()) {
            String id = res.getString("IdKategori");
            String nama = res.getString("NamaKategori");
            
            kategoriMap.put(nama, id);
            jComboKategori.addItem(nama);
        }
        
        // Pertahankan seleksi sebelumnya jika ada
        if (jComboKategori.getItemCount() > 0) {
            jComboKategori.setSelectedIndex(0);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Gagal memuat data kategori : " + e.getMessage());
    } finally {
        try {
            if (res != null) res.close();
            if (pst != null) pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

public void dataToComboBoxRak() {
    try {
        String sql = "SELECT IdRak, NamaRak FROM trak";
        pst = con.prepareStatement(sql);
        res = pst.executeQuery();
        
        jComboRak.removeAllItems();
        rakMap.clear();
        
        while (res.next()) {
            String id = res.getString("IdRak");
            String nama = res.getString("NamaRak");
            
            rakMap.put(nama, id);
            jComboRak.addItem(nama);
        }
        
        // Pertahankan seleksi sebelumnya jika ada
        if (jComboRak.getItemCount() > 0) {
            jComboRak.setSelectedIndex(0);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Gagal memuat data rak : " + e.getMessage());
    } finally {
        try {
            if (res != null) res.close();
            if (pst != null) pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    
    private void datatojtable() 
    { 
        DefaultTableModel tb= new DefaultTableModel(); 
        // Memberi nama pada se�ap kolom tabel 
        tb.addColumn("Id Buku"); 
        tb.addColumn("Nama Buku"); 
        tb.addColumn("Pengarang"); 
        tb.addColumn("Penerbit"); 
        tb.addColumn("Jumlah");   
        tb.addColumn("Date Create"); 
        tb.addColumn("Date Modify"); 
        tb.addColumn("Id Kategori"); 
        tb.addColumn("Id Rak"); 
        
        jTableBuku.setModel(tb); 
        try{ 
        // Mengambil data dari database 
         res=stat.executeQuery("select *from tmasterbuku"); 
        while (res.next()) 
        { 
        // Mengambil data dari database berdasarkan nama kolom pada tabel 
            // Lalu di tampilkan ke dalam JTable 
            tb.addRow(new Object[]{ 
            res.getString("IdBuku"), 
            res.getString("NamaBuku"), 
            res.getString("Pengarang"), 
            res.getString("Penerbit"), 
            res.getString("Jumlah"), 
            res.getString("DateCreate"), 
            res.getString("DateModify"),     
            res.getString("IdKategori"), 
            res.getString("IdRak")     
           }); 
          } 
         Aturkolom(); //pemanggilan class untuk mengatur kolom 
        }catch (SQLException e){ 
        } 
           } 
    
    private void Aturkolom() 
    { 
        TableColumn column;
         jTableBuku.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF); 
        column = jTableBuku.getColumnModel().getColumn(0); 
        column.setPreferredWidth(75); 
        column = jTableBuku.getColumnModel().getColumn(1); 
        column.setPreferredWidth(200); 
        column = jTableBuku.getColumnModel().getColumn(2); 
        column.setPreferredWidth(100); 
        column = jTableBuku.getColumnModel().getColumn(3); 
        column.setPreferredWidth(75); 
        column = jTableBuku.getColumnModel().getColumn(4); 
        column.setPreferredWidth(75);     
        column = jTableBuku.getColumnModel().getColumn(5); 
        column.setPreferredWidth(80); 
        column = jTableBuku.getColumnModel().getColumn(6); 
        column.setPreferredWidth(75); 
    }
    
    private void cekdatabrg() 
    { 
      try{ 
         String sqlcek="SELECT b.*,r.NamaRak AS NamaRak,k.NamaKategori AS NamaKategori FROM tmasterbuku b INNER JOIN trak r ON b.IdRak = r.IdRak INNER JOIN tkategori k ON b.IdKategori = k.IdKategori WHERE b.IdBuku = '"+jTextIdBuku.getText()+"'";
            ResultSet rscek=stat.executeQuery(sqlcek); 
                  if (rscek.next()) 
                      
         { 
             JOptionPane.showMessageDialog(null, "Buku Ditemukan!, anda bisa merubah atau menghapus datanya");
             jTextIdBuku.setText(rscek.getString("IdBuku"));     
             jTextNamaBuku.setText(rscek.getString("NamaBuku")); 
             jTextPengarang.setText(rscek.getString("Pengarang"));
             jTextPenerbit.setText(rscek.getString("Penerbit"));
             jTextJumlah.setText(rscek.getString("Jumlah"));
             jComboKategori.setSelectedItem(rscek.getString("NamaKategori"));    
             jComboRak.setSelectedItem(rscek.getString("NamaRak"));    
             jTextIdBuku.requestFocus(); 
         }else{ 
              JOptionPane.showMessageDialog(null, "Buku tidak Ditemukan!, anda bisa memasukan datanya");
             jTextNamaBuku.setText(""); 
             jTextPenerbit.setText(""); 
             jTextPengarang.setText(""); 
             jTextJumlah.setText(""); 
             jTextNamaBuku.requestFocus(); 
         } 
                  
      }catch(SQLException e) 
        { 
          JOptionPane.showMessageDialog(null, "Gagal memuat cek data : " + e.getMessage());
        }      
    } 
    
     public void Bersihkan() 
   { 
       jTextIdBuku.setText(""); 
       jTextNamaBuku.setText(""); 
       jTextPenerbit.setText(""); 
       jTextPengarang.setText(""); 
       jComboKategori.setSelectedIndex(0);
       jComboRak.setSelectedIndex(0);
       jTextJumlah.setText("");
       jTextIdBuku.requestFocus(); 
   }
     
    private void insEksemplar(String idBuku, int jumlah) throws SQLException {
    // Cari nomor terakhir untuk IdBuku ini
    String findLastNumQuery = "SELECT MAX(IdEksemplar) as last_num FROM teksemplar WHERE IdBuku = ?";
    int startNumber = 1;
    
    try (PreparedStatement findStmt = con.prepareStatement(findLastNumQuery)) {
        findStmt.setString(1, idBuku);
        ResultSet rs = findStmt.executeQuery();
        
        if (rs.next() && rs.getObject("last_num") != null) {
            startNumber = rs.getInt("last_num") + 1;
        }
    }

    // Insert data baru
    String insertQuery = "INSERT INTO teksemplar (IdEksemplar, IdBuku, KodeEksemplar, DateCreate, DateModify) " +
                       "VALUES (?, ?, ?, NOW(), NOW())";
    
    try (PreparedStatement stmt = con.prepareStatement(insertQuery)) {
        for (int i = 0; i < jumlah; i++) {
            int currentId = startNumber + i;
            String kodeEks = idBuku + "." + currentId;
            
            stmt.setInt(1, currentId);
            stmt.setString(2, idBuku);
            stmt.setString(3, kodeEks);
            stmt.executeUpdate();
        }
    }
}
     
     private void updEksemplar(String idBuku, int xjml, String TglNow) throws SQLException {
         String namaKategori = jComboKategori.getSelectedItem().toString();
    String idKategori = kategoriMap.get(namaKategori);
    String namaRak = jComboRak.getSelectedItem().toString();
    String idRak = rakMap.get(namaRak);
    int response = JOptionPane.showConfirmDialog(null, 
        "Apakah Data Stok Akan di Tambah [Ya] atau Ubah Stok [Tidak]?",
        "Informasi",
        JOptionPane.YES_NO_OPTION);
    
    Statement findStmt = con.createStatement();
    ResultSet res = findStmt.executeQuery(
        "SELECT MAX(CAST(SUBSTRING_INDEX(KodeEksemplar, '.', -1) AS UNSIGNED)) as last_num " +
        "FROM teksemplar WHERE IdBuku='" + idBuku + "'");
    
    int startNumber = 1; // Default jika belum ada eksemplar
    if (res.next() && res.getObject("last_num") != null) {
        startNumber = res.getInt("last_num");
    }
    findStmt.close();
    
    if (response == JOptionPane.YES_OPTION) {
        // Tambah stok
        // Dapatkan jumlah saat ini
            Statement getStmt = con.createStatement();
            ResultSet rs = getStmt.executeQuery("SELECT Jumlah FROM tmasterbuku WHERE IdBuku='" + idBuku + "'");
            
            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "Buku dengan ID " + idBuku + " tidak ditemukan");
                getStmt.close();
                return;
            }
            
            int currentJumlah = rs.getInt("Jumlah");
        try {
            // Tambahkan eksemplar baru
            insEksemplar(idBuku, xjml);
            
            // Update jumlah di tmasterbuku
            Statement updateStmt = con.createStatement();
            updateStmt.executeUpdate("UPDATE tmasterbuku SET " +
                                   "Jumlah = " + (currentJumlah + xjml) + ", " +
                                   "DateModify = '" + TglNow + "' " +
                                   "WHERE IdBuku = '" + idBuku + "'");
            updateStmt.close();
            
            JOptionPane.showMessageDialog(null, "Berhasil menambahkan " + xjml + " eksemplar. " +
                                      "Stok sekarang: " + (currentJumlah + xjml));
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal menambah stok: " + e.getMessage());
            e.printStackTrace();
        }
    } else if (response == JOptionPane.NO_OPTION) {
        // Ubah stok
        try {
            // Hapus semua eksemplar lama
            Statement delStmt = con.createStatement();
            delStmt.executeUpdate("DELETE FROM teksemplar WHERE IdBuku='" + idBuku + "'");
            delStmt.close();
            
            // Tambahkan eksemplar baru
            insEksemplar(idBuku, xjml);
            
            // Update jumlah di tmasterbuku
            Statement updateStmt = con.createStatement();
            updateStmt.executeUpdate("UPDATE tmasterbuku SET " +
                "NamaBuku='" + jTextNamaBuku.getText() + "', " +
                "Pengarang='" + jTextPengarang.getText() + "', " +
                "Penerbit='" + jTextPenerbit.getText() + "', " +
                "Jumlah=" + xjml + ", " +
                "IdKategori='" + idKategori + "', " +
                "IdRak='" + idRak + "', " +
                "DateModify='" + TglNow + "' " +
                "WHERE IdBuku='" + idBuku + "'");
            updateStmt.close();
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal mengubah stok: " + e.getMessage());
            e.printStackTrace();
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextIdBuku = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextNamaBuku = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextPengarang = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextPenerbit = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jComboKategori = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jComboRak = new javax.swing.JComboBox<>();
        jButtonSimpan = new javax.swing.JButton();
        jButtonHapus = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableBuku = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jTextJumlah = new javax.swing.JTextField();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("MAINTENANCE BUKU");

        jLabel2.setText("ID BUKU");

        jTextIdBuku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextIdBukuKeyPressed(evt);
            }
        });

        jLabel3.setText("NAMA BUKU");

        jLabel4.setText("PENGARANG");

        jLabel5.setText("PENERBIT");

        jLabel6.setText("KATEGORI");

        jLabel7.setText("RAK");

        jButtonSimpan.setBackground(new java.awt.Color(0, 0, 255));
        jButtonSimpan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonSimpan.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSimpan.setText("SIMPAN");
        jButtonSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSimpanActionPerformed(evt);
            }
        });

        jButtonHapus.setBackground(new java.awt.Color(255, 0, 0));
        jButtonHapus.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonHapus.setForeground(new java.awt.Color(255, 255, 255));
        jButtonHapus.setText("HAPUS");
        jButtonHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHapusActionPerformed(evt);
            }
        });

        jTableBuku.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableBuku);

        jLabel8.setText("JUMLAH");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
                        .addGap(49, 49, 49))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonSimpan)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonHapus)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextJumlah))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboRak, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboKategori, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextPenerbit)
                                    .addComponent(jTextIdBuku)
                                    .addComponent(jTextNamaBuku)
                                    .addComponent(jTextPengarang, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextIdBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextNamaBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextPengarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextPenerbit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jComboRak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSimpan)
                    .addComponent(jButtonHapus))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextIdBukuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextIdBukuKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode()==KeyEvent.VK_ENTER) 
        { 
            cekdatabrg();            
        }
    }//GEN-LAST:event_jTextIdBukuKeyPressed

    private void jButtonHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHapusActionPerformed
        // TODO add your handling code here:
         if (jTextIdBuku.getText().isEmpty()) {
        JOptionPane.showMessageDialog(null, "ID Buku masih kosong");
        jTextIdBuku.requestFocus();
        return;
    }

    try {
        // Ambil daftar eksemplar untuk buku ini
        var eksemplarList = new ArrayList<String[]>();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT IdEksemplar, KodeEksemplar FROM teksemplar WHERE IdBuku='" + jTextIdBuku.getText() + "'")) {
            
            while (rs.next()) {
                String[] eksemplar = {rs.getString("IdEksemplar"), rs.getString("KodeEksemplar")};
                eksemplarList.add(eksemplar);
            }
        }

        if (eksemplarList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tidak ada eksemplar untuk buku ini");
            return;
        }

        // Buat opsi tambahan untuk hapus semua
        String[] options = new String[eksemplarList.size() + 1];
        for (int i = 0; i < eksemplarList.size(); i++) {
            options[i] = "ID: " + eksemplarList.get(i)[0] + " - Kode: " + eksemplarList.get(i)[1];
        }
        options[eksemplarList.size()] = "HAPUS SEMUA EKSPEMPLAR DAN BUKU";

        String selected = (String) JOptionPane.showInputDialog(
            null,
            "Pilih eksemplar yang ingin dihapus:",
            "Pilih Eksemplar",
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);

        if (selected == null) {
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null, 
            "Apakah yakin ingin menghapus?\n" + selected,
            "Konfirmasi Penghapusan",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        con.setAutoCommit(false);
        try {
            if (selected.equals("HAPUS SEMUA EKSPEMPLAR DAN BUKU")) {
                try (Statement stmtDelete = con.createStatement()) {
                    stmtDelete.executeUpdate("DELETE FROM teksemplar WHERE IdBuku='" + jTextIdBuku.getText() + "'");
                }
                try (Statement stmtDeleteBuku = con.createStatement()) {
                    stmtDeleteBuku.executeUpdate("DELETE FROM tmasterbuku WHERE IdBuku='" + jTextIdBuku.getText() + "'");
                }
                
                JOptionPane.showMessageDialog(null, "Semua eksemplar dan buku berhasil dihapus");
            } else {
                String selectedIdEksemplar = selected.split(" - ")[0].replace("ID: ", "").trim();

                try (Statement stmtDelete = con.createStatement()) {
                    stmtDelete.executeUpdate("DELETE FROM teksemplar WHERE IdEksemplar='" + selectedIdEksemplar + "'");
                }

                try (Statement stmtUpdate = con.createStatement()) {
                    stmtUpdate.executeUpdate("UPDATE tmasterbuku SET Jumlah = Jumlah - 1 WHERE IdBuku='" + jTextIdBuku.getText() + "'");
                }

                try (Statement stmtCount = con.createStatement();
                     ResultSet rs = stmtCount.executeQuery("SELECT COUNT(*) FROM teksemplar WHERE IdBuku='" + jTextIdBuku.getText() + "'")) {
                    
                    rs.next();
                    int jumlahEksemplar = rs.getInt(1);
                    
                    if (jumlahEksemplar == 0) {

                        try (Statement stmtDeleteBuku = con.createStatement()) {
                            stmtDeleteBuku.executeUpdate("DELETE FROM tmasterbuku WHERE IdBuku='" + jTextIdBuku.getText() + "'");
                        }
                        JOptionPane.showMessageDialog(null, "Eksemplar terakhir dan buku berhasil dihapus");
                    } else {
                        JOptionPane.showMessageDialog(null, "Eksemplar berhasil dihapus");
                    }
                }
            }
            
            con.commit();
        } catch (SQLException e) {
            con.rollback();
            throw e;
        } finally {
            con.setAutoCommit(true);
        }
        
        datatojtable();
        Bersihkan();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    triggerUpdate();
    }//GEN-LAST:event_jButtonHapusActionPerformed

    private void jButtonSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSimpanActionPerformed
        // TODO add your handling code here:
         java.util.Date tanggal = new java.util.Date();
    java.text.SimpleDateFormat setTanggal = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String TglNow = setTanggal.format(tanggal);
    
    String idBuku = jTextIdBuku.getText();
    String namaBuku = jTextNamaBuku.getText();
    String jumlahText = jTextJumlah.getText();
    String namaKategori = jComboKategori.getSelectedItem().toString();
    String idKategori = kategoriMap.get(namaKategori);
    String namaRak = jComboRak.getSelectedItem().toString();
    String idRak = rakMap.get(namaRak);
    
    // Validasi input
    if (idBuku.isEmpty() || namaBuku.isEmpty() || jumlahText.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Semua Kolom harus diisi");
        jTextIdBuku.requestFocus();
        return;
    }
    
    try {
        int xjml = Integer.parseInt(jumlahText);
        if (xjml < 0) {
            JOptionPane.showMessageDialog(null, "Jumlah harus lebih besar atau sama dengan nol");
            jTextJumlah.requestFocus();
            return;
        }
        
        Statement checkStmt = con.createStatement();
        ResultSet rs = checkStmt.executeQuery("SELECT * FROM tmasterbuku WHERE IdBuku='" + idBuku + "'");
        boolean isUpdate = rs.next();
        checkStmt.close();
        
        if (isUpdate) {
            updEksemplar(idBuku, xjml, TglNow);
        } else {
            // Insert data baru
            Statement insertStmt = con.createStatement();
            insertStmt.executeUpdate("INSERT INTO tmasterbuku (IdBuku, NamaBuku, Pengarang, Penerbit, Jumlah,  " +
                                   "DateCreate, DateModify, IdKategori, IdRak) VALUES ('" + idBuku + "', '" + 
                                   namaBuku + "', '" + jTextPengarang.getText() + "', '" + jTextPenerbit.getText() + "', " +
                                   xjml + ", '" + TglNow + "', '" + TglNow + "', '" + 
                                   idKategori + "', '" + idRak + "')");
            insertStmt.close();
            
            insEksemplar(idBuku, xjml);
        }
        
        JOptionPane.showMessageDialog(null, "Data buku berhasil " + (isUpdate ? "diupdate" : "ditambahkan"), 
            "Sukses", JOptionPane.INFORMATION_MESSAGE);
        datatojtable();
        Bersihkan();
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Jumlah harus berupa angka", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (SQLException err) {
        JOptionPane.showMessageDialog(this, "Error database: " + err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    triggerUpdate();
    }//GEN-LAST:event_jButtonSimpanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonHapus;
    private javax.swing.JButton jButtonSimpan;
    private javax.swing.JComboBox<String> jComboKategori;
    private javax.swing.JComboBox<String> jComboRak;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableBuku;
    private javax.swing.JTextField jTextIdBuku;
    private javax.swing.JTextField jTextJumlah;
    private javax.swing.JTextField jTextNamaBuku;
    private javax.swing.JTextField jTextPenerbit;
    private javax.swing.JTextField jTextPengarang;
    // End of variables declaration//GEN-END:variables
}
