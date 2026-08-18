package fr.mathip.azplugin.bukkit.module.cosmetic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.mathip.azplugin.bukkit.Main;
import fr.mathip.azplugin.bukkit.entity.AZPlayer;
import fr.mathip.azplugin.bukkit.entity.appearance.AZCosmeticEquipment;
import fr.mathip.azplugin.bukkit.entity.appearance.AZCosmeticEquipment.Slot;
import fr.mathip.azplugin.bukkit.utils.AZChatComponent;

import org.bukkit.event.Listener;

public class CosmeticMenu implements Listener {

    private static final String MENU_TITLE = "Cosm\u00e9tiques";
    private static final int ROWS = 3;

    private final CosmeticModule module;
    private final CosmeticDataStorage storage;
    private final Map<Slot, EquipmentConfig> equipmentConfigs;
    private final Set<UUID> openMenus = new HashSet<>();

    public CosmeticMenu(CosmeticModule module, CosmeticDataStorage storage,
            Map<Slot, EquipmentConfig> equipmentConfigs) {
        this.module = module;
        this.storage = storage;
        this.equipmentConfigs = equipmentConfigs;
    }

    public void open(Player player) {
        Map<Slot, Integer> layout = buildLayout();
        Inventory inventory = Bukkit.createInventory(null, ROWS * 9, MENU_TITLE);

        ItemStack glass = createGlassPane();
        for (int i = 0; i < ROWS * 9; i++) {
            inventory.setItem(i, glass.clone());
        }

        Map<Slot, ItemStack> playerCosmetics = loadSavedItems(player);

        for (Map.Entry<Slot, Integer> entry : layout.entrySet()) {
            if (playerCosmetics.containsKey(entry.getKey())) {
                inventory.setItem(entry.getValue(), playerCosmetics.get(entry.getKey()));
            } else {
                inventory.setItem(entry.getValue(), null);
            }
        }

        openMenus.add(player.getUniqueId());
        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;
        Player player = (Player) event.getWhoClicked();

        if (!openMenus.contains(player.getUniqueId()))
            return;
        if (!event.getView().getTopInventory().getTitle().equals(MENU_TITLE)) {
            openMenus.remove(player.getUniqueId());
            return;
        }

        int slot = event.getRawSlot();

        if (slot >= 0 && slot < event.getView().getTopInventory().getSize()) {
            if (isEquipmentSlot(slot)) {
                // event.setCancelled(true);
                if (event.isShiftClick()) {
                    event.setCancelled(true);
                }
            } else {
                event.setCancelled(true);
            }

        }

        // if (slot >= 0 && slot < event.getView().getTopInventory().getSize()) {
        // ItemStack clicked = event.getInventory().getItem(slot);
        // if (clicked != null && clicked.getType() == Material.STAINED_GLASS_PANE) {
        // event.setCancelled(true);
        // return;
        // }
        // if (isEquipmentSlot(slot)) {
        // event.setCancelled(true);
        // if (event.isShiftClick()) {
        // event.setCancelled(true);
        // }
        // } else {
        // event.setCancelled(true);
        // }
        // }
        // else {
        // event.setCancelled(true);
        // }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player))
            return;
        Player player = (Player) event.getPlayer();

        if (!openMenus.remove(player.getUniqueId()))
            return;
        if (!event.getView().getTopInventory().getTitle().equals(MENU_TITLE))
            return;

        Inventory inventory = event.getView().getTopInventory();
        Map<Slot, Integer> layout = buildLayout();
        Map<Slot, ItemStack> savedItems = new HashMap<>();

        AZPlayer azPlayer = Main.getAZManager().getPlayer(player);
        if (azPlayer == null)
            return;

        for (Map.Entry<Slot, Integer> entry : layout.entrySet()) {
            Slot slot = entry.getKey();
            int invSlot = entry.getValue();
            ItemStack item = inventory.getItem(invSlot);

            EquipmentConfig config = equipmentConfigs.get(slot);
            if (config != null) {
                azPlayer.setCosmeticEquipment(slot, config.build(player, item));
            }

            if (item != null && item.getType() != Material.AIR) {
                savedItems.put(slot, item);
            }
        }

        for (Slot configuredSlot : equipmentConfigs.keySet()) {
            if (!layout.containsKey(configuredSlot)) {
                azPlayer.setCosmeticEquipment(configuredSlot, AZCosmeticEquipment.builder()
                        .build());
            }
        }

        storage.save(player, savedItems);

        Bukkit.getScheduler().runTaskLaterAsynchronously(
                Main.getInstance(),
                () -> azPlayer.flush(),
                1L);
    }

    public Map<Slot, ItemStack> loadSavedItems(Player player) {
        return storage.load(player);
    }

    private Map<Slot, Integer> buildLayout() {
        Map<Slot, Integer> layout = new HashMap<>();

        layout.put(Slot.MAIN_HAND, 10);
        layout.put(Slot.HEAD, 11);
        layout.put(Slot.OFF_HAND, 12);

        layout.put(Slot.FEET, 19);
        layout.put(Slot.LEGS, 20);
        layout.put(Slot.CHEST, 21);

        layout.put(Slot.CUSTOM_1, 14);
        layout.put(Slot.CUSTOM_3, 15);
        layout.put(Slot.CUSTOM_5, 16);

        layout.put(Slot.CUSTOM_2, 23);
        layout.put(Slot.CUSTOM_4, 24);
        layout.put(Slot.CUSTOM_6, 25);

        layout.keySet().removeIf(slot -> !equipmentConfigs.containsKey(slot));
        return layout;
    }

    private boolean isEquipmentSlot(int slot) {
        return buildLayout().containsValue(slot);
    }

    private ItemStack createGlassPane() {
        ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
        ItemMeta meta = glass.getItemMeta();
        meta.setDisplayName(" ");
        glass.setItemMeta(meta);
        return glass;
    }

    public Set<UUID> getOpenMenus() {
        return openMenus;
    }
}
