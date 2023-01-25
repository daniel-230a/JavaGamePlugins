package axleham.core.npc;

import axleham.core.format_msg.HelpManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;


public class NPC_CMD {

    public void npcCMD(Player p, String[] args) {

        NPC_Handler npc_handler = new NPC_Handler();
        HelpManager help_manager = new HelpManager();

        ArrayList<String> bullet_points = new ArrayList<String>();
        bullet_points.add("/npc create <name> <skin>");
        bullet_points.add("/npc delete <npc name>");
        bullet_points.add("/npc info <npc name>");
        bullet_points.add("/npc locate <npc name>");
        bullet_points.add("/npc move <npc name>");
        bullet_points.add("/npc list");

        String npc_name = "";

        if (args.length > 1) {

            String npc_option = args[1];

            if (args.length > 2) {

                npc_name = args[2];

            }

            if (npc_option.equalsIgnoreCase("create") && args.length == 4) {

                //createNPC(p.getLocation(), args[2], args[3]);
                String create_npc = npc_handler.recordNPC(p.getLocation(), args[2], args[3], "");
                p.sendMessage(create_npc);

            } else if(npc_option.equalsIgnoreCase("delete")) {

                if (args.length < 3) {

                    help_manager.helpMessage(p, "[Help]", "Command Usage: /npc", bullet_points);

                } else {

                    npc_handler.removeNPC(p, npc_name);

                }

            } else if(npc_option.equalsIgnoreCase("list")) {

                if (args.length < 2) {

                    help_manager.helpMessage(p, "[Help]", "Command Usage: /npc", bullet_points);

                } else {

                    npc_handler.listNPC(p);

                }


            } else if(npc_option.equalsIgnoreCase("info")) {

                if (args.length < 3) {

                    help_manager.helpMessage(p, "[Help]", "Command Usage: /npc", bullet_points);

                } else {

                    npc_handler.infoNPC(p, args[2]);

                }


            } else if(npc_option.equalsIgnoreCase("locate")) {

                if (args.length < 3) {

                    help_manager.helpMessage(p, "[Help]", "Command Usage: /npc", bullet_points);

                } else {

                    npc_handler.locateNPC(p, args[2]);

                }


            } else if(npc_option.equalsIgnoreCase("move")) {

                if (args.length < 3) {

                    help_manager.helpMessage(p, "[Help]", "Command Usage: /npc", bullet_points);

                } else {

                    npc_handler.moveNPC(p, args[2]);

                }


            } else {

                help_manager.helpMessage(p, "[Help]", "Command Usage: /npc", bullet_points);

            }

        } else {

            help_manager.helpMessage(p, "[Help]", "Command Usage: /npc", bullet_points);

        }


    }

}
