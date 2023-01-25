package axleham.core.hologram;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class HologramFileHandler {

    private String Dir_Path = "plugins/Server_config";
    private String file_name = "/hologram.yml";

    private File server_config_file = new File(Dir_Path + file_name);
    private YamlConfiguration config_yml_file = YamlConfiguration.loadConfiguration(server_config_file);

    public void Setup() {

        File MainDirectory =new File(Dir_Path);
        if(!MainDirectory.exists()) {
            MainDirectory.mkdir();
        }

        File server_config_file = new File(Dir_Path + file_name);

        YamlConfiguration hologram_db = YamlConfiguration.loadConfiguration(server_config_file);
        hologram_db.options().copyDefaults(true);

        try {
            hologram_db.save(server_config_file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public File getConfigFile() {

        return server_config_file;

    }

    public YamlConfiguration getConfigYMLFile() {

        return config_yml_file;

    }


}
