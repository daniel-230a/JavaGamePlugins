package axleham.core.npc;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class NPC_FileHandler {

    private String Dir_Path = "plugins/Server_config";
    private String file_name = "/npcs.yml";

    private File server_config_file = new File(Dir_Path + file_name);
    private YamlConfiguration config_yml_file = YamlConfiguration.loadConfiguration(server_config_file);

    public void Setup() {

        File MainDirectory =new File(Dir_Path);
        if(!MainDirectory.exists()) {
            MainDirectory.mkdir();
        }

        File server_config_file = new File(Dir_Path + file_name);

        YamlConfiguration npc_db = YamlConfiguration.loadConfiguration(server_config_file);
        npc_db.options().copyDefaults(true);

        try {
            npc_db.save(server_config_file);
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
