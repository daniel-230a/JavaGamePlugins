package axleham.core.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class DBManager {


    private static Connection connection;

    public static String host, db, username, password, table;
    public static int port;

    public void mysqlSetup() {
        host = "77.68.21.119";
        port = 3306;
        db = "axleham";
        username = "axleham";
        password = "";
        table= "tbl_player_data";

        try {
            synchronized (this) {

                if(getConnection() != null && !getConnection().isClosed()) {
                    return;
                } else {

                    Class.forName("com.mysql.jdbc.Driver");
                    setConnection(DriverManager.getConnection("jdbc:mysql://" + DBManager.host + ":"
                            + DBManager.port + "/" + DBManager.db + "?characterEncoding=latin1", DBManager.username, DBManager.password));

                    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[Axleham Core API] MYSQL DB CONNECTED");

                }

            }

        } catch(SQLException e) {
            e.printStackTrace();
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }



    }

    public static Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        DBManager.connection = connection;
    }


}
