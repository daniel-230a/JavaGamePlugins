package axleham.ranks.cmds;

import axleham.ranks.db.PlayerHandler;
import axleham.core.main.AxlehamCoreAPI;
import axleham.ranks.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;


public class TpCMD {

    private AxlehamCoreAPI axleham_api = (AxlehamCoreAPI) Bukkit.getServer().getPluginManager().getPlugin("Axleham_Core_API");

    PlayerHandler player_handler = new PlayerHandler();

    int tp_request_id;
    UUID request_id;
    Player target_1;
    Player target_2;
    Player tp_request_sender;
    int tp_cooldown;

    public void teleportPlayer(final Player sender, String[] args) {

        if (!sender.hasPermission("permission.staff") && !sender.isOp()) {

           sender.sendMessage(ChatColor.RED + "You don't have permission to do that");

        } else {

            if((args.length < 2 || args.length > 3) && args.length != 5) {

                ArrayList<String> bullet_points = new ArrayList<String>();
                bullet_points.add("/tp <Player>");
                bullet_points.add("/tp <Player> <Player>");
                bullet_points.add("/tp <Player> <x> <y> <z> " + ChatColor.DARK_RED + "[Under Development]");

                axleham_api.help_manager.helpMessage(sender, "[Help]", "Command Usage: /tp", bullet_points);

            } else {

                if (args.length == 3) {


                    if (args[1].equalsIgnoreCase("cancel_request")) {

                        if (args[2].equalsIgnoreCase(request_id.toString())) {
                            if (tp_cooldown > 0) {
                                tp_request_sender.sendMessage(ChatColor.GOLD + "[AH-TP] " + ChatColor.RED + "TP request has been declined");
                                target_1.sendMessage(ChatColor.GOLD + "[AH-TP] " + ChatColor.RED + "TP request has been declined");
                                Bukkit.getScheduler().cancelTask(tp_request_id);
                                tp_cooldown = 0;
                            } else {
                               sender.sendMessage(ChatColor.GOLD + "[AH-TP] " + ChatColor.RED + "This request has already expired");
                            }
                        } else {
                           sender.sendMessage(ChatColor.GOLD + "[AH-TP] " + ChatColor.RED + "This request has already expired");
                        }

                    }else if (args[1].equalsIgnoreCase("accept_request")) {
                        if (args[2].equalsIgnoreCase(request_id.toString())) {
                            if (tp_cooldown > 0) {
                                target_1.teleport(target_2.getLocation());
                               sender.sendMessage(ChatColor.GOLD + "[AH-TP] " + ChatColor.YELLOW + "Teleported "+ target_1.getName() +" to " + target_2.getName());

                                Bukkit.getScheduler().cancelTask(tp_request_id);
                                tp_cooldown = 0;
                            } else {
                               sender.sendMessage(ChatColor.GOLD + "[AH-TP] " + ChatColor.RED + "This request has already expired");
                            }
                        } else {
                           sender.sendMessage(ChatColor.GOLD + "[AH-TP] " + ChatColor.RED + "This request has already expired");
                        }

                    } else {

                        tp_request_sender = sender;
                        target_1 = Bukkit.getPlayer(args[1]);
                        target_2 = Bukkit.getPlayer(args[2]);

                        if(target_1 == null || target_2 == null) {
                            if (target_1 == null) {
                               sender.sendMessage(ChatColor.GOLD + "[AH-TP] " + ChatColor.RED + args[1] + " is not online");
                            }

                            if (target_2 == null) {
                               sender.sendMessage(ChatColor.GOLD + "[AH-TP] " + ChatColor.RED + args[2] + " is not online");
                            }

                        } else {

                            if (tp_cooldown < 1) {
                                request_id = UUID.randomUUID();
                            }


                            if (!sender.isOp() && player_handler.getRank(target_1) > player_handler.getRank(sender)) {

                                TextComponent accept_tp = new TextComponent(ChatColor.GREEN + "[Accept]  ");
                                TextComponent decline_tp = new TextComponent(ChatColor.RED + "[Decline]");

                                accept_tp.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/tp accept_request " + request_id));
                                decline_tp.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/tp cancel_request " + request_id));

                                if (tp_cooldown <= 0) {

                                   sender.sendMessage(ChatColor.GOLD + "[AH-TP] " + ChatColor.YELLOW + "Sent a TP Request to " + target_1.getName());
                                   sender.sendMessage(ChatColor.GOLD + "[AH-TP] " + ChatColor.YELLOW + "Request will expire in 30 seconds");



                                    target_1.sendMessage(ChatColor.GOLD + "[AH-TP] " + ChatColor.YELLOW +sender.getName() + " wants to Teleport you to " + target_2.getName());
                                    target_1.sendMessage(ChatColor.GOLD + "[AH-TP] " + ChatColor.YELLOW + "Request will expire in 30 seconds");
                                    target_1.spigot().sendMessage(accept_tp, decline_tp);


                                    //accept_tp.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, ChatColor.YELLOW + "The TP request has expired"));
                                    //decline_tp.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, ChatColor.YELLOW + "The TP request has expired"));




                                    new BukkitRunnable() {

                                        int tp_request_expire = 30;

                                        @Override
                                        public void run() {

                                            if (tp_request_expire == 30) {
                                                tp_request_id = this.getTaskId();
                                            }
                                            if (tp_request_expire <= 0) {
                                                this.cancel();
                                                target_1.sendMessage(ChatColor.GOLD + "[AH-TP] " + ChatColor.YELLOW + "The TP request from " +sender.getName() + " has expired");
                                                sender.sendMessage(ChatColor.GOLD + "[AH-TP] " + ChatColor.YELLOW + "The TP request to " + target_1.getName() + " has expired");

                                            }

                                            tp_request_expire--;
                                            tp_cooldown = tp_request_expire;
                                        }
                                    }.runTaskTimer(Main.plugin, 0, 20);



                                } else {
                                   sender.sendMessage(ChatColor.GOLD + "[AH-TP] " + ChatColor.YELLOW + "You have already sent a TP request. Try again in " + tp_cooldown + " seconds");
                                }

                            } else {

                                target_1.teleport(target_2.getLocation());
                               sender.sendMessage(ChatColor.GOLD + "[AH-TP] " + ChatColor.YELLOW + "Teleported "+ target_1.getName() +" to " + target_2.getName());
                            }

                        }
                    }


                }

            }


            if (args.length == 2) {

                Player target = Bukkit.getPlayer(args[1]);

                if(target != null) {

                   sender.teleport(target.getLocation());
                   sender.sendMessage(ChatColor.GOLD + "[AH-TP] " + ChatColor.YELLOW + "Teleported to " + target.getName());

                } else {

                   sender.sendMessage(ChatColor.GOLD + "[AH-TP] " + ChatColor.RED + args[1] + " is not online");
                }


            }

            if (args.length == 5) {

               sender.sendMessage(ChatColor.GOLD + "[AH-TP] " + ChatColor.RED + "Co-ordinates TP currently under development");

            }


        }

    }

}
