package axleham.ranks.main;

import axleham.core.db.DBConnections;
import axleham.core.db.DBManager;
import axleham.ranks.db.SqlHandler;
import axleham.ranks.handle.EventListener;
import axleham.ranks.handle.FileHandler;
import axleham.ranks.handle.RefreshRanks;
import axleham.ranks.handle.TabComplete;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import axleham.ranks.perms.HandlePerms;


public class Main extends JavaPlugin implements Listener{


    public static Main plugin;


    FileHandler file_handler = new FileHandler();
    EventListener e_listener = new EventListener();
    RefreshRanks refresh_rank = new RefreshRanks();
    DBManager db_manager = new DBManager();
    SqlHandler sql_handler = new SqlHandler();
    HandlePerms handle_perms = new HandlePerms();

    @Override
    public void onEnable() {

        plugin = this;

        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        PluginManager plugin_manager = getServer().getPluginManager();

        plugin_manager.registerEvents(e_listener, this);
        plugin_manager.registerEvents(sql_handler, this);
        plugin_manager.registerEvents(handle_perms, this);

        getCommand("rank").setTabCompleter(new TabComplete());

        file_handler.Setup();
        refresh_rank.refresh_ranks();



    }

    @Override
    public void onDisable() {

    }

    public Plugin get() {

        return plugin;

    }


}
