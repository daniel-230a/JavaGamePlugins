package axleham.ranks.perms;

import axleham.core.main.AxlehamCoreAPI;
import axleham.ranks.cmds.*;
import axleham.ranks.db.PlayerHandler;
import axleham.ranks.main.Ranks;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class EventPerms implements Listener {

    private AxlehamCoreAPI axleham_api = (AxlehamCoreAPI) Bukkit.getServer().getPluginManager().getPlugin("Axleham_Core_API");
    PlayerHandler player_handler = new PlayerHandler();

    @EventHandler
    public void breakBlock(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (!p.hasPermission(PlayerPerms.PERMS_BLOCKBREAK.getPermNode())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void emptyBucket(PlayerBucketEmptyEvent e) {
        Player p = e.getPlayer();
        if (!p.hasPermission(PlayerPerms.PERMS_EMPTYBUCKET.getPermNode())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void placeBlock(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (!p.hasPermission(PlayerPerms.PERMS_BLOCKPLACE.getPermNode())) {
            e.setCancelled(true);
        }
    }



    @EventHandler
    public void playerCommand(PlayerCommandPreprocessEvent e) {
        String[] args = e.getMessage().split(" ");

        Player p = e.getPlayer();
        int rank = player_handler.getRank(p);

        if(args[0].equalsIgnoreCase("/rank")) {

            if(rank >= Ranks.ADMIN.getRankValue() || p.isOp()) {

                e.setCancelled(true);

                RankCMD rank_command = new RankCMD();
                rank_command.rankPlayer(p, args);

            } else {
                p.sendMessage(ChatColor.RED + "You don't have permission to do that");
            }

        }

        if (args[0].equalsIgnoreCase("/tp")) {
            e.setCancelled(true);

            if (!p.hasPermission(PlayerPerms.PERMS_STAFF.getPermNode()) && !p.isOp()) {

                p.sendMessage(ChatColor.RED + "You don't have permission to do that");

            } else {

                TpCMD tp_command = new TpCMD();
                tp_command.teleportPlayer(p,args);

            }
        }

        if (args[0].equalsIgnoreCase("/speed")) {

            if (!p.hasPermission(PlayerPerms.PERMS_STAFF.getPermNode())) {

                e.setCancelled(true);
                p.sendMessage(ChatColor.RED + "You don't have permission to do that");

            } else {

                SpeedCMD speed_command = new SpeedCMD();
                speed_command.toggleSpeed(p,args);

            }

        }

        if (args[0].equalsIgnoreCase("/server_type")) {
            if (p.isOp()) {

                ServerTypeCMD server_type_command = new ServerTypeCMD();
                server_type_command.setServerType(p, args);

            } else {
                p.sendMessage(ChatColor.RED + "You don't have permission to do that");
            }
        }

        if (args[0].equalsIgnoreCase("/hologram")) {
            if (p.isOp() || rank >= Ranks.ADMIN.getRankValue()) {

                axleham_api.hologram_cmd.hologramCMD(p, args);

            } else {
                p.sendMessage(ChatColor.RED + "You don't have permission to do that");
            }
        }

        if (args[0].equalsIgnoreCase("/npc")) {
            if (p.isOp() || rank >= Ranks.ADMIN.getRankValue()) {

                axleham_api.npc_cmd.npcCMD(p, args);

            } else {
                p.sendMessage(ChatColor.RED + "You don't have permission to do that");
            }
        }

        if (e.getMessage().equalsIgnoreCase("/pl") || e.getMessage().equalsIgnoreCase("/plugins")) {
            if (!p.hasPermission("permission.admin")) {
                e.setCancelled(true);
                p.sendMessage(ChatColor.RED + "You don't have permission to do that");
            }
        }

        if (e.getMessage().equalsIgnoreCase("/version")) {
            if (!p.hasPermission("permission.admin")) {
                e.setCancelled(true);
                p.sendMessage(ChatColor.RED + "You don't have permission to do that");
            }
        }

        if (e.getMessage().equalsIgnoreCase("//wand")) {
            if (!p.hasPermission("worldedit.*")) {
                e.setCancelled(true);
                p.sendMessage(ChatColor.RED + "You don't have permission to do that");
            }
        }

        if(args.length >= 2) {
            if (args[0].equalsIgnoreCase("/kick") || args[0].equalsIgnoreCase("/ban") ) {
                Player target = Bukkit.getPlayer(args[1]);

                if (player_handler.getRank(target) >= player_handler.getRank(p)) {
                    e.setCancelled(true);
                    p.sendMessage(ChatColor.RED + "You don't have permission to do that");
                }
            }
        }

        if (args[0].equalsIgnoreCase("/worldedit") || args[0].equalsIgnoreCase("/we")) {
            if (args.length == 2) {
                if (args[1].equalsIgnoreCase("report") || args[1].equalsIgnoreCase("reload")) {
                    if (p.hasPermission("worldedit.report") || p.hasPermission("worldedit.reload")) {
                        e.setCancelled(true);
                        p.sendMessage(ChatColor.RED + "You don't have permission to do that");
                    }
                }
            }
        }

        if (args[0].equalsIgnoreCase("/lobby") || args[0].equalsIgnoreCase("/hub") || args[0].equalsIgnoreCase("/spawn")) {

            LobbyCMD lobby_command = new LobbyCMD();
            lobby_command.sendToSpawn(p);

        }

    }

}
