package axleham.ranks.cmds;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import axleham.ranks.handle.FileHandler;
import axleham.ranks.main.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LobbyCMD {

    FileHandler file_handler = new FileHandler();
    String server_Type = file_handler.getServerType();

    public void sendToSpawn(Player p) {

        if (server_Type.equals("lobby")) {

            Location spawn = p.getWorld().getSpawnLocation();

            Location centered_spawn = new Location(p.getWorld(), spawn.getX() + 0.5, spawn.getY(), spawn.getZ() + 0.5);

            p.teleport(centered_spawn);


        } else {

            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF("lobby");
            //applies to the player you send it to. aka Kick To Server.
            p.sendPluginMessage(Main.plugin, "BungeeCord", out.toByteArray());

        }



    }

}
