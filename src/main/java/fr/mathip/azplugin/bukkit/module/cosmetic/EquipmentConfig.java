package fr.mathip.azplugin.bukkit.module.cosmetic;

import org.bukkit.entity.Player;

import fr.mathip.azplugin.bukkit.entity.appearance.AZCosmeticEquipment;
import fr.mathip.azplugin.bukkit.entity.appearance.AZCosmeticEquipment.ItemMatchFlag;
import fr.mathip.azplugin.bukkit.entity.appearance.AZCosmeticEquipment.Symbol;
import fr.mathip.azplugin.bukkit.utils.AZChatComponent;
import lombok.Data;

@Data
public class EquipmentConfig {

    private String symbol;
    private String tooltipPrefixText;
    private String tooltipPrefixCommand;
    private String tooltipSuffixText;
    private String tooltipSuffixCommand;
    private ItemMatchFlag match;

    public AZCosmeticEquipment build(Player player) {
        AZCosmeticEquipment.Builder builder = AZCosmeticEquipment.builder();

        if (symbol != null && !symbol.isEmpty()) {
            builder.symbol(Symbol.valueOf(symbol));
        }

        if (tooltipPrefixText != null && !tooltipPrefixText.isEmpty()) {
            AZChatComponent prefix = new AZChatComponent(tooltipPrefixText.replace("%player%", player.getName()));
            if (tooltipPrefixCommand != null && !tooltipPrefixCommand.isEmpty()) {
                prefix.setClickEvent(new AZChatComponent.ClickEvent("run_command",
                        tooltipPrefixCommand.replace("%player%", player.getName())));
            }
            builder.tooltipPrefix(prefix);
        }

        if (tooltipSuffixText != null && !tooltipSuffixText.isEmpty()) {
            AZChatComponent suffix = new AZChatComponent(tooltipSuffixText.replace("%player%", player.getName()));
            if (tooltipSuffixCommand != null && !tooltipSuffixCommand.isEmpty()) {
                suffix.setClickEvent(new AZChatComponent.ClickEvent("run_command",
                        tooltipSuffixCommand.replace("%player%", player.getName())));
            }
            builder.tooltipSuffix(suffix);
        }

        if (match != null) {
            builder.itemMatchFlag(match);
        }

        return builder.build();
    }
}
