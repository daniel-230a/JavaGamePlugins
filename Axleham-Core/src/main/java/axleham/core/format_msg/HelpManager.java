package axleham.core.format_msg;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class HelpManager {

    //INITIALISE COLOR FORMAT
    private ChatColor prefix_color = ChatColor.GOLD;
    private ChatColor heading_color = ChatColor.GREEN;
    private ChatColor bullet_point_color = ChatColor.GOLD;
    private ChatColor bullet_text_color = ChatColor.YELLOW;

    public void helpMessage(Player recipient,String prefix, String heading, ArrayList<String> bullet_points) {
        
        recipient.sendMessage(prefix_color + prefix + " " + heading_color + ChatColor.BOLD.toString() + heading);

        for(int i = 0; i <= bullet_points.size()-1; i++) {

            recipient.sendMessage(bullet_point_color + "> " + bullet_text_color + bullet_points.get(i));

        }

    }

}
