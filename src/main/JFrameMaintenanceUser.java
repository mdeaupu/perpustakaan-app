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

public class JFrameMaintenanceUser extends javax.swing.JFrame {
    DefaultTableModel tb;
    Connection con=null;
    Statement stat;
    ResultSet res;
    PreparedStatement pst = null;
    String rool_id="";

    /**
     * Creates new form JFrameMaintenanceUser
     */
    
    public JFrameMaintenanceUser() {
        initComponents();
        koneksi();
        datatojtable();        
        dataToComboBox();
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
    jTable_user.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

    // Assuming jTable_user has the following columns based on datatojtable():
    // "Id Anggota", "Nama Anggota", "Alamat", "No Telepon", "Email", "Username", 
    // "Password", "Id Role", "Nama Role", "Date Create", "Date Modify"

    column = jTable_user.getColumnModel().getColumn(0); // Id Anggota
    column.setPreferredWidth(100);

    column = jTable_user.getColumnModel().getColumn(1); // Nama Anggota
    column.setPreferredWidth(150); // Increased width for name

    column = jTable_user.getColumnModel().getColumn(2); // Alamat
    column.setPreferredWidth(200); // Increased width for address

    column = jTable_user.getColumnModel().getColumn(3); // No Telepon
    column.setPreferredWidth(120);

    column = jTable_user.getColumnModel().getColumn(4); // Email
    column.setPreferredWidth(180); // Increased width for email

    column = jTable_user.getColumnModel().getColumn(5); // Username
    column.setPreferredWidth(120);

    column = jTable_user.getColumnModel().getColumn(6); // Password
    column.setPreferredWidth(120); // Consider adjusting based on password display policy

    column = jTable_user.getColumnModel().getColumn(7); // Id Role
    column.setPreferredWidth(80);

    column = jTable_user.getColumnModel().getColumn(8); // Nama Role
    column.setPreferredWidth(120);

    column = jTable_user.getColumnModel().getColumn(9); // Date Create
    column.setPreferredWidth(150);

    column = jTable_user.getColumnModel().getColumn(10); // Date Modify
    column.setPreferredWidth(150);
}


    private void datatojtable() {
        tb= new DefaultTableModel();
        tb.addColumn("Id Anggota");
        tb.addColumn("Nama Anggota");
        tb.addColumn("Alamat");
        tb.addColumn("No Telepon");
        tb.addColumn("Email");          
        tb.addColumn("Username");
        tb.addColumn("Password");
        tb.addColumn("Id Role");
        tb.addColumn("Nama Role");
        tb.addColumn("Date Create");
        tb.addColumn("Date Modify");
        jTable_user.setModel(tb);
        Aturkolom();
        try{
            res=stat.executeQuery("select a.IdAnggota,a.NamaAnggota,a.Alamat,a.NoTelepon,a.Email,a.Username,a.Password,a.IdRole,b.NamaRole,a.DateCreate,a.DateModify from tanggota a,trole b where a.IdRole=b.IdRole");
            while (res.next()) {
                tb.addRow(new Object[]{
                res.getString("IdAnggota"),
                        res.getString("NamaAnggota"),
                        res.getString("Alamat"),
                        res.getString("NoTelepon"),
                        res.getString("Email"),
                        res.getString("Username"),
                        res.getString("Password"),
                        res.getInt("IdRole"), // Gunakan getInt untuk IdRole
                        res.getString("NamaRole"),
                        res.getTimestamp("DateCreate"), // Gunakan getTimestamp untuk DateCreate dan DateModify
                        res.getTimestamp("DateModify")
                });
            }
        }catch (SQLException e){
        }  
    }
    
    private void dataToComboBox() {
        try{
            String sql = "SELECT * FROM trole";
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();
            while (res.next()) {
                jComborole.addItem(res.getString("NamaRole"));
            }
            res.last();
            int jumlahdata = res.getRow();
            res.first();
        }catch (SQLException e) {
        }        
    }
    
    private void bersihkantextfiled() {
        txt_iduser.setText("");
        txt_nama.setText("");
        txt_pass.setText("");
        txt_konfirmasi.setText("");
        txt_iduser.requestFocus();
    }
    
