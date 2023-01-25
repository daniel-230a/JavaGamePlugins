package axleham.core.link_server;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public enum GameServers {

    LOBBY(PhysicalSevers.SERVER_1.getIpAddress(),
            25565,
            ChatColor.AQUA + "Lobby",
            new String[] {ChatColor.GRAY + "Click to go to Lobby"},
            new ItemStack(Material.ENCHANTED_BOOK)),
    RVB(PhysicalSevers.SERVER_1.getIpAddress(),
            25568,
            ChatColor.RED + "Red" + ChatColor.GRAY + "Vs" + ChatColor.BLUE + "Blue",
            new String[] {ChatColor.GRAY + "- Building",
                    ChatColor.GRAY + "- Development",
                    ChatColor.GRAY + "- Custom Game"},
            new ItemStack(Material.IRON_SWORD)),
    FARM_FIGHT(PhysicalSevers.SERVER_1.getIpAddress(),
            0,
            ChatColor.YELLOW + "Farm Fight",
            new String[] {ChatColor.GRAY + "- Custom Game"},
            new ItemStack(Material.WHEAT)),
    SKY_WARS(PhysicalSevers.SERVER_1.getIpAddress(),
            0,
            ChatColor.AQUA + "Sky Wars",
            new String[] {},
            new ItemStack(Material.FEATHER)),
    @SuppressWarnings("deprecation")
    BED_WARS(PhysicalSevers.SERVER_1.getIpAddress(),
            0,
            ChatColor.RED + "Bed Wars",
            new String[] {},
            new ItemStack(Material.BED, 1,DyeColor.BROWN.getData())),
    PARKOUR(PhysicalSevers.SERVER_1.getIpAddress(),
            0,
            ChatColor.GREEN + "Parkour",
            new String[] {},
            new ItemStack(Material.LADDER)),
    SURVIVAL(PhysicalSevers.SERVER_1.getIpAddress(),
            25570,
            ChatColor.GREEN + "Survival",
            new String[] {},
            new ItemStack(Material.GRASS));


    private String display_name;
    private String server_address;
    private Integer server_port;
    private String[] item_lore;
    private ItemStack item_stack;

    private static final Map<Material, GameServers> game_server_selectors = new HashMap<>();
    private static final Map<String, String> game_server_names = new HashMap<>();
    public static final Map<String, Integer> player_count = new HashMap<>();
    private static Integer player_count_online = 0;

    static{

        for (GameServers game_server : values()) {

            game_server_selectors.put(game_server.getItem(), game_server);
            game_server_names.put(game_server.name().toLowerCase(), game_server.getDisplayName());
            player_count.put(game_server.name().toLowerCase(), 0);

        }

    }

    GameServers(String server_address, Integer server_port, String display_name, String[] item_lore, ItemStack item_stack) {

        this.server_address = server_address;
        this.server_port = server_port;
        this.display_name = display_name;
        this.item_lore = item_lore;
        this.item_stack = item_stack;

    }

    public String getDisplayName() {
        return display_name;
    }

    public String getDisplayName(String server_name) {
        return game_server_names.get(server_name);
    }

    public String[] getItemLore() {
        return item_lore;
    }

    public ItemStack getItemStack() {
        return item_stack;
    }

    public Material getItem() {
        return item_stack.getType();
    }

    public boolean isOnline() {

        try {
            Socket s = new Socket();
            s.connect(new InetSocketAddress(this.getIpAddress(), this.getServerPort()), 15);
            s.close();
            return true;
        } catch (Exception e) {
            // not online
            return false;
        }

    }

    public String getIpAddress() {
        return server_address;
    }

    public Integer getServerPort() {
        return server_port;
    }

    public static GameServers getGameServerBySelector(Material selector) {
        return game_server_selectors.get(selector);
    }

    public static Integer getPlayerCount() {
        return player_count_online;
    }

    public static Integer getPlayerCount(String server) {
        return player_count.get(server);
    }

}
