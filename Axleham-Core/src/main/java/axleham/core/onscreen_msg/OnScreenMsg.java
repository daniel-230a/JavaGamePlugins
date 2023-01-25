package axleham.core.onscreen_msg;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class OnScreenMsg {

    public void sendOnScreenMsg(String msg, int duration_sec, Player p) {

        PacketPlayOutTitle title_msg_packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE,
                IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + msg + "\"}"),20, duration_sec * 20,20);

        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(title_msg_packet);

    }

}
