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
public class JPanelMaintenanceKategoriAdmin extends javax.swing.JPanel {
    DefaultTableModel tb;
    Connection con=null;
    Statement stat;
    ResultSet res;
    PreparedStatement pst = null;
    String rool_id="";
    private String oldIdKategori = "";

    /**
     * Creates new form JPanelMaintenanceKategoriAdmin
     */
    public JPanelMaintenanceKategoriAdmin() {
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
        jTableKategori.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = jTableKategori.getColumnModel().getColumn(0);
        column.setPreferredWidth(75);
        column = jTableKategori.getColumnModel().getColumn(1);
        column.setPreferredWidth(200);
        column = jTableKategori.getColumnModel().getColumn(2);
        column.setPreferredWidth(75);
        column = jTableKategori.getColumnModel().getColumn(3);
        column.setPreferredWidth(75); 
    }
    
    private void datatojtable() {
        tb= new DefaultTableModel();
        tb.addColumn("Id Kategori");
        tb.addColumn("Nama Kategori");        
        tb.addColumn("Date Create");
        tb.addColumn("Date Modify");
        jTableKategori.setModel(tb);
        try{
            res=stat.executeQuery("select * from tkategori");
            while (res.next()) {
                tb.addRow(new Object[]{
                res.getString("IdKategori"),
                res.getString("NamaKategori"),
                res.getDate("DateCreate"),
                res.getDate("DateModify")
                });
            }
            Aturkolom();
        }catch (SQLException e){
        }  
    }
    
    public void bersihkantextfiled() {
        jTextIdKategori.setText("");
        jTextNamaKategori.setText("");
        jTextIdKategori.requestFocus();
    }
    
