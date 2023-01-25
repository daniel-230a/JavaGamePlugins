package axleham.ranks.db;

import axleham.core.db.DBManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerHandler {

    public boolean setRank(Player p, int rank) {

        try {

            PreparedStatement insert = DBManager.getConnection()
                    .prepareStatement("UPDATE " + DBManager.table + " SET `rank`=? WHERE UUID=?");

            insert.setInt(1, rank);
            insert.setString(2, p.getUniqueId().toString());
            insert.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean setOfflineRank(OfflinePlayer p, int rank) {

        try {

            PreparedStatement insert = DBManager.getConnection()
                    .prepareStatement("UPDATE " + DBManager.table + " SET `rank`=? WHERE UUID=?");

            insert.setInt(1, rank);
            insert.setString(2, p.getUniqueId().toString());
            insert.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public int getRank(Player p) {

        try {

            PreparedStatement statement = DBManager.getConnection().prepareStatement("SELECT `rank` FROM " + DBManager.table + " WHERE UUID=?");

            statement.setString(1, p.getUniqueId().toString());

            ResultSet results = statement.executeQuery();

            if(results.next()) {
                return results.getInt("rank");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;


    }

    public int getOfflineRank(OfflinePlayer p) {

        try {

            PreparedStatement statement = DBManager.getConnection().prepareStatement("SELECT `rank` FROM " + DBManager.table + " WHERE UUID=?");

            statement.setString(1, p.getUniqueId().toString());

            ResultSet results = statement.executeQuery();

            if(results.next()) {
                return results.getInt("rank");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;


    }

    public String getPlayerUUID(String p) {

        try {

            PreparedStatement statement = DBManager.getConnection().prepareStatement("SELECT `UUID` FROM " + DBManager.table + " WHERE player_name=?");

            statement.setString(1, p);

            ResultSet results = statement.executeQuery();

            if(results.next()) {
                return results.getString("UUID");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;


    }

}
