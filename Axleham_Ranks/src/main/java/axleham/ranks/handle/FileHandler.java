package axleham.ranks.handle;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public class FileHandler {

    private String Dir_Path = "plugins/Server_config";
    private String file_name = "/server_config.yml";

    public void Setup() {

        File MainDirectory =new File(Dir_Path);
        if(!MainDirectory.exists()) {
            MainDirectory.mkdir();
        }

        File server_config_file = new File(Dir_Path + file_name);

        YamlConfiguration yml = YamlConfiguration.loadConfiguration(server_config_file);
        yml.addDefault("server_type", "default");
        yml.options().copyDefaults(true);

        try {
            yml.save(server_config_file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getServerType() {

        File server_config_file = new File(Dir_Path + file_name);
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(server_config_file);
        String server_Type = yml.getString("server_type");

        return  server_Type;

    }

    public String configFilePath() {

        return Dir_Path + file_name;

    }

}
