package fr.mathip.azplugin.bukkit;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import fr.mathip.azplugin.bukkit.azclientapi.AZPlayer;
import fr.mathip.azplugin.bukkit.azclientapi.handlers.PLSPConfFlag;
import fr.mathip.azplugin.bukkit.azclientapi.handlers.PLSPConfInt;
import fr.mathip.azplugin.bukkit.azclientapi.packets.player.PacketConf;
import fr.mathip.azplugin.bukkit.azclientapi.packets.player.PacketWindow;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
        if (main.isUpdate && main.updateMessage && player.hasPermission("azplugin.update")) {
            player.sendMessage("§6Une nouvelle version du §bAZPlugin§6 a été détecté !");
            player.sendMessage("§bhttps://www.spigotmc.org/resources/azplugin.115548/");
        }
    }

    public AZListener(Main main) {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(main, PacketType.Play.Server.SPAWN_ENTITY, PacketType.Play.Server.SPAWN_ENTITY_LIVING, PacketType.Play.Server.SPAWN_ENTITY_EXPERIENCE_ORB, PacketType.Play.Server.NAMED_ENTITY_SPAWN) {
            @Override
            public void onPacketSending(PacketEvent event) {
                int entityId = event.getPacket().getIntegers().read(0);
                Player player = event.getPlayer();
                Entity entity = event.getPacket().getEntityModifier(player.getWorld()).read(0);
                if (entity instanceof Player) {
                    Main.getInstance().sendPLSPPackets((Player) entity, player);
                }
            }
        });
    }

    @EventHandler
    void OnInventoryOpen(InventoryOpenEvent e) {
        if (e.getPlayer() instanceof Player) {
            Player p = (Player) e.getPlayer();
            if (e.getInventory().getTitle().contains(Main.getInstance().specialInventoryCharacter))
                PacketWindow.customWindow.add(p.getUniqueId());
        }
    }

}
