/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main;

import javax.swing.JFrame;
import main.JFrameRoleUser;
import main.JFrameMaintenanceUser;
import view.JPanelMaintenanceBukuAdmin;
import view.JPanelMaintenanceKategoriAdmin;
import view.JPanelMaintenanceRakAdmin;
import view.JPanelPeminjamanUser;
import view.JPanelPengembalianUser;
import view.JPanelViewBukuAdmin;
import view.JPanelViewPeminjamanAdmin;

/**
 *
 * @author Hp
 */


public class JFrameHome extends javax.swing.JFrame {
    private String loggedInRole;
    private PanelUpdateListener updateListener;
    JPanelMaintenanceBukuAdmin MBA = new JPanelMaintenanceBukuAdmin();
    JPanelViewBukuAdmin V = new JPanelViewBukuAdmin();
    JPanelMaintenanceKategoriAdmin K = new JPanelMaintenanceKategoriAdmin();
    JPanelMaintenanceRakAdmin R = new JPanelMaintenanceRakAdmin();
    JPanelViewPeminjamanAdmin VPA = new JPanelViewPeminjamanAdmin();
    JPanelPeminjamanUser PU = new JPanelPeminjamanUser();
    JPanelPengembalianUser PUU = new JPanelPengembalianUser();
    
    /**
     * Creates new form JFrameHome
     */
    
    public JFrameHome() {
        initComponents();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Tambahkan ini
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                kembaliKeLogin();
            }
        });
    }
    
    private void kembaliKeLogin() {
        JFrameLogin login = new JFrameLogin();
        login.setVisible(true);
        this.dispose(); // Tutup frame home
    }
    
    public void setUpdateListener(PanelUpdateListener listener) {
        this.updateListener = listener;
    }
    
    public void notifyPanels(String panelName) {
        if (updateListener != null) {
            updateListener.onPanelUpdate(panelName);
        }
    }
    
    public void setRole(String role) {
    this.loggedInRole = role;
    if (loggedInRole != null && loggedInRole.equals("1")) {
        MRole.setVisible(true);
        MUser.setVisible(true);
        MAnggota.setVisible(false);
        jTabbedPane.setVisible(true);
        jTabbedPane.addTab("Maintenance Buku", MBA);
        jTabbedPane.addTab("View Buku", V);
        jTabbedPane.addTab("Maintenance Kategori", K);
        jTabbedPane.addTab("Maintenance Rak", R);
        MBA.setUpdateListener(() -> notifyPanels("MAINTENANCE_BUKU"));
            K.setUpdateListener(() -> {
                notifyPanels("MAINTENANCE_KATEGORI");
                MBA.refreshData();
            });
            R.setUpdateListener(() -> {
                notifyPanels("MAINTENANCE_RAK");
                MBA.refreshData();
            });
            MBA.setComboBoxUpdateListener(panelName -> {
                if ("MAINTENANCE_KATEGORI".equals(panelName)) {
                    MBA.dataToComboBoxKategori();
                } else if ("MAINTENANCE_RAK".equals(panelName)) {
                    MBA.dataToComboBoxRak();
                }
            });
    } else {
        PU.setUpdateListener(() -> {
            VPA.refreshData();
        });
        PUU.setUpdateListener(() -> {
            PU.refreshData();
            VPA.refreshData();
        });
        MRole.setVisible(false);
        MUser.setVisible(false);
        MAnggota.setVisible(true);
        jTabbedPane.setVisible(true);
        jTabbedPane.addTab("Peminjaman Buku", PU);
        jTabbedPane.addTab("Pengembalian Buku", PUU);
        jTabbedPane.addTab("View Peminjaman", VPA);
    }
    revalidate();
    repaint();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane = new javax.swing.JTabbedPane();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        MRole = new javax.swing.JMenuItem();
        MUser = new javax.swing.JMenuItem();
        MAnggota = new javax.swing.JMenuItem();
        MKeluar = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPaneMouseClicked(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/books (1).png"))); // NOI18N

        jMenu1.setText("File");

        MRole.setText("Role User");
        MRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MRoleActionPerformed(evt);
            }
        });
        jMenu1.add(MRole);

        MUser.setText("Maintenance User");
        MUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MUserActionPerformed(evt);
            }
        });
        jMenu1.add(MUser);

        MAnggota.setText("Maintenance Anggota");
        MAnggota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MAnggotaActionPerformed(evt);
            }
        });
        jMenu1.add(MAnggota);

        MKeluar.setText("Keluar");
        MKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MKeluarActionPerformed(evt);
            }
        });
        jMenu1.add(MKeluar);

        jMenuBar.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar.add(jMenu2);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(jTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void MRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MRoleActionPerformed
        // TODO add your handling code here:
        JFrameRoleUser role=new JFrameRoleUser();
        role.setLocationRelativeTo(null); 
        role.setVisible(true);
    }//GEN-LAST:event_MRoleActionPerformed

    private void MUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MUserActionPerformed
        // TODO add your handling code here:
        JFrameMaintenanceUser role=new JFrameMaintenanceUser();
        role.setLocationRelativeTo(null); 
        role.setVisible(true);
    }//GEN-LAST:event_MUserActionPerformed

    private void MKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MKeluarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_MKeluarActionPerformed

    private void jTabbedPaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPaneMouseClicked
        // TODO add your handling code here:
        K.bersihkantextfiled();
        R.bersihkantextfiled();
        MBA.Bersihkan();
        V.bersihkan();
    }//GEN-LAST:event_jTabbedPaneMouseClicked

    private void MAnggotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MAnggotaActionPerformed
        // TODO add your handling code here:
        JFrameMaintenanceAnggota role = new JFrameMaintenanceAnggota();
        role.setLocationRelativeTo(null); 
        role.setVisible(true);
    }//GEN-LAST:event_MAnggotaActionPerformed

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
            java.util.logging.Logger.getLogger(JFrameHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrameHome().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem MAnggota;
    private javax.swing.JMenuItem MKeluar;
    private javax.swing.JMenuItem MRole;
    private javax.swing.JMenuItem MUser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JTabbedPane jTabbedPane;
    // End of variables declaration//GEN-END:variables
}
