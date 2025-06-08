/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import main.PanelUpdateListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Hp
 */
public class JPanelPeminjamanUser extends javax.swing.JPanel {
    private Runnable updateListener;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    DefaultTableModel tabelmodel; 
    Connection con=null; 
    Statement stat; 
    ResultSet res; 
    PreparedStatement pst = null; 
    String cekconeks; 
    
    /**
     * Creates new form JPanelPeminjamanUser
     */
    public JPanelPeminjamanUser() {
        initComponents();
        koneksi();
        datatojtable();
    }
    
    public void setUpdateListener(Runnable listener) {
    this.updateListener = listener;
}

private void triggerUpdate() {
    if (updateListener != null) {
        updateListener.run();
    }
}

public void refreshData() {
        datatojtable();

    }
    
    private void koneksi(){ 
            try { 
               Class.forName("com.mysql.cj.jdbc.Driver"); 
               con=DriverManager.getConnection("jdbc:mysql://localhost/dbperpustakaan", "root", ""); 
               stat=con.createStatement();
               } catch (ClassNotFoundException | SQLException e) { 
                   JOptionPane.showMessageDialog(null, e); 
               } 
               }
    
    
    public void datatojtable() 
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
tb.addColumn("Id Eksemplar"); 
tb.addColumn("Kode Eksemplar");
tb.addColumn("Status");
jTableBuku.setModel(tb); 
try{ 
// Mengambil data dari database
  res=stat.executeQuery("SELECT b.IdEksemplar, b.KodeEksemplar, b.Status, a.*, c.IdKategori, d.IdRak FROM tmasterbuku a JOIN teksemplar b ON a.IdBuku = b.IdBuku JOIN tkategori c ON a.IdKategori = c.IdKategori JOIN trak d ON a.IdRak = d.IdRak WHERE b.Status = 'true'GROUP BY b.IdEksemplar, b.KodeEksemplar ORDER BY SUBSTRING_INDEX(b.KodeEksemplar, '.', 1), CAST(SUBSTRING_INDEX(b.KodeEksemplar, '.', -1) AS UNSIGNED), b.IdEksemplar;"); 
        
 
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
            res.getString("IdRak"),
            res.getString("IdEksemplar"), 
            res.getString("KodeEksemplar"),
            res.getString("Status")
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
         column = jTableBuku.getColumnModel().getColumn(7); 
        column.setPreferredWidth(75); 
         column = jTableBuku.getColumnModel().getColumn(8); 
        column.setPreferredWidth(75); 
    } 