    private void cekdata() {
        try{
            if (jTextIdKategori.getText().length()  < 5) {
                JOptionPane.showMessageDialog(null, "Panjang Karakter Kurang dari 5 Digit");
                jTextIdKategori.requestFocus();
            } else {
                String sqlcek="select * from tkategori where IdKategori='"+ jTextIdKategori.getText() +"'";
                ResultSet rscek=stat.executeQuery(sqlcek);
                if (rscek.next()) {
                    JOptionPane.showMessageDialog(null, "Kategori ditemukan, anda bisa merubah dan menghapus datanya");
                    jTextIdKategori.setText(rscek.getString("IdKategori"));
                    oldIdKategori = rscek.getString("IdKategori");
                    jTextNamaKategori.setText(rscek.getString("NamaKategori"));
                    jTextIdKategori.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Id Kategori Tidak Di temukan, anda bisa memasukan datanya");
                    oldIdKategori = "";
                    jTextNamaKategori.requestFocus();
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
        
        if (!oldIdKategori.isEmpty()) {
            if (!oldIdKategori.equals(jTextIdKategori.getText())) {
                con.setAutoCommit(false);
                
                try {

                    Statement updateBooksStmt = con.createStatement();
                    updateBooksStmt.executeUpdate("UPDATE tmasterbuku SET IdKategori='" + jTextIdKategori.getText() + 
                                               "' WHERE IdKategori='" + oldIdKategori + "'");
                    

                    Statement updateRakStmt = con.createStatement();
                    String updateSql = "UPDATE tkategori SET IdKategori='" + jTextIdKategori.getText() + 
                                     "', NamaKategori='" + jTextNamaKategori.getText() + 
                                     "', DateModify='" + TglNow + 
                                     "' WHERE IdKategori='" + oldIdKategori + "'";
                    updateRakStmt.executeUpdate(updateSql);
                    

                    con.commit();
                    
                    JOptionPane.showMessageDialog(null, "Data berhasil diupdate termasuk ID Kategori", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    
                } catch (SQLException e) {
                    con.rollback();
                    throw e;
                } finally {
                    con.setAutoCommit(true);
                }
            } else {

                Statement updateStmt = con.createStatement();
                String updateSql = "UPDATE trak SET NamaKategori='" + jTextNamaKategori.getText() + 
                                 "', DateModify='" + TglNow + 
                                 "' WHERE IdKategori='" + oldIdKategori + "'";
                updateStmt.executeUpdate(updateSql);
                JOptionPane.showMessageDialog(null, "Data berhasil diupdate", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {

            Statement insertStmt = con.createStatement();
            String insertSql = "INSERT INTO trak VALUES('" + jTextIdKategori.getText() + 
                             "','" + jTextNamaKategori.getText() + 
                             "','" + TglNow + "','" + TglNow + "')";
            insertStmt.executeUpdate(insertSql);
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }
        

        datatojtable();
        bersihkantextfiled();
        oldIdKategori = "";
        
    } catch(SQLException err) {
        JOptionPane.showMessageDialog(this, "Operasi gagal\n" + err.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
    }
}
     
    public void hapus_data() {
    if(JOptionPane.showConfirmDialog(null, "Apakah Yakin Akan di Hapus?","Informasi",JOptionPane.INFORMATION_MESSAGE)==JOptionPane.OK_OPTION) {
        try {

            Statement checkStmt = con.createStatement();
            ResultSet rs = checkStmt.executeQuery("SELECT COUNT(*) FROM tmasterbuku WHERE IdKategori = '" + jTextIdKategori.getText() + "'");
            rs.next();
            int count = rs.getInt(1);
            
            if(count > 0) {

                int option = JOptionPane.showConfirmDialog(null, 
                    "Terdapat " + count + " buku yang menggunakan Kategori ini.\n" +
                    "Apakah Anda ingin mengosongkan data rak untuk buku-buku tersebut?\n" +
                    "Pilih 'Ya' untuk melanjutkan penghapusan, 'Tidak' untuk membatalkan.", 
                    "Peringatan", 
                    JOptionPane.YES_NO_OPTION);
                
                if(option == JOptionPane.YES_OPTION) {

                    con.setAutoCommit(false);
                    
                    try {

                        Statement updateStmt = con.createStatement();
                        updateStmt.executeUpdate("UPDATE tmasterbuku SET IdKategori = NULL WHERE IdKategori = '" + jTextIdKategori.getText() + "'");
                        
                        Statement deleteStmt = con.createStatement();
                        deleteStmt.executeUpdate("DELETE FROM tkategori WHERE IdKategori = '" + jTextIdKategori.getText() + "'");
                        
                        con.commit();
                        
                        JOptionPane.showMessageDialog(this, "Data Kategori berhasil dihapus dan referensi buku diupdate menjadi NULL", 
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
                Statement deleteStmt = con.createStatement();
                deleteStmt.executeUpdate("DELETE FROM tkategori WHERE IdKategori = '" + jTextIdKategori.getText() + "'");
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            
            bersihkantextfiled();
            datatojtable();
            
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(this, "Delete data gagal\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(null, "Id Kategori Batal Di Hapus");
        jTextIdKategori.requestFocus(); 
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
        jTextIdKategori = new javax.swing.JTextField();
        jTextNamaKategori = new javax.swing.JTextField();
        jButtonSimpan = new javax.swing.JButton();
        jButtonHapus = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableKategori = new javax.swing.JTable();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("MAINTENANCE KATEGORI");

        jLabel2.setText("ID KATEGORI");

        jLabel3.setText("NAMA KATEGORI");

        jTextIdKategori.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextIdKategoriKeyPressed(evt);
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

        jTableKategori.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTableKategori);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonSimpan)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonHapus))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextIdKategori)
                                .addComponent(jTextNamaKategori)))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextIdKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextNamaKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSimpan)
                    .addComponent(jButtonHapus))
                .addContainerGap(70, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSimpanActionPerformed
        // TODO add your handling code here:
        String idkategori=jTextIdKategori.getText();
        String namakategori=jTextNamaKategori.getText();
        if (idkategori.isEmpty() && namakategori.isEmpty() ) {
             JOptionPane.showMessageDialog(null, "Ada kolom yang masih kosong");
             jTextIdKategori.requestFocus();
        } else {
            if (idkategori.length() >= 5) {
                InserUpdate();
            } else {
               JOptionPane.showMessageDialog(null, "Panjang Karakter Kurang dari 5 Digit");
               jTextIdKategori.requestFocus();
            }
        }
          triggerUpdate();
    if (updateListener != null) {
        updateListener.run();
    }
    }//GEN-LAST:event_jButtonSimpanActionPerformed

    private void jTextIdKategoriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextIdKategoriKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode()==KeyEvent.VK_ENTER) {
            cekdata();
        } 
    }//GEN-LAST:event_jTextIdKategoriKeyPressed

    private void jButtonHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHapusActionPerformed
        // TODO add your handling code here:
        if (jTextIdKategori.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Id Kategori Tidak Boleh Kosong");
            jTextIdKategori.requestFocus();
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableKategori;
    private javax.swing.JTextField jTextIdKategori;
    private javax.swing.JTextField jTextNamaKategori;
    // End of variables declaration//GEN-END:variables
}
