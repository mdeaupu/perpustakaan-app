/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.SQLException; 
import java.sql.Statement; 
import java.sql.PreparedStatement;

/**
 *
 * @author Hp
 */
public class koneksi {
    public static Connection con; 
    public static Statement stm; 
    public static PreparedStatement ps;
    public static Connection GetKoneksi() {
         try { 
            String url ="jdbc:mysql://localhost/dbperpustakaan"; 
            String user="root"; 
            String pass=""; 
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            con =DriverManager.getConnection(url,user,pass); 
            stm = con.createStatement(); 
            System.out.println("koneksi berhasil;"); 
        } catch (ClassNotFoundException | SQLException e) { 
            System.err.println("koneksi gagal" +e.getMessage()); 
        } 
        return con;
    }
    
    public static void main(String args[]){ 
       GetKoneksi();
    } 
}