    private void pinjamBuku() {
    String idPeminjaman = jTextIdPeminjaman.getText().trim();
    String idAnggota = jTextIdAnggota.getText().trim();
    String namaAnggota = jTextNamaAnggota.getText().trim();
    String kodeEksemplar = jTextKodeEksemplar.getText().trim();
    String namaBuku = jTextJudulBuku.getText().trim();
    String tanggalPinjam = jTextTanggalPinjam.getText().trim();
    String tanggalJatuhTempo = jTextTanggalJatuhTempo.getText().trim();
    
    // Validasi input
    if (idPeminjaman.isEmpty() || idAnggota.isEmpty() || namaAnggota.isEmpty() || kodeEksemplar.isEmpty() || 
        namaBuku.isEmpty() || tanggalPinjam.isEmpty() || tanggalJatuhTempo.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    } 
    
    try {
        // 2. Cek status eksemplar buku
        if (!cekStatusEksemplar(kodeEksemplar)) {
            JOptionPane.showMessageDialog(this, "Buku tidak tersedia untuk dipinjam", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // 3. Proses peminjaman
        String sql = "INSERT INTO tpeminjaman (IdPeminjaman, IdAnggota, NamaAnggota, KodeEksemplar, NamaBuku, TanggalPinjam, TanggalJatuhTempo) VALUES (?,?, ?, ?, ?, ?, ?)";
        pst = con.prepareStatement(sql);
        pst.setString(1, idPeminjaman);
        pst.setString(2, idAnggota);
        pst.setString(3, namaAnggota);
        pst.setString(4, kodeEksemplar);
        pst.setString(5, namaBuku);
        pst.setString(6, tanggalPinjam);
        pst.setString(7, tanggalJatuhTempo);
        
        
        int rowsAffected = pst.executeUpdate();
        
        if (rowsAffected > 0) {
            // Update status eksemplar menjadi false (tidak tersedia)
            updateStatusEksemplar(kodeEksemplar, false);
            
            JOptionPane.showMessageDialog(this, "Peminjaman berhasil!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            datatojtable();
            triggerUpdate();
            return;
        } else {
            JOptionPane.showMessageDialog(this, "Peminjaman gagal", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    } finally {
        try {
            if (pst != null) pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    
    
// Method untuk mengecek status eksemplar
private boolean cekStatusEksemplar(String kodeEksemplar) throws SQLException {
    String sql = "SELECT Status FROM teksemplar WHERE KodeEksemplar = ?";
    pst = con.prepareStatement(sql);
    pst.setString(1, kodeEksemplar);
    res = pst.executeQuery();
    
    if (res.next()) {
        return "true".equals(res.getString("Status"));
    }
    
    return false;
}

// Method untuk mengupdate status eksemplar
private void updateStatusEksemplar(String kodeEksemplar, boolean status) throws SQLException {
    String sql = "UPDATE teksemplar SET Status = ? WHERE KodeEksemplar = ?";
    pst = con.prepareStatement(sql);
    pst.setString(1, status ? "true" : "false");
    pst.setString(2, kodeEksemplar);
    pst.executeUpdate();
}

// Method untuk membersihkan field input
private void clearFields() {
    jTextIdPeminjaman.setText("");
    jTextIdAnggota.setText("");
    jTextNamaAnggota.setText("");
    jTextKodeEksemplar.setText("");
    jTextJudulBuku.setText("");
    jTextTanggalPinjam.setText("");
    jTextTanggalJatuhTempo.setText("");
}

private void cekdatauser() {
    
    // Definisikan formatter di level class atau di sini jika hanya digunakan di method ini
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime dueDateTime = now.plusWeeks(1);

    try {
        // Gunakan PreparedStatement untuk menghindari SQL injection
        String sqlcek = "SELECT Nama FROM tanggota WHERE IdAnggota = ?";
        pst = con.prepareStatement(sqlcek);
        pst.setString(1, jTextIdAnggota.getText().trim());
        ResultSet rscek = pst.executeQuery();
        
        if (rscek.next()) {
            jTextNamaAnggota.setText(rscek.getString("Nama"));
            jTextTanggalPinjam.setText(now.format(formatter));
            jTextTanggalJatuhTempo.setText(dueDateTime.format(formatter));
            jTextKodeEksemplar.requestFocus();
        } else {
            JOptionPane.showMessageDialog(this, 
                "ID Anggota tidak ditemukan", 
                "Peringatan", 
                JOptionPane.WARNING_MESSAGE);
            clearFields();
            jTextIdAnggota.requestFocus();
        }
    } catch(SQLException e) {
        JOptionPane.showMessageDialog(this, 
            "Error saat mengakses database: " + e.getMessage(), 
            "Database Error", 
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    } finally {
        // Pastikan untuk menutup resources
        try {
            if (pst != null) pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

private String generateIdPeminjaman(String idAnggota, String kodeEksemplar) {
    // Format: IDAnggota-KodeEksemplar-Timestamp (contoh: AGT001-BUK.001-20240530)
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    String timestamp = LocalDate.now().format(dateFormatter);
    
    String cleanKode = kodeEksemplar.replace(".", "").replace(" ", "");
    
    return idAnggota + "-" + cleanKode + "-" + timestamp;
}

private void cekdatabuku() {
    String idAnggota = jTextIdAnggota.getText().trim();
    String kodeEksemplar = jTextKodeEksemplar.getText().trim();
    try {
        String sqlcek = "SELECT b.NamaBuku " +
                       "FROM teksemplar e " +
                       "JOIN tmasterbuku b ON e.IdBuku = b.IdBuku " +
                       "WHERE e.KodeEksemplar = ?";
        
        pst = con.prepareStatement(sqlcek);
        pst.setString(1, jTextKodeEksemplar.getText().trim());
        
        ResultSet rscek = pst.executeQuery();
        
        if (rscek.next()) {
            jTextJudulBuku.setText(rscek.getString("NamaBuku"));
            jTextIdPeminjaman.setText(generateIdPeminjaman(idAnggota, kodeEksemplar));
        } else {
            JOptionPane.showMessageDialog(this, 
                "Kode Eksemplar tidak valid/tidak ditemukan",
                "Peringatan",
                JOptionPane.WARNING_MESSAGE);
            clearFields();
        }
    } catch(SQLException e) {
        JOptionPane.showMessageDialog(this,
            "Error saat mengakses data buku: " + e.getMessage(),
            "Database Error",
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    } finally {
        try {
            if (pst != null) pst.close();
        } catch (SQLException e) {
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButtonPinjam = new javax.swing.JButton();
        jTextIdAnggota = new javax.swing.JTextField();
        jTextKodeEksemplar = new javax.swing.JTextField();
        jTextJudulBuku = new javax.swing.JTextField();
        jTextTanggalPinjam = new javax.swing.JTextField();
        jTextTanggalJatuhTempo = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableBuku = new javax.swing.JTable();
        jTextNamaAnggota = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextIdPeminjaman = new javax.swing.JTextField();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("PEMINJAMAN USER");

        jLabel2.setText("ID ANGGOTA");

        jLabel3.setText("NAMA ANGGOTA");

        jLabel4.setText("KODE EKSEMPLAR");

        jLabel5.setText("JUDUL BUKU");

        jLabel6.setText("TANGGAL PINJAM");

        jLabel7.setText("TANGGAL JATUH TEMPO");

        jButtonPinjam.setText("PINJAM");
        jButtonPinjam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPinjamActionPerformed(evt);
            }
        });

        jTextIdAnggota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextIdAnggotaKeyPressed(evt);
            }
        });

        jTextKodeEksemplar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextKodeEksemplarKeyPressed(evt);
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

        jLabel8.setText("ID PEMINJAMAN");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextIdAnggota)
                            .addComponent(jTextKodeEksemplar)
                            .addComponent(jTextJudulBuku)
                            .addComponent(jTextTanggalPinjam)
                            .addComponent(jTextTanggalJatuhTempo)
                            .addComponent(jTextNamaAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextIdPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButtonPinjam)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextIdPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextIdAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextNamaAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextKodeEksemplar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextJudulBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextTanggalPinjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextTanggalJatuhTempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonPinjam)
                .addContainerGap(77, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonPinjamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPinjamActionPerformed
        // TODO add your handling code here:
        pinjamBuku();
    }//GEN-LAST:event_jButtonPinjamActionPerformed

    private void jTextIdAnggotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextIdAnggotaKeyPressed
        // TODO add your handling code here:
                if (evt.getKeyCode()==KeyEvent.VK_ENTER) {
                    cekdatauser();
        }
    }//GEN-LAST:event_jTextIdAnggotaKeyPressed

    private void jTextKodeEksemplarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextKodeEksemplarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode()==KeyEvent.VK_ENTER) {
            cekdatabuku();
        }
    }//GEN-LAST:event_jTextKodeEksemplarKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonPinjam;
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
    private javax.swing.JTextField jTextIdAnggota;
    private javax.swing.JTextField jTextIdPeminjaman;
    private javax.swing.JTextField jTextJudulBuku;
    private javax.swing.JTextField jTextKodeEksemplar;
    private javax.swing.JTextField jTextNamaAnggota;
    private javax.swing.JTextField jTextTanggalJatuhTempo;
    private javax.swing.JTextField jTextTanggalPinjam;
    // End of variables declaration//GEN-END:variables
}
