package axleham.ranks.cmds;

import axleham.core.main.AxlehamCoreAPI;
import axleham.ranks.handle.FileHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ServerTypeCMD {

    private AxlehamCoreAPI axleham_api = (AxlehamCoreAPI) Bukkit.getServer().getPluginManager().getPlugin("Axleham_Core_API");

    FileHandler file_handler = new FileHandler();

    File server_config_file = new File(file_handler.configFilePath());
    YamlConfiguration yml = YamlConfiguration.loadConfiguration(server_config_file);

    public void setServerType(Player p, String[] args) {

        if(args.length == 3) {
            if(args[1].toLowerCase().equals("set")) {
                boolean server_set = false;
                if (args[2].toLowerCase().equals("lobby")) {
                    yml.set("server_type", "lobby");
                    server_set = true;
                } else if (args[2].toLowerCase().equals("game")) {
                    yml.set("server_type", "game");
                    server_set = true;
                } else {
                    p.sendMessage(ChatColor.RED + "That is not a valid server type");
                }

                if (server_set) {
                    try {
                        yml.save(server_config_file);
                        p.sendMessage(ChatColor.GREEN + "Successfully set server type to "  + args[2].toLowerCase());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }


            } else {
                ArrayList<String> bullet_points = new ArrayList<String>();
                bullet_points.add("/server_type set <Game|Lobby>");

                axleham_api.help_manager.helpMessage(p, "[Help]", "Command Usage: /server_type", bullet_points);

            }
        } else {
            ArrayList<String> bullet_points = new ArrayList<String>();
            bullet_points.add("/server_type set <Game|Lobby>");

            axleham_api.help_manager.helpMessage(p, "[Help]", "Command Usage: /server_type", bullet_points);
        }

    }

}
