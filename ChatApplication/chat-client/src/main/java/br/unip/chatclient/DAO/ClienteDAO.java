package br.unip.chatclient.DAO;
import static br.unip.chatclient.controler.ConnectionFactory.getConnection;
import br.unip.chatclient.model.Cliente;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClienteDAO {
    public static boolean insert(Cliente c)
        {

            try (
                Connection conn = getConnection();
                CallableStatement stmt = conn.prepareCall("{CALL SP_CLIENTE_IN(?,?,?)}");
                )
            {
                stmt.setString(1, c.getLogin());
                stmt.setString(2, c.getNome());
                stmt.setString(3, c.getPassword());

                stmt.execute();
                stmt.close();
                return true;

            } catch (SQLException ex) {
                return false;
            }
        }
    public static boolean buscaLogin(String c)        
    {
        try{
            Connection conn = getConnection();
            Statement sql = conn.createStatement();
            ResultSet rs = sql.executeQuery("SELECT CLI_STR_LOGIN FROM CLIENTE WHERE CLI_STR_LOGIN ='" +c + "'");
            if (rs.isBeforeFirst() || rs.isAfterLast()){
                return true;
            }else{
                return false;
            }
        }  catch (SQLException ex) {
                return false;
            }
    }
    public static boolean validaLogin(String c, String d)        
    {
        try{
            Connection conn = getConnection();
            Statement sql = conn.createStatement();
            ResultSet rs = sql.executeQuery("SELECT CLI_STR_LOGIN, CLI_STR_PASSWORD FROM CLIENTE WHERE CLI_STR_LOGIN ='" +c + "' and CLI_STR_PASSWORD = '"+d +"'" );
            if (rs.isBeforeFirst() || rs.isAfterLast()){
                return true;
            }else{
                return false;
            }
        }  catch (SQLException ex) {
                return false;
            }
    }
}

