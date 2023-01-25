package axleham.ranks.handle;

import axleham.core.link_server.GameServers;
import axleham.ranks.db.PlayerHandler;
import axleham.ranks.main.Main;
import axleham.ranks.main.Ranks;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RefreshRanks {

    PlayerHandler player_handler = new PlayerHandler();

    private static final Map<Ranks, String> rank_groups = new HashMap<>();

    public void refresh_ranks() {
        EventListener e_listener = new EventListener();


        //for every player online
        for(Player p : Bukkit.getOnlinePlayers()) {

            //create new scoreboard
            Scoreboard board = Bukkit.getServer().getScoreboardManager().getNewScoreboard();


            char ordered = 'A';

            for(int i = 0 ; i <= Ranks.values().length-1 ; i++) {

                Ranks rank_name = Ranks.values()[i];
                String new_group = ordered + "-" + rank_name;

                board.registerNewTeam(new_group);
                board.getTeam(new_group).setPrefix(rank_name.getRankPrefix());

                rank_groups.put(rank_name, new_group);

                ordered++;

            }

            //get every player online

            for(Player p1 : Bukkit.getOnlinePlayers()) {


                Ranks player_rank = Ranks.getRankByValue(player_handler.getRank(p1));

                board.getTeam(rank_groups.get(player_rank)).addEntry(p1.getName());

            }



            //axleham scoreboard animation

            final Objective objective = board.registerNewObjective("Test ", "test");

            new BukkitRunnable() {
                int frame_count = 0;
                int letter_count = 0;
                int wait_time= 0;
                String[] full_text = {"A", "X", "L", "E", "H", "A", "M"};
                ArrayList<String> display_text = new ArrayList<String>();

                @Override
                public void run() {
                    if (wait_time == 0) {
                        frame_count++;
                        if (frame_count <= full_text.length) {

                            if (letter_count >= full_text.length) {
                                letter_count = 0;
                                wait_time += 200;
                            }

                            if (frame_count <= letter_count)  {

                                display_text.clear();
                                for (int i = 0; i <= letter_count; i++) {
                                    display_text.add(full_text[i]);
                                    for (int j = 0; j < full_text.length - letter_count-i-1; j++) {

                                        display_text.add(" ");

                                    }


                                }

                            }


                            objective.setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + String.join("", display_text));

                        } else if (frame_count >= full_text.length) {
                            letter_count++;
                            frame_count = 0;
                        }

                    } else {
                        wait_time--;
                    }
                }
            }.runTaskTimer(Main.plugin, 0, 1);



            objective.setDisplaySlot(DisplaySlot.SIDEBAR);

            String prefix = Ranks.getRankPrefix(p);

            if (prefix == "") {
                Score rank_score = objective.getScore(ChatColor.WHITE + "Rank: " + ChatColor.GRAY + "default");
                rank_score.setScore(0);
            } else {
                Score rank_score = objective.getScore(ChatColor.WHITE + "Rank: " + prefix);
                rank_score.setScore(0);
            }




            Score blank_score_2 = objective.getScore("  ");


            if (e_listener.server_Type.equals("lobby")) {
                blank_score_2.setScore(5);
                Score coins_balance = objective.getScore(ChatColor.YELLOW + "Coins: " + ChatColor.WHITE + "0");
                coins_balance.setScore(4);
            }


            Score server_location = objective.getScore(ChatColor.GREEN + "Location: " + ChatColor.GRAY + Bukkit.getWorlds().get(0).getName());
            server_location.setScore(3);

            Score player_count = objective.getScore(ChatColor.GRAY + "Players: " + GameServers.getPlayerCount("ALL"));
            player_count.setScore(2);
            Score blank_score_1 = objective.getScore(" ");
            blank_score_1.setScore(1);


            p.setScoreboard(board);



        }
    }

}
