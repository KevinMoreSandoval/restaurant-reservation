/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import BD.ConnectionBD;
import  java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 *
 * @Gian
 */
public class administrador {
    
    public boolean validaciónlogin(String usuario,String password){
    
            String sql = "SELECT * FROM admin WHERE usuario = ? AND password = ?"; //Query BD
            
        try (Connection cx = ConnectionBD.conectar(); PreparedStatement pr = cx.prepareStatement(sql) ){
            pr.setString(1, usuario);
            pr.setString(2, password);
            
            ResultSet rs = pr.executeQuery();
            return rs.next();
            
        } catch (Exception e) {
            System.out.println("Error en el Login");
            return false;
            
        }
    
    
    }
}
