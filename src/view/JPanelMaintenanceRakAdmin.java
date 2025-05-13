/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Hp
 */
public class JPanelMaintenanceRakAdmin extends javax.swing.JPanel {
        DefaultTableModel tb;
    Connection con=null;
    Statement stat;
    ResultSet res;
    PreparedStatement pst = null;
    String rool_id="";
    private String oldIdRak = "";

    /**
     * Creates new form JPanelMaintenanceRakAdmin
     */
    public JPanelMaintenanceRakAdmin() {
        initComponents();
        koneksi();
        datatojtable();  
    }
    
    private Runnable updateListener;
    
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
    
    private void Aturkolom() {
        TableColumn column;
        jTableRak.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = jTableRak.getColumnModel().getColumn(0);
        column.setPreferredWidth(75);
        column = jTableRak.getColumnModel().getColumn(1);
        column.setPreferredWidth(200);
        column = jTableRak.getColumnModel().getColumn(1);
        column.setPreferredWidth(200);
        column = jTableRak.getColumnModel().getColumn(2);
        column.setPreferredWidth(75);
        column = jTableRak.getColumnModel().getColumn(3);
        column.setPreferredWidth(75); 
    }
    
    private void datatojtable() {
        tb= new DefaultTableModel();
        tb.addColumn("Id Rak");
        tb.addColumn("Nama Rak");        
        tb.addColumn("Lokasi Rak");
        tb.addColumn("Date Create");
        tb.addColumn("Date Modify");
        jTableRak.setModel(tb);
        try{
            res=stat.executeQuery("select * from trak");
            while (res.next()) {
                tb.addRow(new Object[]{
                res.getString("IdRak"),
                res.getString("NamaRak"),
                res.getString("Lokasi"),
                res.getDate("DateCreate"),
                res.getDate("DateModify")
                });
            }
            Aturkolom();
        }catch (SQLException e){
        }  
    }
    
    public void bersihkantextfiled() {
        jTextIdRak.setText("");
        jTextNamaRak.setText("");
        jTextLokasi.setText("");
        jTextIdRak.requestFocus();
    }
    