    private void cekdatauser() {
        try{
            if (txt_iduser.getText().length()!=8) {
                JOptionPane.showMessageDialog(null, "Panjang Karakter Kurang dari 8 Digit");
                txt_iduser.requestFocus();
            } else {
                String sqlcek="select * from tanggota where IdAnggota='"+ txt_iduser.getText() +"'";
                ResultSet rscek=stat.executeQuery(sqlcek);
                if (rscek.next()) {
                    txt_nama.setText(rscek.getString("NamaAnggota"));
                    txt_pass.setText(rscek.getString("Password"));
                    txt_konfirmasi.setText(rscek.getString("Password"));
                    txt_nama.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Id Anggota Tidak Di temukan");
                    txt_nama.setText("");
                    txt_pass.setText("");
                    txt_konfirmasi.setText("");
                    txt_nama.requestFocus();
                }
            }
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null,"GAGAL KETEMU");
        }     
    }
    
    public void cekPass(){
        String Pass=txt_pass.getText();
        String Pass1=txt_konfirmasi.getText();
        if (Pass.equals(Pass1)) {
            jComborole.requestFocus();
        } else {
            JOptionPane.showMessageDialog(null, "Password Belum Sama","Pesan",JOptionPane.ERROR_MESSAGE);
            txt_konfirmasi.requestFocus();
        }           
      }
    
    public  void cekrool_id() {
        String roleid=jComborole.getSelectedItem().toString();
        try{
            String sql="select * from trole where NamaRole='"+ roleid +"'";
            ResultSet rsid=stat.executeQuery(sql);
            if (rsid.next()) {
                rool_id=rsid.getString("IdRole");
            } else {
                rool_id="0";
            }
            InserUpdate();
        } catch(SQLException err) {
            JOptionPane.showMessageDialog(this, "Koneksi Gagal\n"+err.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
     }
    
    public void InserUpdate() {
        try{
            Statement strU=con.createStatement();
            Statement strI=con.createStatement();
            Statement str =con.createStatement();          
            String sqlu="select * from tanggota where IdAnggota='"+ txt_iduser.getText() +"'";
            ResultSet rsu=str.executeQuery(sqlu);
            java.util.Date tanggal=new java.util.Date();
            java.text.SimpleDateFormat setTanggal=new java.text.SimpleDateFormat("yyyy-MM-dd");
            String TglNow=setTanggal.format(tanggal);
            if (rsu.next()) {
                String SqlU="update tanggota set NamaAnggota='"+ txt_nama.getText() +"',password ='"+ txt_pass.getText() +"',roleid='"+ rool_id +"',datemodify='"+ TglNow +"' where idUser='"+ txt_iduser.getText() +"'";
                strU.executeUpdate(SqlU);
                JOptionPane.showMessageDialog(null, "Data Sudah Di Ubah","Insert",JOptionPane.INFORMATION_MESSAGE);
                datatojtable();
                bersihkantextfiled();
            } else {
                String SqlI="insert into tuser values('"+ txt_iduser.getText() +"','"+ txt_nama.getText() +"','"+ txt_pass.getText() +"','"+ rool_id +"','"+TglNow+"','"+TglNow+"')";
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
                SqlDel.executeUpdate("delete from tuser where iduser='"+  txt_iduser.getText()+"'");   
                JOptionPane.showMessageDialog(this,"Data berhasil Di Hapus","Success",JOptionPane.INFORMATION_MESSAGE);
                bersihkantextfiled();
                datatojtable();
            } catch(SQLException e) {
                JOptionPane.showMessageDialog(this,"Delete data gagal\n"+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Kode User Batal Di Hapus");
            txt_iduser.requestFocus(); 
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
        txt_iduser = new javax.swing.JTextField();
        txt_nama = new javax.swing.JTextField();
        txt_pass = new javax.swing.JTextField();
        txt_konfirmasi = new javax.swing.JTextField();
        jComborole = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_user = new javax.swing.JTable();
        btn_simpan = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        cmd_keluar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("MAINTENANCE USER");

        jLabel2.setText("Id Anggota");

        jLabel3.setText("Nama User");

        jLabel4.setText("Password");

        jLabel5.setText("Confirm Pass");

        txt_iduser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_iduserKeyPressed(evt);
            }
        });

        txt_pass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_passKeyPressed(evt);
            }
        });

        txt_konfirmasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_konfirmasiKeyPressed(evt);
            }
        });

        jComborole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboroleActionPerformed(evt);
            }
        });

        jLabel6.setText("Role User");

        jTable_user.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable_user.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_userMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_user);

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

        jLabel7.setText("Alamat");

        jLabel8.setText("No Telepon");

        jLabel9.setText("Email");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_nama)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txt_pass, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txt_konfirmasi, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jComborole, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btn_simpan)
                        .addGap(18, 18, 18)
                        .addComponent(btn_hapus)
                        .addGap(141, 141, 141)
                        .addComponent(cmd_keluar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1)
                            .addComponent(jTextField3)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_iduser, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(33, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_iduser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txt_pass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_konfirmasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComborole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_simpan)
                    .addComponent(btn_hapus)
                    .addComponent(cmd_keluar))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboroleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboroleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboroleActionPerformed

    private void txt_passKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_passKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode()==KeyEvent.VK_ENTER) {
            txt_nama.requestFocus();
        }
    }//GEN-LAST:event_txt_passKeyPressed

    private void txt_konfirmasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_konfirmasiKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode()==KeyEvent.VK_ENTER){
              cekPass();
        } 

    }//GEN-LAST:event_txt_konfirmasiKeyPressed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        // TODO add your handling code here:
        String kodeuser=txt_iduser.getText();
        String namauser=txt_nama.getText();
        String pass=txt_pass.getText();
        String cpass=txt_konfirmasi.getText();
        String roleid=jComborole.getSelectedItem().toString();
        String roolid;
        if (namauser.isEmpty() && kodeuser.isEmpty() && pass.isEmpty() && cpass.isEmpty() ) {
             JOptionPane.showMessageDialog(null, "Ada kolom yang masih kosong");
             txt_iduser.requestFocus();
        } else {
            if (kodeuser.length()==8) {
                cekrool_id();
            } else {
               JOptionPane.showMessageDialog(null, "Panjang Karakter Kurang dari 8 Digit");
               txt_iduser.requestFocus();
            }
        }
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        // TODO add your handling code here:
        if (txt_iduser.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Kode User Tidak Boleh Kosong");
            txt_iduser.requestFocus();
        } else {
           hapus_data();
        }
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void cmd_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd_keluarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_cmd_keluarActionPerformed

    private void txt_iduserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_iduserKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode()==KeyEvent.VK_ENTER) {
            cekdatauser();
        } 

    }//GEN-LAST:event_txt_iduserKeyPressed

    private void jTable_userMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_userMouseClicked
        // TODO add your handling code here:
        txt_iduser.setText(tb.getValueAt(jTable_user.getSelectedRow(),0).toString());
        txt_nama.setText(tb.getValueAt(jTable_user.getSelectedRow(),1).toString());
        txt_pass.setText(tb.getValueAt(jTable_user.getSelectedRow(),2).toString());
        txt_konfirmasi.setText(tb.getValueAt(jTable_user.getSelectedRow(),2).toString());
        jComborole.setSelectedItem(tb.getValueAt(jTable_user.getSelectedRow(), 4).toString());
        rool_id=tb.getValueAt(jComborole.getSelectedIndex(),0).toString();
    }//GEN-LAST:event_jTable_userMouseClicked

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
            java.util.logging.Logger.getLogger(JFrameMaintenanceUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameMaintenanceUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameMaintenanceUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameMaintenanceUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrameMaintenanceUser().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton cmd_keluar;
    private javax.swing.JComboBox<String> jComborole;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_user;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField txt_iduser;
    private javax.swing.JTextField txt_konfirmasi;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_pass;
    // End of variables declaration//GEN-END:variables
}
