package br.unip.chatclient.DAO;

import static br.unip.chatclient.controler.ConnectionFactory.getConnection;
import br.unip.chatclient.model.Mensagem;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Erick
 */
public class MensagemDAO {
    public static boolean insereMensagem(Mensagem m)
        {

            try (
                Connection conn = getConnection();
                CallableStatement stmt = conn.prepareCall("{CALL SP_MENSAGEM_IN(?,?,?)}");
                )
            {
                stmt.setString(1, m.getMensagem());
                stmt.setString(2, m.getRemetente());
                stmt.setString(3, m.getDestinatario());

                stmt.execute();
                stmt.close();
                return true;

            } catch (SQLException ex) {
                return false;
            }
        }

   }

