package axleham.core.main;

import axleham.core.db.DBManager;
import axleham.core.format_msg.HelpManager;
import axleham.core.hologram.HologramHandler;
import axleham.core.hologram.HologramCMD;
import axleham.core.hologram.HologramFileHandler;
import axleham.core.link_server.GameServers;
import axleham.core.link_server.ProxyLink;
import axleham.core.npc.NPC;
import axleham.core.npc.NPC_CMD;
import axleham.core.npc.NPC_FileHandler;
import axleham.core.npc.NPC_Handler;
import axleham.core.onscreen_msg.OnScreenMsg;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class AxlehamCoreAPI extends JavaPlugin implements Listener, PluginMessageListener {

    public HelpManager help_manager;
    public DBManager DB_manager;
    public HologramHandler hologram;
    public HologramCMD hologram_cmd;
    public HologramFileHandler hologram_file_handler;
    public ProxyLink proxy_link;
    public NPC npc;
    public NPC_CMD npc_cmd;
    public OnScreenMsg on_screen_msg;
    public NPC_FileHandler npc_fileHandler;
    public NPC_Handler npc_handler;

    public static AxlehamCoreAPI plugin;

    @Override
    public void onEnable() {

        plugin = this;

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[Axleham Core API] Enabled");

        help_manager = new HelpManager();
        DB_manager = new DBManager();
        hologram = new HologramHandler();
        hologram_file_handler = new HologramFileHandler();
        hologram_cmd = new HologramCMD();
        proxy_link = new ProxyLink();
        npc = new NPC();
        npc_cmd = new NPC_CMD();
        on_screen_msg = new OnScreenMsg();
        npc_fileHandler = new NPC_FileHandler();
        npc_handler = new NPC_Handler();

        hologram_file_handler.Setup();
        npc_fileHandler.Setup();
        npc_handler.getSavedNPCs();
    }

    @Override
    public void onPluginMessageReceived(String channel, Player p, byte[] message) {

        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subChannel = in.readUTF();

        if (subChannel.equals("PlayerCount")) {
            String server = in.readUTF();
            int player_count = in.readInt();

            GameServers.player_count.put(server, player_count);
        }

    }
}
