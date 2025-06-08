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
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import main.PanelUpdateListener;

/**
 *
 * @author Hp
 */
public class JPanelPengembalianUser extends javax.swing.JPanel {
private Runnable updateListener;
    DefaultTableModel tabelmodel; 
    Connection con=null; 
    Statement stat; 
    ResultSet res; 
    PreparedStatement pst = null; 
    String cekconeks; 
    
    /**
     * Creates new form JPanelPengembalianUser
     */
    public JPanelPengembalianUser() {
        initComponents();
        koneksi();
    }
    
    public void setUpdateListener(Runnable listener) {
    this.updateListener = listener;
}

private void triggerUpdate() {
    if (updateListener != null) {
        updateListener.run();
    }
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

    // Method untuk mengembalikan buku
private void kembalikanBuku() {
    String idPeminjaman = jTextIdPeminjaman.getText().trim();
    String kodeEksemplar = jTextKodeEksemplar.getText().trim();
    
    // Validasi input
    if (idPeminjaman.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Kode eksemplar dan ID anggota harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    try {
        
        // 2. Update status peminjaman
        String sqlUpdatePeminjaman = "DELETE FROM tpeminjaman WHERE IdPeminjaman = ?";
pst = con.prepareStatement(sqlUpdatePeminjaman);
pst.setString(1, idPeminjaman);
int rowsAffected = pst.executeUpdate();
        
        if (rowsAffected > 0) {
            // 3. Update status eksemplar menjadi tersedia
            updateStatusEksemplar(kodeEksemplar, true);
            
            JOptionPane.showMessageDialog(this, "Pengembalian buku berhasil!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            triggerUpdate();
        } else {
            JOptionPane.showMessageDialog(this, "Pengembalian buku gagal", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }
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
    jTextNamaBuku.setText("");
    jTextTanggalPinjam.setText("");
    jTextTanggalJatuhTempo.setText("");
}

private void cekdata() {
    String idPeminjaman = jTextIdPeminjaman.getText().trim();
    
    if (idPeminjaman.isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "ID Peminjaman harus diisi", 
            "Peringatan", 
            JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        // Query untuk mengecek data peminjaman
        String sql = "SELECT a.IdAnggota, a.NamaAnggota, a.KodeEksemplar, " +
                    "a.NamaBuku, a.TanggalPinjam, a.TanggalJatuhTempo, b.Status " +
                    "FROM tpeminjaman a, teksemplar b " +
                    "WHERE a.IdPeminjaman = ? " +
                    "AND b.Status = 'false'"; // Hanya peminjaman aktif
        
        pst = con.prepareStatement(sql);
        pst.setString(1, idPeminjaman);
        res = pst.executeQuery();
        
        if (res.next()) {
            // Jika data peminjaman ditemukan
            jTextIdAnggota.setText(res.getString("IdAnggota"));
            jTextNamaAnggota.setText(res.getString("NamaAnggota"));
            jTextKodeEksemplar.setText(res.getString("KodeEksemplar"));
            jTextNamaBuku.setText(res.getString("NamaBuku"));
            jTextTanggalPinjam.setText(res.getString("TanggalPinjam"));
            jTextTanggalJatuhTempo.setText(res.getString("TanggalJatuhTempo"));
            jButton1.requestFocus();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Data peminjaman tidak ditemukan atau buku sudah dikembalikan", 
                "Informasi", 
                JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, 
            "Error saat mengecek data peminjaman: " + ex.getMessage(), 
            "Database Error", 
            JOptionPane.ERROR_MESSAGE);
    } finally {
        try {
            if (pst != null) pst.close();
            if (res != null) res.close();
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
        jTextIdPeminjaman = new javax.swing.JTextField();
        jTextKodeEksemplar = new javax.swing.JTextField();
        jTextNamaAnggota = new javax.swing.JTextField();
        jTextTanggalPinjam = new javax.swing.JTextField();
        jTextTanggalJatuhTempo = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextNamaBuku = new javax.swing.JTextField();
        jTextIdAnggota = new javax.swing.JTextField();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("PENGEMBALIAN USER");

        jLabel2.setText("ID PEMINJAMAN");

        jLabel3.setText("ID ANGGOTA");

        jLabel4.setText("NAMA ANGGOTA");

        jLabel5.setText("TANGGAL PINJAM");

        jLabel6.setText("TANGGAL JATUH TEMPO");

        jTextIdPeminjaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextIdPeminjamanActionPerformed(evt);
            }
        });
        jTextIdPeminjaman.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextIdPeminjamanKeyPressed(evt);
            }
        });

        jButton1.setText("KEMBALIKAN");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel7.setText("KODE EKSEMPLAR");

        jLabel8.setText("NAMA BUKU");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextTanggalJatuhTempo)
                            .addComponent(jTextTanggalPinjam)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextKodeEksemplar, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                            .addComponent(jTextIdAnggota)
                            .addComponent(jTextIdPeminjaman)
                            .addComponent(jTextNamaBuku)
                            .addComponent(jTextNamaAnggota))))
                .addContainerGap(95, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextIdPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextIdAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextNamaAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextKodeEksemplar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextNamaBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextTanggalPinjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextTanggalJatuhTempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(185, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        kembalikanBuku();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextIdPeminjamanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextIdPeminjamanKeyPressed
        // TODO add your handling code here:
                if (evt.getKeyCode()==KeyEvent.VK_ENTER) {
                    cekdata();
        }
    }//GEN-LAST:event_jTextIdPeminjamanKeyPressed

    private void jTextIdPeminjamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextIdPeminjamanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextIdPeminjamanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField jTextIdAnggota;
    private javax.swing.JTextField jTextIdPeminjaman;
    private javax.swing.JTextField jTextKodeEksemplar;
    private javax.swing.JTextField jTextNamaAnggota;
    private javax.swing.JTextField jTextNamaBuku;
    private javax.swing.JTextField jTextTanggalJatuhTempo;
    private javax.swing.JTextField jTextTanggalPinjam;
    // End of variables declaration//GEN-END:variables
}
