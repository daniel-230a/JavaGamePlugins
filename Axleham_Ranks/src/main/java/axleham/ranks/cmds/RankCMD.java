package axleham.ranks.cmds;

import axleham.core.main.AxlehamCoreAPI;
import axleham.ranks.db.PlayerHandler;
import axleham.ranks.handle.RefreshRanks;
import axleham.ranks.main.Ranks;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import axleham.ranks.perms.HandlePerms;

import java.util.ArrayList;
import java.util.UUID;

public class RankCMD {

    private AxlehamCoreAPI axleham_api = (AxlehamCoreAPI) Bukkit.getServer().getPluginManager().getPlugin("Axleham_Core_API");

    PlayerHandler player_handler = new PlayerHandler();
    RefreshRanks refresh_rank = new RefreshRanks();
    HandlePerms handle_perms = new HandlePerms();

    public void rankPlayer(Player p, String[] args) {

        int rank = player_handler.getRank(p);

        if(args.length == 3) {
            String target_name = args[1];
            Player target = Bukkit.getPlayer(target_name);
            String target_UUID = player_handler.getPlayerUUID(target_name);


            if(target != null || target_UUID != null) {

                int rank_value = 0;
                String rank_name = args[2];

                for(int i = 0; i <= Ranks.values().length-1 ; i++) {

                    Ranks rank_groups = Ranks.values()[i];

                    if(rank_name.equalsIgnoreCase(rank_groups.toString())) {
                        rank_value = rank_groups.getRankValue();
                        break;
                    }else {
                        rank_value = -1;
                    }

                }



                if(rank_value >= 0) {
                    if(rank_value < rank ||  p.isOp()) {

                        if (target != null) {

                            if (player_handler.getRank(target) < rank || p.isOp()) {

                                if(player_handler.setRank(target, rank_value)) {
                                    //refresh players rank to new one
                                    refresh_rank.refresh_ranks();

                                    String prefix = Ranks.getRankPrefix(target);

                                    if (prefix == "") {
                                        prefix = "default";
                                    }

                                    p.sendMessage(ChatColor.GREEN + "Successfully set " + target.getName() + "'s rank to " + prefix);
                                    target.sendMessage(ChatColor.GRAY + "Your rank has been changed to " + prefix);

                                    //trial builders
                                    if (rank_value == 40) {
                                        target.sendMessage(ChatColor.YELLOW + "Note: This is a temp rank. Features such as world edit and voxel sniper are disabled.");
                                    }

                                    //update rank string in permissions
                                    handle_perms.rank = Ranks.getRankByValue(player_handler.getRank(target)).toString();
                                    //to reassign new permissions
                                    handle_perms.refreshPermissions(target);
                                }


                            }

                        } else if (target_UUID != null) {

                            OfflinePlayer target_db = Bukkit.getOfflinePlayer(UUID.fromString(target_UUID));

                            if (player_handler.getOfflineRank(target_db) < rank || p.isOp()) {

                                if(player_handler.setOfflineRank(target_db, rank_value)) {

                                    String prefix = Ranks.getOfflineRankPrefix(target_db);

                                    if (prefix == "") {
                                        prefix = "default";
                                    }

                                    p.sendMessage(ChatColor.GREEN + "Successfully set " + target_db.getName() + "'s rank to " + prefix);

                                }


                            }

                        } else {
                            p.sendMessage(ChatColor.RED + "You don't have permission to do that");
                        }

                    } else {
                        p.sendMessage(ChatColor.RED + "You don't have permission to do that");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Error: " + rank_name + " is not a rank!");
                }

            } else {
                p.sendMessage(ChatColor.RED + "Error: " + target_name + " is not online or has never been on the server!");
            }
        } else {

            ArrayList<String> bullet_points = new ArrayList<String>();

            bullet_points.add("/rank <player> <rank name>");
            bullet_points.add("");
            bullet_points.add(ChatColor.GOLD + "Ranks:");


            for(int i = 0 ; i <= Ranks.values().length-1 ; i++) {

                Ranks rank_name = Ranks.values()[i];

                if (player_handler.getRank(p) > rank_name.getRankValue()) {

                    bullet_points.add(rank_name.prettyPrintRank());

                }  else if (p.isOp()) {
                    bullet_points.add(rank_name.prettyPrintRank());
                }


            }


            axleham_api.help_manager.helpMessage(p, "[Help]", "Command Usage: /rank", bullet_points);
        }

    }

}