    private void cekdata() {
        try{
            if (jTextIdRak.getText().length()  < 3) {
                JOptionPane.showMessageDialog(null, "Panjang Karakter Kurang dari 3 Digit");
                jTextIdRak.requestFocus();
            } else {
                String sqlcek="select * from trak where IdRak='"+ jTextIdRak.getText() +"'";
                ResultSet rscek=stat.executeQuery(sqlcek);
                if (rscek.next()) {
                    JOptionPane.showMessageDialog(null, "Kategori ditemukan, anda bisa merubah dan menghapus datanya");
                    jTextIdRak.setText(rscek.getString("IdRak"));
                    oldIdRak = rscek.getString("IdRak");
                    jTextNamaRak.setText(rscek.getString("NamaRak"));
                    jTextLokasi.setText(rscek.getString("Lokasi"));
                    jTextIdRak.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Id Rak Tidak Di temukan, anda bisa memasukan datanya");
                    oldIdRak = "";
                    jTextNamaRak.requestFocus();
                }
            }
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null,"GAGAL KETEMU");
        }     
    }
    
    public void InserUpdate() {
    try {
        java.util.Date tanggal = new java.util.Date();
        java.text.SimpleDateFormat setTanggal = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String TglNow = setTanggal.format(tanggal);
        
        if (!oldIdRak.isEmpty()) {
            if (!oldIdRak.equals(jTextIdRak.getText())) {

                con.setAutoCommit(false);
                
                try {
                    Statement updateBooksStmt = con.createStatement();
                    updateBooksStmt.executeUpdate("UPDATE tmasterbuku SET IdRak='" + jTextIdRak.getText() + 
                                               "' WHERE IdRak='" + oldIdRak + "'");
                    
                    Statement updateRakStmt = con.createStatement();
                    String updateSql = "UPDATE trak SET IdRak='" + jTextIdRak.getText() + 
                                     "', NamaRak='" + jTextNamaRak.getText() + 
                                     "', Lokasi='" + jTextLokasi.getText() + 
                                     "', DateModify='" + TglNow + 
                                     "' WHERE IdRak='" + oldIdRak + "'";
                    updateRakStmt.executeUpdate(updateSql);
                    
                    con.commit();
                    
                    JOptionPane.showMessageDialog(null, "Data berhasil diupdate termasuk ID Rak", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    
                } catch (SQLException e) {
                    con.rollback();
                    throw e;
                } finally {
                    con.setAutoCommit(true);
                }
            } else {
                Statement updateStmt = con.createStatement();
                String updateSql = "UPDATE trak SET NamaRak='" + jTextNamaRak.getText() + 
                                 "', Lokasi='" + jTextLokasi.getText() + 
                                 "', DateModify='" + TglNow + 
                                 "' WHERE IdRak='" + oldIdRak + "'";
                updateStmt.executeUpdate(updateSql);
                JOptionPane.showMessageDialog(null, "Data berhasil diupdate", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            Statement insertStmt = con.createStatement();
            String insertSql = "INSERT INTO trak VALUES('" + jTextIdRak.getText() + 
                             "','" + jTextNamaRak.getText() + 
                             "','" + jTextLokasi.getText() + 
                             "','" + TglNow + "','" + TglNow + "')";
            insertStmt.executeUpdate(insertSql);
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }
        
        datatojtable();
        bersihkantextfiled();
        oldIdRak = "";
        
    } catch(SQLException err) {
        JOptionPane.showMessageDialog(this, "Operasi gagal\n" + err.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
    public void hapus_data() {
    if(JOptionPane.showConfirmDialog(null, "Apakah Yakin Akan di Hapus?","Informasi",JOptionPane.INFORMATION_MESSAGE)==JOptionPane.OK_OPTION) {
        try {
            Statement checkStmt = con.createStatement();
            ResultSet rs = checkStmt.executeQuery("SELECT COUNT(*) FROM tmasterbuku WHERE IdRak = '" + jTextIdRak.getText() + "'");
            rs.next();
            int count = rs.getInt(1);
            
            if(count > 0) {
                int option = JOptionPane.showConfirmDialog(null, 
                    "Terdapat " + count + " buku yang menggunakan rak ini.\n" +
                    "Apakah Anda ingin mengosongkan data rak untuk buku-buku tersebut?\n" +
                    "Pilih 'Ya' untuk melanjutkan penghapusan, 'Tidak' untuk membatalkan.", 
                    "Peringatan", 
                    JOptionPane.YES_NO_OPTION);
                
                if(option == JOptionPane.YES_OPTION) {
                    con.setAutoCommit(false);
                    
                    try {
                        Statement updateStmt = con.createStatement();
                        updateStmt.executeUpdate("UPDATE tmasterbuku SET IdRak = NULL WHERE IdRak = '" + jTextIdRak.getText() + "'");
                        
                        Statement deleteStmt = con.createStatement();
                        deleteStmt.executeUpdate("DELETE FROM trak WHERE IdRak = '" + jTextIdRak.getText() + "'");

                        con.commit();
                        
                        JOptionPane.showMessageDialog(this, "Data rak berhasil dihapus dan referensi buku diupdate menjadi NULL", 
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                        
                    } catch(SQLException e) {
                        con.rollback();
                        throw e;
                    } finally {
                        con.setAutoCommit(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Penghapusan dibatalkan", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            } else {
                // Jika tidak ada buku yang menggunakan rak ini, langsung hapus
                Statement deleteStmt = con.createStatement();
                deleteStmt.executeUpdate("DELETE FROM trak WHERE IdRak = '" + jTextIdRak.getText() + "'");
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            
            bersihkantextfiled();
            datatojtable();
            
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(this, "Delete data gagal\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(null, "ID Rak Batal Di Hapus");
        jTextIdRak.requestFocus(); 
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
        jTextIdRak = new javax.swing.JTextField();
        jTextNamaRak = new javax.swing.JTextField();
        jTextLokasi = new javax.swing.JTextField();
        jButtonSimpan = new javax.swing.JButton();
        jButtonHapus = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableRak = new javax.swing.JTable();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("MAINTENANCE RAK");

        jLabel2.setText("ID RAK");

        jLabel3.setText("NAMA RAK");

        jLabel4.setText("LOKASI");

        jTextIdRak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextIdRakKeyPressed(evt);
            }
        });

        jButtonSimpan.setBackground(new java.awt.Color(0, 51, 255));
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

        jTableRak.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableRak);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonSimpan)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonHapus))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextNamaRak)
                            .addComponent(jTextLokasi, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextIdRak, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextIdRak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextNamaRak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextLokasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSimpan)
                    .addComponent(jButtonHapus))
                .addContainerGap(80, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSimpanActionPerformed
        // TODO add your handling code here:
        String idrak=jTextIdRak.getText();
        String namarak=jTextNamaRak.getText();
        String lokasirak=jTextLokasi.getText();
        if (idrak.isEmpty() && namarak.isEmpty() && lokasirak.isEmpty()) {
             JOptionPane.showMessageDialog(null, "Ada kolom yang masih kosong");
             jTextIdRak.requestFocus();
        } else {
            if (idrak.length() >= 3) {
                InserUpdate();
            } else {
               JOptionPane.showMessageDialog(null, "Panjang Karakter Kurang dari 8 Digit");
               jTextIdRak.requestFocus();
            }
        }
         triggerUpdate();
    if (updateListener != null) {
        updateListener.run();
    }
    }//GEN-LAST:event_jButtonSimpanActionPerformed

    private void jTextIdRakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextIdRakKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode()==KeyEvent.VK_ENTER) {
            cekdata();
        } 
    }//GEN-LAST:event_jTextIdRakKeyPressed

    private void jButtonHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHapusActionPerformed
        // TODO add your handling code here:
        if (jTextIdRak.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Kode User Tidak Boleh Kosong");
            jTextIdRak.requestFocus();
        } else {
           hapus_data();
        }
         triggerUpdate();
    if (updateListener != null) {
        updateListener.run();
    }
    }//GEN-LAST:event_jButtonHapusActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonHapus;
    private javax.swing.JButton jButtonSimpan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableRak;
    private javax.swing.JTextField jTextIdRak;
    private javax.swing.JTextField jTextLokasi;
    private javax.swing.JTextField jTextNamaRak;
    // End of variables declaration//GEN-END:variables
}
