package axleham.core.hologram;

import axleham.core.format_msg.HelpManager;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class HologramCMD {

    public void hologramCMD(Player p, String[] args) {

        HologramHandler hologram_handler = new HologramHandler();

        HelpManager help_manager = new HelpManager();

        ArrayList<String> bullet_points = new ArrayList<String>();
        bullet_points.add("/hologram create <hologram name> <text>");
        bullet_points.add("/hologram delete <hologram name>");
        bullet_points.add("/hologram info <hologram name>");
        bullet_points.add("/hologram locate <hologram name>");
        bullet_points.add("/hologram move <hologram name>");
        bullet_points.add("/hologram list");

        String hologram_name = "";

        if (args.length > 1) {

            String hologram_option = args[1];

            if (args.length > 2) {

                hologram_name = args[2];

            }

            if (hologram_option.equalsIgnoreCase("create")) {

                if (args.length < 4) {

                    help_manager.helpMessage(p, "[Help]", "Command Usage: /hologram", bullet_points);

                } else {

                    String hologram_text = StringUtils.join(Arrays.copyOfRange(args, 3, args.length), " ");

                    String create_hologram = hologram_handler.recordHologram(p.getLocation(), hologram_name, hologram_text);
                    p.sendMessage(create_hologram);

                }

            } else if(hologram_option.equalsIgnoreCase("delete")) {

                if (args.length < 3) {

                    help_manager.helpMessage(p, "[Help]", "Command Usage: /hologram", bullet_points);

                } else {

                    hologram_handler.removeHologram(p, hologram_name);

                }

            } else if(hologram_option.equalsIgnoreCase("list")) {

                if (args.length < 2) {

                    help_manager.helpMessage(p, "[Help]", "Command Usage: /hologram", bullet_points);

                } else {

                    hologram_handler.listHologram(p);

                }


            } else if(hologram_option.equalsIgnoreCase("info")) {

                if (args.length < 3) {

                    help_manager.helpMessage(p, "[Help]", "Command Usage: /hologram", bullet_points);

                } else {

                    hologram_handler.infoHologram(p, args[2]);

                }


            } else if(hologram_option.equalsIgnoreCase("locate")) {

                if (args.length < 3) {

                    help_manager.helpMessage(p, "[Help]", "Command Usage: /hologram", bullet_points);

                } else {

                    hologram_handler.locateHologram(p, args[2]);

                }


            } else if(hologram_option.equalsIgnoreCase("move")) {

                if (args.length < 3) {

                    help_manager.helpMessage(p, "[Help]", "Command Usage: /hologram", bullet_points);

                } else {

                    hologram_handler.moveHologram(p, args[2]);

                }


            } else {

                help_manager.helpMessage(p, "[Help]", "Command Usage: /hologram", bullet_points);

            }

        } else {

            help_manager.helpMessage(p, "[Help]", "Command Usage: /hologram", bullet_points);

        }


    }

}
