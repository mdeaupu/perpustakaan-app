/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane; 
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.sql.PreparedStatement;

/**
 *
 * @author Hp
 */

public class JFrameMaintenanceAnggota extends javax.swing.JFrame {
    DefaultTableModel tb;
    Connection con=null;
    Statement stat;
    ResultSet res;

    /**
     * Creates new form JFrameMaintenanceUser
     */
    
    public JFrameMaintenanceAnggota() {
        initComponents();
        koneksi();
        datatojtable();        
    }
    
    private void koneksi(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost/dbperpustakaan", "root", "");
            stat=con.createStatement();
            System.out.println("koneksi berhasil;");
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            System.out.println("koneksi gagal;");
        }
    }

    private void Aturkolom() {
    TableColumn column;
    jTable_anggota.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

    column = jTable_anggota.getColumnModel().getColumn(0);
    column.setPreferredWidth(100);

    column = jTable_anggota.getColumnModel().getColumn(1);
    column.setPreferredWidth(120);

    column = jTable_anggota.getColumnModel().getColumn(2);
    column.setPreferredWidth(120);

    column = jTable_anggota.getColumnModel().getColumn(3);
    column.setPreferredWidth(80);

    column = jTable_anggota.getColumnModel().getColumn(4);
    column.setPreferredWidth(120);

    column = jTable_anggota.getColumnModel().getColumn(5);
    column.setPreferredWidth(150);

    column = jTable_anggota.getColumnModel().getColumn(6);
    column.setPreferredWidth(150);
    
    column = jTable_anggota.getColumnModel().getColumn(7);
    column.setPreferredWidth(120);

    column = jTable_anggota.getColumnModel().getColumn(8);
    column.setPreferredWidth(150);

    column = jTable_anggota.getColumnModel().getColumn(9);
    column.setPreferredWidth(150);
    }
    
    private void datatojtable() {
        tb= new DefaultTableModel();
        tb.addColumn("Id Anggota");
        tb.addColumn("NIM");
        tb.addColumn("Nama");
        tb.addColumn("Program Studi");
        tb.addColumn("Semester");
        tb.addColumn("Nomor Telepon");
        tb.addColumn("Email");
        tb.addColumn("Alamat");
        tb.addColumn("Date Create");
        tb.addColumn("Date Modify");
        jTable_anggota.setModel(tb);
        Aturkolom();
        try{
            res=stat.executeQuery("select * from tanggota");
            while (res.next()) {
                tb.addRow(new Object[]{
                    res.getString("IdAnggota"),
                    res.getString("Nim"),
                    res.getString("Nama"),
                    res.getString("Prodi"),
                    res.getString("Semester"),
                    res.getString("NoTelepon"),
                    res.getString("Email"),
                    res.getString("Alamat"),
                    res.getTimestamp("DateCreate"),
                    res.getTimestamp("DateModify")
                });
            }
        }catch (SQLException e){
        }  
    }
    
    
    private void bersihkantextfiled() {
        txt_idanggota.setText("");
        txt_nim.setText("");
        txt_alamat.setText("");
        txt_email.setText("");
        txt_nama.setText("");
        txt_nomortelepon.setText("");
        txt_prodi.setText("");
        txt_semester.setText("");
        txt_idanggota.requestFocus();
    }
    
    private void cekdatauser() {
        try{
            if (txt_idanggota.getText().length() < 1) {
                JOptionPane.showMessageDialog(null, "Panjang Karakter Kurang dari 1 Digit");
                txt_idanggota.requestFocus();
            } else {
                String sqlcek="select * from tanggota where IdAnggota='"+ txt_idanggota.getText() +"'";
                ResultSet rscek=stat.executeQuery(sqlcek);
                if (rscek.next()) {
                    txt_nim.setText(rscek.getString("Username"));
                    txt_nama.setText(rscek.getString("Nama"));
                    txt_prodi.setText(rscek.getString("Prodi"));
                    txt_semester.setText(rscek.getString("Semester"));
                    txt_nomortelepon.setText(rscek.getString("NoTelepon"));
                    txt_email.setText(rscek.getString("Email"));
                    txt_alamat.setText(rscek.getString("Alamat"));
                    txt_nim.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Id Anggota Tidak Di temukan");
                    txt_nim.setText("");
                    txt_nama.setText("");
                    txt_prodi.setText("");
                    txt_semester.setText("");
                    txt_nomortelepon.setText("");
                    txt_email.setText("");
                    txt_alamat.setText("");
                    txt_nim.requestFocus();
                }
            }
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null,"GAGAL KETEMU");
        }     
    }
    
    public void InserUpdate() {
        try{
            Statement strU=con.createStatement();
            Statement strI=con.createStatement();
            Statement str =con.createStatement();          
            String sqlu="select * from tanggota where IdAnggota='"+ txt_idanggota.getText() +"'";
            ResultSet rsu=str.executeQuery(sqlu);
            java.util.Date tanggal=new java.util.Date();
            java.text.SimpleDateFormat setTanggal=new java.text.SimpleDateFormat("yyyy-MM-dd");
            String TglNow=setTanggal.format(tanggal);
            if (rsu.next()) {
                String SqlU = "UPDATE tanggota SET Nim = '" + txt_nim.getText() + "', Nama = '" + txt_nama.getText() + "', Prodi = '" + txt_prodi.getText() + "', Semester = " + txt_semester.getText() + ", NoTelepon = '" + txt_nomortelepon.getText() + "', Email = '" + txt_email.getText() + "', Alamat = '" + txt_alamat.getText() + "', DateModify = '" + TglNow + "' WHERE IdAnggota = " + txt_idanggota.getText();
                strU.executeUpdate(SqlU);
                JOptionPane.showMessageDialog(null, "Data Sudah Di Ubah","Insert",JOptionPane.INFORMATION_MESSAGE);
                datatojtable();
                bersihkantextfiled();
            } else {
                String SqlI = "INSERT INTO tanggota (IdAnggota, Nim, Nama, Prodi, Semester, NoTelepon, Email, Alamat, DateCreate, DateModify) VALUES ('" + txt_idanggota.getText() + "', '" + txt_nim.getText() + "', '" + txt_nama.getText() + "', '" + txt_prodi.getText() + "', " + txt_semester.getText() + ", '" + txt_nomortelepon.getText() + "', '" + txt_email.getText() + "', '" + txt_alamat.getText() + "', '" + TglNow + "', '" + TglNow + "')";
                strI.executeUpdate(SqlI);
                JOptionPane.showMessageDialog(null, "Data Sudah Di Simpan","Insert",JOptionPane.INFORMATION_MESSAGE);
                datatojtable();
                bersihkantextfiled();
            }
        } catch(SQLException err) {
            JOptionPane.showMessageDialog(this, "Koneksi Gagal\n"+err.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void hapus_data() {
        if(JOptionPane.showConfirmDialog(null, "Apakah Yakin Akan di Hapus ?","Informasi",JOptionPane.INFORMATION_MESSAGE)==JOptionPane.OK_OPTION) {
            try{
                Statement SqlDel;
                SqlDel = con.createStatement();
                SqlDel.executeUpdate("delete from tanggota where IdAnggota='"+  txt_idanggota.getText()+"'");   
                JOptionPane.showMessageDialog(this,"Data berhasil Di Hapus","Success",JOptionPane.INFORMATION_MESSAGE);
                bersihkantextfiled();
                datatojtable();
            } catch(SQLException e) {
                JOptionPane.showMessageDialog(this,"Delete data gagal\n"+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Kode User Batal Di Hapus");
            txt_idanggota.requestFocus(); 
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
        txt_idanggota = new javax.swing.JTextField();
        txt_nim = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_anggota = new javax.swing.JTable();
        btn_simpan = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        cmd_keluar = new javax.swing.JButton();
        txt_nama = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_prodi = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_semester = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_nomortelepon = new javax.swing.JTextField();
        txt_email = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txt_alamat = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("MAINTENANCE ANGGOTA");

        jLabel2.setText("Id Anggota");

        jLabel3.setText("NIM");

        txt_idanggota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_idanggotaKeyPressed(evt);
            }
        });

        jTable_anggota.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable_anggota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_anggotaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_anggota);

        btn_simpan.setBackground(new java.awt.Color(0, 102, 255));
        btn_simpan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_simpan.setForeground(new java.awt.Color(255, 255, 255));
        btn_simpan.setText("SIMPAN");
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });

        btn_hapus.setBackground(new java.awt.Color(255, 0, 0));
        btn_hapus.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_hapus.setForeground(new java.awt.Color(255, 255, 255));
        btn_hapus.setText("HAPUS");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });

        cmd_keluar.setBackground(new java.awt.Color(0, 0, 0));
        cmd_keluar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmd_keluar.setForeground(new java.awt.Color(255, 255, 255));
        cmd_keluar.setText("KELUAR");
        cmd_keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd_keluarActionPerformed(evt);
            }
        });

        jLabel7.setText("Nama");

        jLabel8.setText("Program Studi");

        jLabel9.setText("Semester");

        jLabel10.setText("Nomor Telepon");

        jLabel11.setText("Email");

        jLabel12.setText("Alamat");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(btn_simpan)
                            .addGap(18, 18, 18)
                            .addComponent(btn_hapus)
                            .addGap(141, 141, 141)
                            .addComponent(cmd_keluar))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txt_idanggota, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_nim, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_prodi, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_semester, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_nomortelepon, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addComponent(txt_alamat))))
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_idanggota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_nim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txt_prodi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txt_semester, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txt_nomortelepon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txt_alamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_simpan)
                    .addComponent(btn_hapus)
                    .addComponent(cmd_keluar))
                .addGap(38, 38, 38))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        // TODO add your handling code here:
        String kodeuser=txt_idanggota.getText();
        String namauser=txt_nim.getText();

        if (namauser.isEmpty() && kodeuser.isEmpty() ) {
             JOptionPane.showMessageDialog(null, "Ada kolom yang masih kosong");
             txt_idanggota.requestFocus();
        } else {
            if (kodeuser.length()>=1) {
                InserUpdate();
            } else {
               JOptionPane.showMessageDialog(null, "Panjang Karakter Kurang dari 1 Digit");
               txt_idanggota.requestFocus();
            }
        }
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        // TODO add your handling code here:
        if (txt_idanggota.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Kode User Tidak Boleh Kosong");
            txt_idanggota.requestFocus();
        } else {
           hapus_data();
        }
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void cmd_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd_keluarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_cmd_keluarActionPerformed

    private void txt_idanggotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_idanggotaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode()==KeyEvent.VK_ENTER) {
            cekdatauser();
        } 

    }//GEN-LAST:event_txt_idanggotaKeyPressed

    private void jTable_anggotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_anggotaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable_anggotaMouseClicked

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
            java.util.logging.Logger.getLogger(JFrameMaintenanceAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameMaintenanceAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameMaintenanceAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameMaintenanceAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrameMaintenanceAnggota().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton cmd_keluar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_anggota;
    private javax.swing.JTextField txt_alamat;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_idanggota;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_nim;
    private javax.swing.JTextField txt_nomortelepon;
    private javax.swing.JTextField txt_prodi;
    private javax.swing.JTextField txt_semester;
    // End of variables declaration//GEN-END:variables
}
