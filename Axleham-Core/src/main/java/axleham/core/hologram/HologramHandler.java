package axleham.core.hologram;

import axleham.core.format_msg.HelpManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class HologramHandler {

    private ArmorStand createHologram(Location location, String text) {

        ArmorStand hologram = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        hologram.setVisible(false);
        hologram.setCustomNameVisible(true);
        hologram.setGravity(false);
        hologram.setCustomName(text);

        return hologram;

    }

    private void removeHologram(String UUID) {

        for(Entity ent : Bukkit.getWorlds().get(0).getEntities()) {

            if(ent.getUniqueId().toString().equals(UUID)) {

                ent.remove();
            }
        }

    }

    public ArrayList<String> getHologramByID(String id) {

        Hologram hologram = new Hologram();

        ArrayList<String> hologram_records =  listHologram();

        ArrayList<String> hologram_data =  new ArrayList<String>();

        for(int i = 0; i < hologram_records.size(); i++) {

            String hologram_ID= hologram.getID(hologram_records.get(i));

            if (hologram_ID.equals(hologram_ID)) {

                hologram_data.add(hologram_records.get(i));

            }

        }

        return hologram_data;

    }

    public ArrayList<String> getHologramByName(String name) {

        Hologram hologram = new Hologram();

        ArrayList<String> hologram_records =  listHologram();

        ArrayList<String> hologram_data =  new ArrayList<String>();

        for(int i = 0; i < hologram_records.size(); i++) {

            String h_rec = hologram_records.get(i);
            String hologram_name = hologram.getName(h_rec);

            if (hologram_name.equals(name)) {

                hologram_data.add(h_rec);

            }

        }

        return hologram_data;
    }


    public String recordHologram(Location location, String name, String text) {

        Hologram hologram = new Hologram();

        ArrayList<String> hologram_records =  listHologram();
        ArrayList<String> duplicate_records =  getHologramByName(name);

        int holo_ID = 0;

        if (hologram_records.size() > 0) {

            String last_holo_ID = hologram_records.get(hologram_records.size()-1);

            holo_ID = Integer.parseInt(hologram.getID(last_holo_ID)) + 1;

        }

        if(duplicate_records.size() == 0) {

            String hologram_record = "Hologram_" + holo_ID;

            hologram.setID(hologram_record, String.valueOf(holo_ID));
            hologram.setName(hologram_record, name);

            ArmorStand hologram_item = createHologram(location, text);
            hologram.setLocX(hologram_record, String.valueOf(location.getX()));
            hologram.setLocY(hologram_record, String.valueOf(location.getY()));
            hologram.setLocZ(hologram_record, String.valueOf(location.getZ()));
            hologram.setUUID(hologram_record, hologram_item.getUniqueId().toString());


            hologram.setText(hologram_record, text);

            return hologram.saveHologram(hologram_record);

        } else if (duplicate_records.size() > 0) {

            return ChatColor.RED + "A hologram with the name " + "\"" + name  + "\"" + " already exists";

        } else {

            return ChatColor.RED + "Something went wrong while creating a hologram!";

        }

    }

    public void removeHologram(Player p, String name) {

        Hologram hologram = new Hologram();

        ArrayList<String> hologram_info =  getHologramByName(name);

        if(hologram_info.size() == 0) {

            p.sendMessage(ChatColor.RED + "A hologram with that name doesn't exist");

        } else {

            String hologram_uuid = hologram.getUUID(hologram_info.get(0));

            removeHologram(hologram_uuid);
            hologram.deleteHologram(hologram_info.get(0));

            p.sendMessage(ChatColor.GREEN + "\"" +  name + "\" hologram deleted");

        }

    }
    /*
    public void removeHologram(String id) {

        HelpManager help_manager = new HelpManager();

        ArrayList<String> bullet_points = new ArrayList<String>();


        HologramFileHandler hologram_file_handler = new HologramFileHandler();
        YamlConfiguration config = hologram_file_handler.getConfigYMLFile();

        ArrayList<String> hologram_records =  listHologram();
        ArrayList<String> duplicate_records =  new ArrayList<String>();

        for(int i = 0; i < hologram_records.size(); i++) {

            String hologram_name = config.get(hologram_records.get(i) + ".Name").toString();

            if (hologram_name.equals(name)) {

                duplicate_records.add((hologram_records.get(i)));

            }

        }

        if(duplicate_records.size() == 0) {

            p.sendMessage(ChatColor.RED + "A hologram with that name doesn't exist");

        } else if (duplicate_records.size() > 1) {

            for(int i = 0; i < duplicate_records.size(); i++) {

                String hologram_name = config.get(duplicate_records.get(i) + ".Name").toString();
                String hologram_id = config.get(duplicate_records.get(i) + ".holo_ID").toString();

                bullet_points.add("id: " + hologram_id + " - name: " + hologram_name);

            }

            help_manager.helpMessage(p, "[Hologram]", "Multiple Holograms Found", bullet_points);

        } else {

            try {

                String hologram_uuid = config.get(duplicate_records.get(0) + ".UUID").toString();

                deleteHologram(hologram_uuid);

                config.set(duplicate_records.get(0), null);
                config.save(hologram_file_handler.getConfigFile());

                p.sendMessage(ChatColor.GREEN + "\"" +  name + "\" hologram deleted");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }



    }*/

    //List Hologram

    public ArrayList<String> listHologram() {

        HologramFileHandler hologram_file_handler = new HologramFileHandler();
        YamlConfiguration config = hologram_file_handler.getConfigYMLFile();

        int hologram_record_count = config.getConfigurationSection("").getKeys(false).size();

        ArrayList<String> hologram_records = new ArrayList<String>();

        for(int i = 0; i < hologram_record_count; i++) {

            String hologram_record =  config.getConfigurationSection("").getKeys(false).toArray()[i].toString();

            hologram_records.add(hologram_record);

        }

        return hologram_records;


    }

    public void listHologram(Player p) {

        HelpManager help_manager = new HelpManager();

        Hologram hologram = new Hologram();

        ArrayList<String> hologram_records =  listHologram();
        ArrayList<String> bullet_points = new ArrayList<String>();
        if (hologram_records.size() > 0) {

            for(int i = 0; i < hologram_records.size(); i++) {

                String hologram_record = hologram_records.get(i);

                String hologram_name = hologram.getName(hologram_record);

                bullet_points.add("name: " + hologram_name);

            }

        } else {
            bullet_points.add("No Saved Holograms");
        }

        help_manager.helpMessage(p, "[Hologram]", "List", bullet_points);

    }

    public void infoHologram(Player p, String name) {

        HelpManager help_manager = new HelpManager();

        Hologram hologram = new Hologram();

        ArrayList<String> hologram_records =  listHologram();
        ArrayList<String> bullet_points = new ArrayList<String>();

        TextComponent hologram_location = new TextComponent(ChatColor.GREEN + "[Tp to Location]");
        TextComponent hologram_delete = new TextComponent(ChatColor.RED + "[Delete Hologram]");
        boolean hologram_exists = false;

        if (hologram_records.size() > 0) {

            for(int i = 0; i < hologram_records.size(); i++) {

                if (hologram.getName(hologram_records.get(i)).equals(name)) {

                    String hologram_record = hologram_records.get(i);

                    String hologram_name = hologram.getName(hologram_record);
                    String hologram_text = hologram.getText(hologram_record);

                    hologram_location.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/hologram locate " + hologram_name));
                    hologram_delete.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/hologram delete " + hologram_name));

                    bullet_points.add("name: " + hologram_name);
                    bullet_points.add("text: " + hologram_text);
                    hologram_exists = true;

                }

                if (bullet_points.size() == 0 && i ==  hologram_records.size()-1) {

                    bullet_points.add(ChatColor.RED + "Invalid Hologram name " + name);

                }

            }

        }  else {
            bullet_points.add(ChatColor.RED + "Invalid Hologram name " + name);
        }

        help_manager.helpMessage(p, "[Hologram]", "Info", bullet_points);

        if (hologram_exists) {
            p.spigot().sendMessage(hologram_location, hologram_delete);
        }


    }

    public void locateHologram(Player p, String name) {

        Hologram hologram = new Hologram();

        ArrayList<String> hologram_records =  listHologram();

        if (hologram_records.size() > 0) {

            for(int i = 0; i < hologram_records.size(); i++) {

                if (hologram.getName(hologram_records.get(i)).equals(name)) {

                    String hologram_record = hologram_records.get(i);

                    double hologram_loc_x = Double.parseDouble(hologram.getLocX(hologram_record));
                    double hologram_loc_y = Double.parseDouble(hologram.getLocY(hologram_record));
                    double hologram_loc_z = Double.parseDouble(hologram.getLocZ(hologram_record));

                    Location hologram_loc = new Location(p.getWorld(),hologram_loc_x,hologram_loc_y,hologram_loc_z);

                    p.teleport(hologram_loc);
                    break;
                }

                if (i ==  hologram_records.size()-1) {

                    p.sendMessage(ChatColor.RED + "Invalid Hologram name " + name);

                }

            }

        }  else {
            p.sendMessage(ChatColor.RED + "Invalid Hologram name " + name);
        }


    }

    public void moveHologram(Player p, String name) {

        Hologram hologram = new Hologram();

        ArrayList<String> hologram_records =  listHologram();

        if (hologram_records.size() > 0) {

            for(int i = 0; i < hologram_records.size(); i++) {

                if (hologram.getName(hologram_records.get(i)).equals(name)) {

                    String hologram_record = hologram_records.get(i);
                    Location p_location = p.getLocation();

                    String hologram_UUID = hologram.getUUID(hologram_record);
                    String hologram_name = hologram.getName(hologram_record);
                    String hologram_text = hologram.getText(hologram_record);

                    hologram.setLocX(hologram_record, String.valueOf(p_location.getX()));
                    hologram.setLocY(hologram_record, String.valueOf(p_location.getY()));
                    hologram.setLocZ(hologram_record, String.valueOf(p_location.getZ()));

                    removeHologram(hologram_UUID);
                    ArmorStand hologram_item = createHologram(p_location, hologram_text);

                    hologram.setUUID(hologram_record, hologram_item.getUniqueId().toString());
                    hologram.saveHologram(hologram_record);
                    p.sendMessage(ChatColor.GREEN + "\"" +  hologram_name + "\" location moved");

                    break;
                }

                if (i ==  hologram_records.size()-1) {

                    p.sendMessage(ChatColor.RED + "Invalid Hologram name " + name);

                }

            }

        }  else {
            p.sendMessage(ChatColor.RED + "Invalid Hologram name " + name);
        }


    }

    public String addLinesHologram(Player p, String name, String text) {

        Hologram hologram = new Hologram();

        ArrayList<String> hologram_record =  getHologramByName(name);

        if(hologram_record.size() == 0) {

            return ChatColor.RED + "A hologram with the name " + "\"" + name  + "\"" + " doesn't exists";

        } else if (hologram_record.size() > 0) {

            hologram.setText(hologram_record.get(0), text);

            return hologram.saveHologram(hologram_record.get(0));

        } else {

            return ChatColor.RED + "Something went wrong while creating a hologram!";

        }



    }


}
