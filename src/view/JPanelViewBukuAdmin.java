/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import main.PanelUpdateListener;


/**
 *
 * @author Hp
 */
public class JPanelViewBukuAdmin extends javax.swing.JPanel implements PanelUpdateListener {
    
    DefaultTableModel tabelmodel; 
    Connection con=null; 
    Statement stat; 
    ResultSet res; 
    PreparedStatement pst = null; 
    String cekconeks; 

    /**
     * Creates new form JPanelViewBukuAdmin
     */
    public JPanelViewBukuAdmin() {
        initComponents();
        koneksi();
        datatojtable();
        
        jTextNamaBuku.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                search();
            }
            
            private void search() {
                Caridata(jTextNamaBuku.getText());
            }
        });
    }
    
@Override
public void onPanelUpdate(String panelName) {
    if ("MAINTENANCE_BUKU".equals(panelName) || 
        "MAINTENANCE_KATEGORI".equals(panelName) || 
        "MAINTENANCE_RAK".equals(panelName)) {
        datatojtable(); // Refresh data tabel
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
    
    public void bersihkan() {
        jTextNamaBuku.setText("");
        jTextNamaBuku.requestFocus();
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
tb.addColumn("Eksemplar");
tb.addColumn("Date Create"); 
tb.addColumn("Date Modify"); 
tb.addColumn("Id Kategori"); 
tb.addColumn("Id Rak"); 
tb.addColumn("Id Eksemplar"); 
tb.addColumn("Kode Eksemplar"); 
jTableBukuView.setModel(tb); 
try{ 
// Mengambil data dari database
  res=stat.executeQuery("SELECT b.IdEksemplar,b.KodeEksemplar,a.*,c.IdKategori,d.IdRak FROM tmasterbuku a JOIN teksemplar b ON a.IdBuku = b.IdBuku JOIN tkategori c ON a.IdKategori = c.IdKategori JOIN trak d ON a.IdRak = d.IdRak GROUP BY b.IdEksemplar, b.KodeEksemplar ORDER BY b.IdEksemplar ASC;"); 
        
 
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
            res.getString("ConEksemplar"), 
            res.getString("DateCreate"), 
            res.getString("DateModify"),     
            res.getString("IdKategori"), 
            res.getString("IdRak"),
            res.getString("IdEksemplar"), 
            res.getString("KodeEksemplar")
           }); 
          } 
         Aturkolom(); //pemanggilan class untuk mengatur kolom 
         
        }catch (SQLException e){ 
        } 
        
    }  
    
     private void Aturkolom() 
    { 
        TableColumn column; 
  jTableBukuView.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF); 
        column = jTableBukuView.getColumnModel().getColumn(0); 
        column.setPreferredWidth(75); 
        column = jTableBukuView.getColumnModel().getColumn(1); 
        column.setPreferredWidth(200); 
        column = jTableBukuView.getColumnModel().getColumn(2); 
        column.setPreferredWidth(100); 
        column = jTableBukuView.getColumnModel().getColumn(3); 
        column.setPreferredWidth(75); 
        column = jTableBukuView.getColumnModel().getColumn(4); 
        column.setPreferredWidth(75);     
        column = jTableBukuView.getColumnModel().getColumn(5); 
        column.setPreferredWidth(80); 
        column = jTableBukuView.getColumnModel().getColumn(6); 
        column.setPreferredWidth(75); 
         column = jTableBukuView.getColumnModel().getColumn(7); 
        column.setPreferredWidth(75); 
         column = jTableBukuView.getColumnModel().getColumn(8); 
        column.setPreferredWidth(75); 
    } 
    
    private void Caridata(String key) 
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
        
        jTableBukuView.setModel(tb); 
        try{ 
        // Mengambil data dari database 
         res=stat.executeQuery("SELECT b.IdEksemplar,b.KodeEksemplar,a.*,c.IdKategori,d.IdRak FROM tmasterbuku a JOIN teksemplar b ON a.IdBuku = b.IdBuku JOIN tkategori c ON a.IdKategori = c.IdKategori JOIN trak d ON a.IdRak = d.IdRak GROUP BY b.IdEksemplar, b.KodeEksemplar ORDER BY b.IdEksemplar ASC;"); 
        
 
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextNamaBuku = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableBukuView = new javax.swing.JTable();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("VIEW BUKU");

        jLabel2.setText("NAMA BUKU");

        jTableBukuView.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableBukuView);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextNamaBuku))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextNamaBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(241, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableBukuView;
    private javax.swing.JTextField jTextNamaBuku;
    // End of variables declaration//GEN-END:variables
}
