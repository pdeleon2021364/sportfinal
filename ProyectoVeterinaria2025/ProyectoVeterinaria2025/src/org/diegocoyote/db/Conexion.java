package org.diegocoyote.db;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexion {
    private static Conexion instancia;
    private Connection conexion;
    
    private Conexion(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
             String url = "jdbc:mysql://localhost:3306/DBVeterinaria2025?useSSL=false&allowPublicKeyRetrieval=true";
            String user = "quintom";
            String password = "admin";  
            conexion = (Connection) DriverManager.getConnection(url,user,password);
        } catch (ClassNotFoundException | SQLException e) {
            
        }
    }   
    
    public static synchronized Conexion getInstancia(){
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }
    
    public Connection getConexion(){
        return conexion;
    }
    
    public void setConexion(Connection conexion){
        this.conexion = conexion;
    }
    
}
