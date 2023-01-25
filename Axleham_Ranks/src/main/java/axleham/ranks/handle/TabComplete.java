package axleham.ranks.handle;

import axleham.ranks.db.PlayerHandler;
import axleham.ranks.main.Ranks;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class TabComplete implements TabCompleter {

    PlayerHandler player_handler = new PlayerHandler();

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> cmds = new ArrayList<String>();

        if (sender instanceof Player) {

            Player p = (Player) sender;



            if (args.length == 2) {

                for(int i = 0; i <= Ranks.values().length-1 ; i++) {

                    Ranks rank_name = Ranks.values()[i];

                    if (player_handler.getRank(p) > rank_name.getRankValue()) {

                        cmds.add(rank_name.prettyPrintRank());

                    } else if (p.isOp()) {
                        cmds.add(rank_name.prettyPrintRank());
                    }


                }

                return StringUtil.copyPartialMatches(args[1], cmds, new ArrayList<String>());

            }

        }

        return null;
    }

}
