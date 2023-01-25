package axleham.ranks.main;

import axleham.ranks.db.PlayerHandler;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public enum Ranks {

    OWNER(100, "OWNER", ChatColor.GOLD, true),
    ADMIN(90, "ADMIN", ChatColor.RED, true),
    MOD(80, "MOD", ChatColor.BLUE, true),
    BUILDER(41, "BUILDER", ChatColor.DARK_GREEN, true),
    TEMP(40, "TEMP", ChatColor.DARK_AQUA, true),
    ARCHITECT(32, "[ARCHITECT]", ChatColor.DARK_PURPLE, false),
    ENGINEER(31, "[ENGINEER]", ChatColor.AQUA, false),
    INVENTOR(30, "[INVENTOR]", ChatColor.GREEN, false),
    CITIZEN(0, "", ChatColor.WHITE, false);

    private int rank_val;
    private String rank_tag;
    private ChatColor rank_color;
    private boolean staff_rank;

    private static final Map<Integer, Ranks> rank_by_values = new HashMap<>();

    static PlayerHandler player_handler = new PlayerHandler();

    static{


        for (Ranks ranks : values()) {

            rank_by_values.put(ranks.rank_val, ranks);


        }
    }

    private Ranks(int val, String tag, ChatColor color, boolean staff) {

        this.rank_val = val;
        this.rank_tag = tag;
        this.rank_color = color;
        this.staff_rank = staff;


    }

    public String getRankPrefix() {

        if (this.rank_val == 0) {
            return "";
        } else {

            if (this.isStaffRank()) {

                return this.rank_color.toString() + ChatColor.BOLD + this.rank_tag + ChatColor.WHITE + " ";

            } else {

                return this.rank_color.toString() + this.rank_tag + ChatColor.WHITE + " ";

            }

        }


    }

    public int getRankValue() {

        return this.rank_val;

    }

    public String prettyPrintRank() {

        return this.toString().substring(0, 1) + this.toString().substring(1).toLowerCase();

    }

    public boolean isStaffRank() {

        return this.staff_rank;

    }

    public static String getRankPrefix(Player p) {

        Ranks rank = getRankByValue(player_handler.getRank(p));
        String prefix = rank.getRankPrefix();

        return prefix;

    }

    public static String getOfflineRankPrefix(OfflinePlayer p) {

        Ranks rank = getRankByValue(player_handler.getOfflineRank(p));
        String prefix = rank.getRankPrefix();

        return prefix;

    }


    public static Ranks getRankByValue(int val) {

        return rank_by_values.get(val);

    }



}
