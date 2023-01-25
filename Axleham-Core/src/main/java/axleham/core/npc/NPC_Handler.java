package axleham.core.npc;

import axleham.core.format_msg.HelpManager;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.NPC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NPC_Handler {

    private static List<EntityPlayer> NPC_list = new ArrayList<EntityPlayer>();


    public void getSavedNPCs() {

        removeAllNPC();
        ArrayList<String> npc_records =  listNPC();

        for(int i = 0; i < npc_records.size(); i++) {

            String npc_record = npc_records.get(i);

            net.minecraft.server.v1_8_R3.NPC npc = new net.minecraft.server.v1_8_R3.NPC();

            EntityPlayer add_npc = createNPC(npc.getLoc(npc_record), npc.getName(npc_record), npc.getSkin(npc_record));
            npc.setUUID(npc_record, add_npc.getUniqueID().toString());

            NPC_list.add(add_npc);


        }


    }

    public static EntityPlayer createNPC(Location location, String npc_name, String skin_name) {


        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) Bukkit.getWorld(location.getWorld().getName())).getHandle();
        GameProfile npc_profile = new GameProfile(UUID.randomUUID(), npc_name);
        EntityPlayer npc = new EntityPlayer(server, world, npc_profile, new PlayerInteractManager(world));

        String[] npc_skin = getSkin(skin_name);

        npc_profile.getProperties().put("textures", new Property("textures", npc_skin[0], npc_skin[1]));

        npc.setLocation(((int) location.getX()) - 0.5, location.getY(), ((int) location.getZ()) - 0.5, location.getYaw(), location.getPitch());
        NPC_list.add(npc);

        addNPCPacket(npc);

        return npc;
    }

    private void removeNPC(String UUID) {

        EntityPlayer remove_npc = null;

        for (EntityPlayer npc : NPC_list) {

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (UUID.equals(npc.getUniqueID().toString())) {

                    PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
                    connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
                    connection.sendPacket(new PacketPlayOutEntityDestroy(npc.getId()));
                    remove_npc = npc;

                }

            }

        }

        NPC_list.remove(remove_npc);


    }

    private void removeAllNPC() {

        for (EntityPlayer npc : NPC_list) {

            for (Player p : Bukkit.getOnlinePlayers()) {

                PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
                connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
                connection.sendPacket(new PacketPlayOutEntityDestroy(npc.getId()));


            }

        }

        NPC_list.clear();

    }

    public static void addNPCPacket(EntityPlayer npc) {

        for (Player p : Bukkit.getOnlinePlayers()) {

            PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
            connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw *256/360)));

        }

    }

    public static void addJoinPacket(Player p) {

        for (EntityPlayer npc : NPC_list) {

            PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
            connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw *256/360)));

        }

    }

    public ArrayList<String> getNPCByID(String id) {

        net.minecraft.server.v1_8_R3.NPC npc = new net.minecraft.server.v1_8_R3.NPC();

        ArrayList<String> npc_records =  listNPC();

        ArrayList<String> npc_data =  new ArrayList<String>();

        for(int i = 0; i < npc_records.size(); i++) {

            String npc_ID= npc.getID(npc_records.get(i));

            if (npc_ID.equals(npc_ID)) {

                npc_data.add(npc_records.get(i));

            }

        }

        return npc_data;

    }

    public ArrayList<String> getNPCByName(String name) {

        net.minecraft.server.v1_8_R3.NPC npc = new net.minecraft.server.v1_8_R3.NPC();

        ArrayList<String> npc_records =  listNPC();

        ArrayList<String> npc_data =  new ArrayList<String>();

        for(int i = 0; i < npc_records.size(); i++) {

            String h_rec = npc_records.get(i);
            String npc_name = npc.getName(h_rec);

            if (npc_name.equals(name)) {

                npc_data.add(h_rec);

            }

        }

        return npc_data;
    }

    public String recordNPC(Location location, String name, String skin_name, String text) {

        net.minecraft.server.v1_8_R3.NPC npc = new net.minecraft.server.v1_8_R3.NPC();

        ArrayList<String> NPC_records =  listNPC();
        ArrayList<String> duplicate_records =  getNPCByName(name);

        int NPC_ID = 0;

        if (NPC_records.size() > 0) {

            String last_NPC_ID = NPC_records.get(NPC_records.size()-1);

            NPC_ID = Integer.parseInt(npc.getID(last_NPC_ID)) + 1;

        }

        if(duplicate_records.size() == 0) {

            String NPC_record = "NPC_" + NPC_ID;

            npc.setID(NPC_record, String.valueOf(NPC_ID));
            npc.setName(NPC_record, name);

            EntityPlayer npc_entity = createNPC(location, name, skin_name);
            npc.setLocX(NPC_record, String.valueOf(((int)location.getX() - 0.5)));
            npc.setLocY(NPC_record, String.valueOf(location.getY()));
            npc.setLocZ(NPC_record, String.valueOf(((int) location.getZ()) - 0.5));
            npc.setLocYaw(NPC_record, String.valueOf(location.getYaw()));
            npc.setLocPitch(NPC_record, String.valueOf(location.getPitch()));
            npc.setUUID(NPC_record, npc_entity.getUniqueID().toString());


            npc.setText(NPC_record, text);
            npc.setSkin(NPC_record, skin_name);

            return npc.saveNPC(NPC_record);

        } else if (duplicate_records.size() > 0) {

            return ChatColor.RED + "An NPC with the name " + "\"" + name  + "\"" + " already exists";

        } else {

            return ChatColor.RED + "Something went wrong while creating an NPC!";

        }

    }

    public void removeNPC(Player p, String name) {

        net.minecraft.server.v1_8_R3.NPC npc = new net.minecraft.server.v1_8_R3.NPC();

        ArrayList<String> npc_info =  getNPCByName(name);

        if(npc_info.size() == 0) {

            p.sendMessage(ChatColor.RED + "An NPC with that name doesn't exist");

        } else {

            String npc_uuid = npc.getUUID(npc_info.get(0));

            removeNPC(npc_uuid);
            npc.deleteNPC(npc_info.get(0));

            p.sendMessage(ChatColor.GREEN + "\"" +  name + "\" NPC deleted");

        }

    }

    public ArrayList<String> listNPC() {

        NPC_FileHandler npc_file_handler = new NPC_FileHandler();
        YamlConfiguration config = npc_file_handler.getConfigYMLFile();

        int npc_record_count = config.getConfigurationSection("").getKeys(false).size();

        ArrayList<String> npc_records = new ArrayList<String>();

        for(int i = 0; i < npc_record_count; i++) {

            String npc_record =  config.getConfigurationSection("").getKeys(false).toArray()[i].toString();

            npc_records.add(npc_record);

        }

        return npc_records;


    }

    public void listNPC(Player p) {

        HelpManager help_manager = new HelpManager();

        net.minecraft.server.v1_8_R3.NPC npc = new net.minecraft.server.v1_8_R3.NPC();

        ArrayList<String> npc_records =  listNPC();
        ArrayList<String> bullet_points = new ArrayList<String>();
        if (npc_records.size() > 0) {

            for(int i = 0; i < npc_records.size(); i++) {

                String npc_record = npc_records.get(i);

                String npc_name = npc.getName(npc_record);

                bullet_points.add("name: " + npc_name);

            }

        } else {
            bullet_points.add("No Saved NPCs");
        }

        help_manager.helpMessage(p, "[NPC]", "List", bullet_points);

    }

    public void infoNPC(Player p, String name) {

        HelpManager help_manager = new HelpManager();

        net.minecraft.server.v1_8_R3.NPC npc = new net.minecraft.server.v1_8_R3.NPC();

        ArrayList<String> npc_records =  listNPC();
        ArrayList<String> bullet_points = new ArrayList<String>();

        TextComponent npc_location = new TextComponent(ChatColor.GREEN + "[Tp to Location]");
        TextComponent npc_delete = new TextComponent(ChatColor.RED + "[Delete NPC]");
        boolean npc_exists = false;

        if (npc_records.size() > 0) {

            for(int i = 0; i < npc_records.size(); i++) {

                if (npc.getName(npc_records.get(i)).equals(name)) {

                    String npc_record = npc_records.get(i);

                    String npc_name = npc.getName(npc_record);
                    String npc_text = npc.getText(npc_record);

                    npc_location.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/npc locate " + npc_name));
                    npc_delete.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/npc delete " + npc_name));

                    bullet_points.add("name: " + npc_name);
                    bullet_points.add("text: " + npc_text);
                    npc_exists = true;

                }

                if (bullet_points.size() == 0 && i ==  npc_records.size()-1) {

                    bullet_points.add(ChatColor.RED + "Invalid NPC name " + name);

                }

            }

        }  else {
            bullet_points.add(ChatColor.RED + "Invalid NPC name " + name);
        }

        help_manager.helpMessage(p, "[NPC]", "Info", bullet_points);

        if (npc_exists) {
            p.spigot().sendMessage(npc_location, npc_delete);
        }


    }

    public void locateNPC(Player p, String name) {

        net.minecraft.server.v1_8_R3.NPC npc = new net.minecraft.server.v1_8_R3.NPC();

        ArrayList<String> npc_records =  listNPC();

        if (npc_records.size() > 0) {

            for(int i = 0; i < npc_records.size(); i++) {

                if (npc.getName(npc_records.get(i)).equals(name)) {

                    String npc_record = npc_records.get(i);

                    double npc_loc_x = Double.parseDouble(npc.getLocX(npc_record));
                    double npc_loc_y = Double.parseDouble(npc.getLocY(npc_record));
                    double npc_loc_z = Double.parseDouble(npc.getLocZ(npc_record));
                    float npc_loc_yaw = Float.parseFloat(npc.getLocYaw(npc_record));
                    float npc_loc_pitch = Float.parseFloat(npc.getLocPitch(npc_record));

                    Location npc_loc = new Location(p.getWorld(),npc_loc_x,npc_loc_y,npc_loc_z, npc_loc_yaw,npc_loc_pitch);

                    p.teleport(npc_loc);
                    break;
                }

                if (i ==  npc_records.size()-1) {

                    p.sendMessage(ChatColor.RED + "Invalid NPC name " + name);

                }

            }

        }  else {
            p.sendMessage(ChatColor.RED + "Invalid NPC name " + name);
        }


    }

    public void moveNPC(Player p, String name) {

        net.minecraft.server.v1_8_R3.NPC npc = new NPC();

        ArrayList<String> npc_records =  listNPC();

        if (npc_records.size() > 0) {

            for(int i = 0; i < npc_records.size(); i++) {

                if (npc.getName(npc_records.get(i)).equals(name)) {

                    String npc_record = npc_records.get(i);
                    Location p_location = p.getLocation();

                    String npc_UUID = npc.getUUID(npc_record);
                    String npc_name = npc.getName(npc_record);
                    String npc_skin = npc.getSkin(npc_record);

                    npc.setLocX(npc_record, String.valueOf(p_location.getX()));
                    npc.setLocY(npc_record, String.valueOf(p_location.getY()));
                    npc.setLocZ(npc_record, String.valueOf(p_location.getZ()));

                    removeNPC(npc_UUID);
                    EntityPlayer npc_entity = createNPC(p_location, npc_name, npc_skin);

                    npc.setUUID(npc_record, npc_entity.getUniqueID().toString());
                    npc.saveNPC(npc_record);
                    p.sendMessage(ChatColor.GREEN + "\"" +  npc_name + "\" location moved");

                    break;
                }

                if (i ==  npc_records.size()-1) {

                    p.sendMessage(ChatColor.RED + "Invalid NPC name " + name);

                }

            }

        }  else {
            p.sendMessage(ChatColor.RED + "Invalid NPC name " + name);
        }


    }

    private static String[] getSkin(String name) {

        for(int i = 0; i <= NPC_Skins.values().length-1 ; i++) {

            NPC_Skins npc_skin = NPC_Skins.values()[i];

            if (npc_skin.getName().equalsIgnoreCase(name)) {

                return new String[] {npc_skin.getTexture(), npc_skin.getSignature()};

            } else if (i == npc_skin.values().length-1) {

                return new String[] {"", ""};

            }

        }

        return new String[] {"", ""};

        /*
        try {
            URL name_url = new URL("https://api.mojang.com/users/profiles/minecraft/"+ name);
            InputStreamReader name_url_reader = new InputStreamReader(name_url.openStream());
            String uuid = new JsonParser().parse(name_url_reader).getAsJsonObject().get("id").getAsString();

            URL uuid_url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            InputStreamReader uuid_url_reader = new InputStreamReader(uuid_url.openStream());

            JsonObject property = new JsonParser().parse(uuid_url_reader).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();

            String texture = property.get("value").getAsString();
            String signature = property.get("signature").getAsString();

            return new String[] {texture, signature};

        } catch(Exception e) {
            Bukkit.broadcastMessage("couldn't get skin");
            return new String[]{"", ""};
        }*/

    }

    public static List<EntityPlayer> getNPCs() {

        return NPC_list;

    }


}
