package axleham.ranks.handle;

import java.util.ArrayList;

import axleham.core.npc.NPC_Handler;
import axleham.core.onscreen_msg.OnScreenMsg;
import axleham.ranks.db.PlayerHandler;
import axleham.core.main.AxlehamCoreAPI;
import axleham.ranks.main.Ranks;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


import axleham.ranks.perms.HandlePerms;
import axleham.ranks.perms.PlayerPerms;

public class EventListener implements Listener{

    PlayerHandler player_handler = new PlayerHandler();
    RefreshRanks refresh_rank = new RefreshRanks();
    HandlePerms handle_perms = new HandlePerms();

    FileHandler file_handler = new FileHandler();
    String server_Type = file_handler.getServerType();

    private AxlehamCoreAPI axleham_api = (AxlehamCoreAPI) Bukkit.getServer().getPluginManager().getPlugin("Axleham_Core_API");

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();

        refresh_rank.refresh_ranks();

        String prefix = Ranks.getRankPrefix(p);

        handle_perms.rank = Ranks.getRankByValue(player_handler.getRank(p)).toString();
        handle_perms.setupPermissions(p);

        if (NPC_Handler.getNPCs() != null || !NPC_Handler.getNPCs().isEmpty()) {
            NPC_Handler.addJoinPacket(p);
        }

        Location spawn = p.getWorld().getSpawnLocation();

        Location centered_spawn = new Location(p.getWorld(), spawn.getX() + 0.5, spawn.getY(), spawn.getZ() + 0.5);

        p.teleport(centered_spawn);

        if (server_Type.equals("lobby")) {

            e.setJoinMessage(prefix + p.getName() + ChatColor.DARK_GRAY + " joined the Lobby");

            OnScreenMsg on_screen_msg = new OnScreenMsg();
            on_screen_msg.sendOnScreenMsg(ChatColor.GOLD + "Welcome to Axleham", 2, p);

        } else {

            e.setJoinMessage("");

        }

        if (p.hasPermission(PlayerPerms.PERMS_STAFF.getPermNode()) || p.isOp()) {
            ArrayList<String> bullet_points = new ArrayList<String>();
            bullet_points.add("goBrush plugin added");

            axleham_api.help_manager.helpMessage(p, "[Axleham Staff Updates]", "Plugin Addition", bullet_points);


			/*
			TextComponent more_info_click = new TextComponent(ChatColor.GOLD + "> " + ChatColor.GREEN + "[Click for more info]");

			more_info_click.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/cskull"));

			p.spigot().sendMessage(more_info_click);*/

        }

    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {

        Player p = e.getPlayer();

        String prefix = Ranks.getRankPrefix(p);

        if (server_Type.equals("lobby")) {

            e.setQuitMessage(prefix + p.getName() + ChatColor.DARK_GRAY + " Left the lobby");

        } else {
            e.setQuitMessage("");
        }



    }


    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {

        e.setCancelled(true);

        Player p = e.getPlayer();


        String msg = e.getMessage(),
                name = p.getName(),
                prefix = Ranks.getRankPrefix(p);

        if (server_Type.equals("lobby")) {

            Bukkit.broadcastMessage(prefix + name + ": " + msg);

        }


    }


}