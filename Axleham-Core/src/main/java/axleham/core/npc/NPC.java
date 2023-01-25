package axleham.core.npc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NPC {

    private static NPC_FileHandler NPC_file_handler = new NPC_FileHandler();
    private static YamlConfiguration config = NPC_file_handler.getConfigYMLFile();

    public String getID(String NPC_record) {

        return config.get(NPC_record + ".NPC_ID").toString();

    }

    public String getUUID(String NPC_record) {

        return config.get(NPC_record + ".UUID").toString();

    }

    public String getName(String NPC_record) {

        return config.get(NPC_record + ".Name").toString();

    }

    public String getLocX(String NPC_record) {

        return config.get(NPC_record + ".Location.X").toString();

    }

    public String getLocY(String NPC_record) {

        return config.get(NPC_record + ".Location.Y").toString();

    }

    public String getLocZ(String NPC_record) {

        return config.get(NPC_record + ".Location.Z").toString();

    }

    public String getLocYaw(String NPC_record) {

        return config.get(NPC_record + ".Location.Yaw").toString();

    }

    public String getLocPitch(String NPC_record) {

        return config.get(NPC_record + ".Location.Pitch").toString();

    }

    public Location getLoc(String NPC_record) {
        double x = Double.parseDouble(getLocX(NPC_record));
        double y = Double.parseDouble(getLocY(NPC_record));
        double z = Double.parseDouble(getLocZ(NPC_record));
        float yaw = Float.parseFloat(getLocYaw(NPC_record));
        float pitch = Float.parseFloat(getLocPitch(NPC_record));
        World world = Bukkit.getWorlds().get(0);

        return new Location(world, x, y, z, yaw, pitch);
    }



    public String getText(String NPC_record) {

        return config.get(NPC_record + ".Text").toString();

    }

    public String getSkin(String NPC_record) {

        return config.get(NPC_record + ".Skin").toString();

    }

    public void setID(String NPC_record, String id) {

        config.set(NPC_record + ".NPC_ID", id);

    }

    public void setUUID(String NPC_record, String uuid) {

        config.set(NPC_record + ".UUID", uuid);

    }

    public void setName(String NPC_record, String name) {

        config.set(NPC_record + ".Name", name);

    }

    public void setLocX(String NPC_record, String loc_x) {

        config.set(NPC_record + ".Location.X", loc_x);

    }

    public void setLocY(String NPC_record, String loc_y) {

        config.set(NPC_record + ".Location.Y", loc_y);

    }

    public void setLocZ(String NPC_record, String loc_z) {

        config.set(NPC_record + ".Location.Z", loc_z);

    }

    public void setLocYaw(String NPC_record, String loc_yaw) {

        config.set(NPC_record + ".Location.Yaw", loc_yaw);

    }

    public void setLocPitch(String NPC_record, String loc_pitch) {

        config.set(NPC_record + ".Location.Pitch", loc_pitch);

    }

    public void setText(String NPC_record, String text) {

        config.set(NPC_record + ".Text", text);

    }

    public void setSkin(String NPC_record, String skin_name) {

        config.set(NPC_record + ".Skin", skin_name);

    }

    public String saveNPC(String NPC_record) {

        String return_msg = "";

        try {
            config.save(NPC_file_handler.getConfigFile());
            return_msg = ChatColor.GREEN + "\"" +  getName(NPC_record) + "\" NPC created";
        } catch (IOException e) {
            return_msg = ChatColor.RED + "NPC could not be created. Something Went wrong";
            e.printStackTrace();
        }

        return return_msg;
    }

    public String deleteNPC(String NPC_record) {

        String return_msg = "";

        try {
            String name =  getName(NPC_record);
            config.set(NPC_record, null);
            config.save(NPC_file_handler.getConfigFile());
            return_msg = ChatColor.GREEN + "\"" + name  + "\" NPC deleted";
        } catch (IOException e) {
            return_msg = ChatColor.RED + "NPC could not be deleted. Something Went wrong";
            e.printStackTrace();
        }

        return return_msg;
    }



}
