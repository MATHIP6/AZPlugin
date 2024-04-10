package fr.mathip.azplugin;

import fr.speccy.azclientapi.bukkit.AZClientPlugin;
import fr.speccy.azclientapi.bukkit.AZManager;
import fr.speccy.azclientapi.bukkit.AZPlayer;
import fr.speccy.azclientapi.bukkit.handlers.PLSPConfFlag;
import fr.speccy.azclientapi.bukkit.packets.PacketConfFlag;
import fr.speccy.azclientapi.bukkit.packets.PacketEntityMeta;
import fr.speccy.azclientapi.bukkit.packets.PacketPlayerModel;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pactify.client.api.plsp.packet.client.PLSPPacketConfFlag;

public class AZListener implements Listener {

    @EventHandler
    void onQuit(PlayerQuitEvent e){
        Main main = Main.getInstance();
        if (main.playersScale.containsKey(e.getPlayer())){
            main.playersScale.remove(e.getPlayer());
        }if (main.playersTag.containsKey(e.getPlayer())){
            main.playersTag.remove(e.getPlayer());
        }if (main.playersOpacity.containsKey(e.getPlayer())){
            main.playersOpacity.remove(e.getPlayer());
        }if (main.playersModel.containsKey(e.getPlayer())){
            main.playersModel.remove(e.getPlayer());
        }
    }

    @EventHandler
    void onJoint(PlayerJoinEvent e){
        Player player = e.getPlayer();
        Main main = Main.getInstance();
        if (AZPlayer.hasAZLauncher(player)){
            main.joinWithAZCommands.forEach(command -> {
                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command.replaceAll("%player%", player.getName()));
            });
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
                @Override
                public void run() {
                    PacketConfFlag.setFlag(player, PLSPConfFlag.ATTACK_COOLDOWN, main.attackCooldown);
                    PacketConfFlag.setFlag(player, PLSPConfFlag.PLAYER_PUSH, main.playerPush);
                    PacketConfFlag.setFlag(player, PLSPConfFlag.LARGE_HITBOX, main.largeHitBox);
                    PacketConfFlag.setFlag(player, PLSPConfFlag.SWORD_BLOCKING, main.swordBlocking);
                    PacketConfFlag.setFlag(player, PLSPConfFlag.HIT_AND_BLOCK, main.hitAndBlock);
                    PacketConfFlag.setFlag(player, PLSPConfFlag.OLD_ENCHANTEMENTS, main.oldEnchantments);
                    PacketConfFlag.setFlag(player, PLSPConfFlag.SIDEBAR_SCORES, main.sidebarScore);
                    PacketConfFlag.setFlag(player, PLSPConfFlag.PVP_HIT_PRIORITY, main.pvpHitPriority);
                    PacketConfFlag.setFlag(player, PLSPConfFlag.SEE_CHUNKS, main.seeChunks);
                    PacketConfFlag.setFlag(player, PLSPConfFlag.SMOOTH_EXPERIENCE_BAR, main.smoothExperienceBar);
                    PacketConfFlag.setFlag(player, PLSPConfFlag.SORT_TAB_LIST_BY_NAMES, main.sortTabListByName);
                }
            }, 1);
        } else {
            main.joinWithoutAZCommands.forEach(command -> {
                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command.replaceAll("%player%", player.getName()));
            });
        }
    }

}
