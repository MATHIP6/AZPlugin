package fr.mathip.azplugin.bukkit.head;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.mathip.azplugin.bukkit.Main;
import fr.mathip.azplugin.bukkit.entity.AZPlayer;

public class HeadMenu implements Listener {

    private static final String MENU_TITLE = "Têtes custom";
    private static final int ROWS = 5;
    private static final int HEAD_SLOTS = 27;
    private static final int PREV_BUTTON_SLOT = 30;
    private static final int NEXT_BUTTON_SLOT = 32;

    private final HeadApiClient apiClient;
    private final Map<UUID, Integer> playerPages = new HashMap<>();
    private final Set<UUID> openMenus = new HashSet<>();

    public HeadMenu(HeadApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public void open(Player player) {
        open(player, 1);
    }

    public void open(Player player, int page) {
        playerPages.put(player.getUniqueId(), page);

        AZPlayer azPlayer = Main.getAZManager().getPlayer(player);
        if (azPlayer != null) {
            azPlayer.openLoadingScreen();
        }

        apiClient.fetchHeads(page).thenAccept(response -> {
            Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                if (!player.isOnline()) return;

                if (azPlayer != null) {
                    azPlayer.closeLoadingScreen();
                }

                if (response == null || response.getHeads().isEmpty()) {
                    player.sendMessage("§cErreur: Impossible de charger les têtes depuis Internet");
                    return;
                }

                Inventory inventory = Bukkit.createInventory(null, ROWS * 9, MENU_TITLE);

                ItemStack filler = createFiller();
                for (int i = 0; i < ROWS * 9; i++) {
                    inventory.setItem(i, filler.clone());
                }

                for (int i = 0; i < response.getHeads().size() && i < HEAD_SLOTS; i++) {
                    HeadData head = response.getHeads().get(i);
                    ItemStack headItem = HeadItemFactory.createFromBase64(head.getValue());
                    inventory.setItem(i, headItem);
                }

                inventory.setItem(PREV_BUTTON_SLOT, createNavigationButton("§a§l⟨ §aPage précédente"));
                inventory.setItem(NEXT_BUTTON_SLOT, createNavigationButton("§aPage suivante §l⟩"));

                if (page <= 1) {
                    inventory.setItem(PREV_BUTTON_SLOT, createDisabledButton("§7§l⟨ §8Page précédente"));
                }
                if (page >= response.getTotalPages()) {
                    inventory.setItem(NEXT_BUTTON_SLOT, createDisabledButton("§8Page suivante §7§l⟩"));
                }

                ItemMeta nextMeta = inventory.getItem(NEXT_BUTTON_SLOT).getItemMeta();
                if (nextMeta != null && page < response.getTotalPages()) {
                    String pageInfo = "§ePage " + page + "/" + response.getTotalPages();
                    java.util.List<String> lore = new java.util.ArrayList<>();
                    lore.add(pageInfo);
                    lore.add("§7" + response.getTotal() + " tête(s) au total");
                    nextMeta.setLore(lore);
                    inventory.getItem(NEXT_BUTTON_SLOT).setItemMeta(nextMeta);
                }

                openMenus.add(player.getUniqueId());
                player.openInventory(inventory);
            });
        });
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
        if (slot < 0 || slot >= event.getView().getTopInventory().getSize())
            return;

        event.setCancelled(true);

        if (slot == PREV_BUTTON_SLOT) {
            int currentPage = playerPages.getOrDefault(player.getUniqueId(), 1);
            if (currentPage > 1) {
                open(player, currentPage - 1);
            }
        } else if (slot == NEXT_BUTTON_SLOT) {
            int currentPage = playerPages.getOrDefault(player.getUniqueId(), 1);
            Inventory inventory = event.getView().getTopInventory();
            ItemStack nextButton = inventory.getItem(NEXT_BUTTON_SLOT);
            if (nextButton != null && nextButton.getType() != Material.STAINED_GLASS_PANE) {
                open(player, currentPage + 1);
            }
        } else if (slot < HEAD_SLOTS) {
            event.setCancelled(false);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player))
            return;
        Player player = (Player) event.getPlayer();
        if (!event.getView().getTopInventory().getTitle().equals(MENU_TITLE))
            return;
        openMenus.remove(player.getUniqueId());
        playerPages.remove(player.getUniqueId());
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;
        Player player = (Player) event.getWhoClicked();
        if (!openMenus.contains(player.getUniqueId()))
            return;
        if (!event.getView().getTopInventory().getTitle().equals(MENU_TITLE))
            return;
        event.setCancelled(true);
    }

    private ItemStack createNavigationButton(String name) {
        ItemStack button = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
        ItemMeta meta = button.getItemMeta();
        meta.setDisplayName(name);
        button.setItemMeta(meta);
        return button;
    }

    private ItemStack createDisabledButton(String name) {
        ItemStack button = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
        ItemMeta meta = button.getItemMeta();
        meta.setDisplayName(name);
        button.setItemMeta(meta);
        return button;
    }

    private ItemStack createFiller() {
        ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemMeta meta = filler.getItemMeta();
        meta.setDisplayName(" ");
        filler.setItemMeta(meta);
        return filler;
    }

    public Set<UUID> getOpenMenus() {
        return openMenus;
    }
}
