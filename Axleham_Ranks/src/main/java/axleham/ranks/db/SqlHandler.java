package axleham.ranks.db;

import axleham.core.db.DBManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SqlHandler implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();
        createPlayer(p.getUniqueId(), p);

    }


    public boolean playerExists(UUID uuid, Player p) {
        try {

            PreparedStatement statement = DBManager.getConnection().prepareStatement("SELECT player_name FROM " + DBManager.table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());


            ResultSet results = statement.executeQuery();

            if(results.next()) {

                if (!results.getString("player_name").equals(p.getName())) {

                    PreparedStatement insert = DBManager.getConnection()
                            .prepareStatement("UPDATE " + DBManager.table + " SET player_name=? WHERE UUID=?");

                    insert.setString(1, p.getName());
                    insert.setString(2, uuid.toString());

                    insert.executeUpdate();

                }

                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createPlayer(final UUID uuid, Player p) {

        try {

            if(!playerExists(uuid, p)) {

                Date date = new Date(new java.util.Date().getTime());


                PreparedStatement insert = DBManager.getConnection()
                        .prepareStatement("INSERT INTO " + DBManager.table + " (UUID, player_name, rank, join_date) VALUE (?,?,?,?)");

                insert.setString(1, uuid.toString());
                insert.setString(2, p.getName());
                insert.setInt(3, 0);
                insert.setDate(4, date);

                insert.executeUpdate();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
