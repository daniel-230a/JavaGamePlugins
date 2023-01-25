package axleham.core.hologram;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;

public class Hologram {

    private static HologramFileHandler hologram_file_handler = new HologramFileHandler();
    private static YamlConfiguration config = hologram_file_handler.getConfigYMLFile();

    public String getID(String hologram_record) {

        return config.get(hologram_record + ".holo_ID").toString();

    }

    public String getUUID(String hologram_record) {

        return config.get(hologram_record + ".UUID").toString();

    }

    public String getName(String hologram_record) {

        return config.get(hologram_record + ".Name").toString();

    }

    public String getLocX(String hologram_record) {

        return config.get(hologram_record + ".Location.X").toString();

    }

    public String getLocY(String hologram_record) {

        return config.get(hologram_record + ".Location.Y").toString();

    }

    public String getLocZ(String hologram_record) {

        return config.get(hologram_record + ".Location.Z").toString();

    }

    public String getText(String hologram_record) {

        return config.get(hologram_record + ".Text").toString();

    }

    public void setID(String hologram_record, String id) {

        config.set(hologram_record + ".holo_ID", id);

    }

    public void setUUID(String hologram_record, String uuid) {

        config.set(hologram_record + ".UUID", uuid);

    }

    public void setName(String hologram_record, String name) {

        config.set(hologram_record + ".Name", name);

    }

    public void setLocX(String hologram_record, String loc_x) {

        config.set(hologram_record + ".Location.X", loc_x);

    }

    public void setLocY(String hologram_record, String loc_y) {

        config.set(hologram_record + ".Location.Y", loc_y);

    }

    public void setLocZ(String hologram_record, String loc_z) {

        config.set(hologram_record + ".Location.Z", loc_z);

    }

    public void setText(String hologram_record, String text) {

        config.set(hologram_record + ".Text", text);

    }

    public String saveHologram(String hologram_record) {

        String return_msg = "";

        try {
            config.save(hologram_file_handler.getConfigFile());
            return_msg = ChatColor.GREEN + "\"" +  getName(hologram_record) + "\" hologram created";
        } catch (IOException e) {
            return_msg = ChatColor.RED + "Hologram could not be created. Something Went wrong";
            e.printStackTrace();
        }

        return return_msg;
    }

    public String deleteHologram(String hologram_record) {

        String return_msg = "";

        try {
            String name =  getName(hologram_record);
            config.set(hologram_record, null);
            config.save(hologram_file_handler.getConfigFile());
            return_msg = ChatColor.GREEN + "\"" + name  + "\" hologram deleted";
        } catch (IOException e) {
            return_msg = ChatColor.RED + "Hologram could not be deleted. Something Went wrong";
            e.printStackTrace();
        }

        return return_msg;
    }

}
