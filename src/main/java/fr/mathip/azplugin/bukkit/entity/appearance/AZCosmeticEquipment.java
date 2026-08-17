package fr.mathip.azplugin.bukkit.entity.appearance;

import org.bukkit.inventory.ItemStack;

import fr.mathip.azplugin.bukkit.utils.AZChatComponent;
import fr.mathip.azplugin.bukkit.utils.notchian.AZItemStack;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pactify.client.api.mcprotocol.model.NotchianChatComponent;
import pactify.client.api.mcprotocol.model.NotchianItemStack;
import pactify.client.api.plprotocol.model.cosmetic.PactifyCosmeticEquipment;
import pactify.client.api.plprotocol.model.cosmetic.PactifyCosmeticEquipmentSlot;
import pactify.client.api.plprotocol.model.cosmetic.PactifyCosmeticEquipmentSymbol;

@Getter
@Builder(builderClassName = "Builder", toBuilder = true)
public final class AZCosmeticEquipment {

    private NotchianItemStack item;
    private boolean hideInInventory;
    private NotchianChatComponent tooltipPrefix;
    private NotchianChatComponent tooltipSuffix;
    private Symbol symbol;

    public PactifyCosmeticEquipment toPacket() {
        PactifyCosmeticEquipment equipment = new PactifyCosmeticEquipment();

        equipment.setItem(item);
        equipment.setHideInInventory(hideInInventory);
        equipment.setTooltipPrefix(tooltipPrefix);
        equipment.setTooltipSuffix(tooltipSuffix);
        if (symbol != null) {
            equipment.setSymbol(PactifyCosmeticEquipmentSymbol.valueOf(symbol.name()));
        }

        return equipment;
    }

    public static class Builder {

        private Builder item(NotchianItemStack item) {
            this.item = item;
            return this;
        }

        public Builder item(ItemStack item) {
            if (item == null) {
                this.item = null;
            } else {
                this.item = new AZItemStack(item);
            }
            return this;
        }

        private Builder tooltipPrefix(NotchianChatComponent tooltipPrefix) {
            this.tooltipPrefix = tooltipPrefix;
            return this;
        }

        public Builder tooltipPrefix(AZChatComponent tooltipPrefix) {
            this.tooltipPrefix = tooltipPrefix;
            return this;
        }

        private Builder tooltipSuffix(NotchianChatComponent tooltipSuffix) {
            this.tooltipSuffix = tooltipSuffix;
            return this;
        }

        public Builder tooltipSuffix(AZChatComponent tooltipSuffix) {
            this.tooltipSuffix = tooltipSuffix;
            return this;
        }
    }

    @Getter
    @AllArgsConstructor
    public enum Slot {
        // Vanilla
        MAIN_HAND(0, -1, 1, true),
        FEET(1, 0, 3, true),
        LEGS(2, 0, 2, true),
        CHEST(3, 0, 1, true),
        HEAD(4, 0, 0, true),
        OFF_HAND(5, 1, 1, true),

        // Custom
        CUSTOM_1(6, -1, 0, false),
        CUSTOM_2(7, 1, 0, false),
        CUSTOM_3(8, -1, 2, false),
        CUSTOM_4(9, 1, 2, false),
        CUSTOM_5(10, -1, 3, false),
        CUSTOM_6(11, 1, 3, false);

        private final int index;
        private final int posX;
        private final int posY;
        private final boolean vanilla;

        public PactifyCosmeticEquipmentSlot toPLSP() {
            return PactifyCosmeticEquipmentSlot.valueOf(this.name());
        }
    }

    @Getter
    @AllArgsConstructor
    public enum Symbol {
        SWORD(0),
        BOOTS(1),
        LEGGINGS(2),
        CHESTPLATE(3),
        HEAD(4),
        SHIELD(5),
        SPIRAL(6),
        SQUARE(7),
        TRIANGLE(8),
        CIRCLE(9),
        OCTAGON(10),
        RHOMBUS(11),
        HELMET(12),
        SHOVEL(13),
        PICKAXE(14),
        AXE(15),
        HOE(16),
        BOW(17),
        FISHING_ROD(18),
        FLINT_AND_STEEL(19),
        SHEARS(20),
        ELYTRA(21),
        BLOCK(22),
        INGOT(23),
        POTION(24),
        DUST(25);

        private final int id;
    }
}
