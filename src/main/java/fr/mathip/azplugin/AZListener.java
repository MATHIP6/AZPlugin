package fr.mathip.azplugin;

import fr.speccy.azclientapi.bukkit.AZClientPlugin;
import fr.speccy.azclientapi.bukkit.AZManager;
import fr.speccy.azclientapi.bukkit.AZPlayer;
import fr.speccy.azclientapi.bukkit.handlers.PLSPConfFlag;
import fr.speccy.azclientapi.bukkit.handlers.PLSPConfInt;
import fr.speccy.azclientapi.bukkit.packets.player.PacketConf;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import pactify.client.api.plsp.packet.client.PLSPPacketConfFlag;

public class AZListener implements Listener {

    @EventHandler
    void onQuit(PlayerQuitEvent e){
        Main main = Main.getInstance();
        Player p = e.getPlayer();
        if (main.playersScale.containsKey(p)){
            main.playersScale.remove(p);
        }if (main.playersTag.containsKey(p)){
            main.playersTag.remove(p);
        }if (main.playersSubTag.containsKey(p)){
            main.playersSubTag.remove(p);
        }if (main.playersSupTag.containsKey(p)){
            main.playersSupTag.remove(p);
        }if (main.playersOpacity.containsKey(p)){
            main.playersOpacity.remove(p);
        }if (main.playersModel.containsKey(p)){
            main.playersModel.remove(p);
        }if (main.playersSeeChunks.contains(p)) {
            main.playersSeeChunks.remove(p);
        }
    }

    @EventHandler
    void onJoint(PlayerJoinEvent e){
        Player player = e.getPlayer();
        Main main = Main.getInstance();
        if (AZPlayer.hasAZLauncher(player)){
            if (main.joinWithAZCommands != null) {
                main.joinWithAZCommands.forEach(command -> {
                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command.replaceAll("%player%", player.getName()));
                });
            }
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
                @Override
                public void run() {
                    PacketConf.setFlag(player, PLSPConfFlag.ATTACK_COOLDOWN, main.attackCooldown);
                    PacketConf.setFlag(player, PLSPConfFlag.PLAYER_PUSH, main.playerPush);
                    PacketConf.setFlag(player, PLSPConfFlag.LARGE_HITBOX, main.largeHitBox);
                    PacketConf.setFlag(player, PLSPConfFlag.SWORD_BLOCKING, main.swordBlocking);
                    PacketConf.setFlag(player, PLSPConfFlag.HIT_AND_BLOCK, main.hitAndBlock);
                    PacketConf.setFlag(player, PLSPConfFlag.OLD_ENCHANTEMENTS, main.oldEnchantments);
                    PacketConf.setFlag(player, PLSPConfFlag.SIDEBAR_SCORES, main.sidebarScore);
                    PacketConf.setFlag(player, PLSPConfFlag.PVP_HIT_PRIORITY, main.pvpHitPriority);
                    PacketConf.setFlag(player, PLSPConfFlag.SEE_CHUNKS, main.seeChunks);
                    PacketConf.setFlag(player, PLSPConfFlag.SMOOTH_EXPERIENCE_BAR, main.smoothExperienceBar);
                    PacketConf.setFlag(player, PLSPConfFlag.SORT_TAB_LIST_BY_NAMES, main.sortTabListByName);
                    PacketConf.setFlag(player, PLSPConfFlag.SERVER_SIDE_ANVIL, main.serverSideAnvil);
                    PacketConf.setFlag(player, PLSPConfFlag.PISTONS_RETRACT_ENTITIES, main.pistonRetractEnvities);
                    PacketConf.setFlag(player, PLSPConfFlag.HIT_INDICATOR, main.hitIndicator);

                    PacketConf.setInt(player, PLSPConfInt.CHAT_MESSAGE_MAX_SIZE, main.chatMaxMessageSize);
                    PacketConf.setInt(player, PLSPConfInt.MAX_BUILD_HEIGHT, main.maxBuildHeight);
                }
            }, 1);
        } else {
            if (main.joinWithoutAZCommands != null) {
                main.joinWithoutAZCommands.forEach(command -> {
                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command.replaceAll("%player%", player.getName()));
                });
            }
        }
    }

}
