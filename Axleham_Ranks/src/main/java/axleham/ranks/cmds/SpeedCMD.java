package axleham.ranks.cmds;

import axleham.core.main.AxlehamCoreAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SpeedCMD {

    private AxlehamCoreAPI axleham_api = (AxlehamCoreAPI) Bukkit.getServer().getPluginManager().getPlugin("Axleham_Core_API");

    public void toggleSpeed(Player p, String[] args) {

        if(args.length >= 2) {
            if (args[1].equalsIgnoreCase("on")) {
                p.setFlySpeed(1.0f);
                p.setWalkSpeed(1.0f);
                p.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "Speed Enabled");
            } else if (args[1].equalsIgnoreCase("off")) {
                p.setFlySpeed(0.2f);
                p.setWalkSpeed(0.2f);
                p.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "Speed Disabled");
            } else {
                p.sendMessage(ChatColor.GOLD + "[Help] " + ChatColor.YELLOW + "Usage: /speed <On|Off>");
            }

        } else {
            ArrayList<String> bullet_points = new ArrayList<String>();
            bullet_points.add("/speed <on|off>");

            axleham_api.help_manager.helpMessage(p, "[Help]", "Command Usage: /speed", bullet_points);
        }

    }

}
