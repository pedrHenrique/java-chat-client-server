package br.unip.chatclient.controler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;




public class ConnectionFactory {
    private static final String URL = "jdbc:mysql://localhost:3306/apschat?useTimezone=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "P@ssw0rd";

    public static Connection getConnection(){
        try {
            
            return DriverManager.getConnection(URL, USER, PASS);

        } catch (SQLException ex) {
            return null;
        }
    }
        public static void closeConnection(Connection conn) {
            try {
               if (conn != null) {
                    conn.close(); 
               }
              } catch (SQLException ex){
                System.out.println("Erro: " + ex);
            }
        }

            public static void closeConnection(Connection conn, PreparedStatement stmt) {
                closeConnection(conn);
                try{
                if (stmt != null) {
                    stmt.close();
                }
                conn.close();
            } catch (SQLException ex){
                System.out.println("Erro: " + ex);
            }
        }
            public static void closeConnection(Connection conn, PreparedStatement stmt, ResultSet rs) {
                closeConnection(conn,stmt);
                try{
                if (rs != null) {
                    rs.close();
                }
                conn.close();
            } catch (SQLException ex){
                System.out.println("Erro: " + ex);
            }
        }
}
