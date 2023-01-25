package axleham.core.link_server;

import axleham.core.main.AxlehamCoreAPI;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import javafx.util.Callback;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;



public class ProxyLink {

    public void proxyConnect(Player p, String server_name) {

        for(int i = 0; i <= GameServers.values().length-1 ; i++) {

            GameServers server = GameServers.values()[i];

            if (server.name().toLowerCase().equals(server_name) && server.isOnline()) {

                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("Connect");
                out.writeUTF(server_name);
                p.sendPluginMessage(AxlehamCoreAPI.plugin, "BungeeCord", out.toByteArray());

                break;

            } else if (i == GameServers.values().length-1) {
                p.sendMessage( server.getDisplayName(server_name) + ChatColor.RED + " is Offline");
            }


        }

    }

    public void updateProxyPlayerCount(Player p, String server_name) {

        for(int i = 0; i <= GameServers.values().length-1 ; i++) {

            GameServers server = GameServers.values()[i];

            if (server.name().equalsIgnoreCase(server_name)) {

                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("PlayerCount");
                out.writeUTF(server_name.toLowerCase());
                p.sendPluginMessage(AxlehamCoreAPI.plugin, "BungeeCord", out.toByteArray());
                break;

            }

            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("PlayerCount");
            out.writeUTF("ALL");
            p.sendPluginMessage(AxlehamCoreAPI.plugin, "BungeeCord", out.toByteArray());

        }

    }


}
