package fr.mathip.azplugin.bukkit.packets;

import fr.mathip.azplugin.bukkit.AZManager;
import fr.mathip.azplugin.bukkit.handlers.PopupType;
import fr.mathip.azplugin.bukkit.utils.AZChatComponent;
import org.bukkit.entity.Player;
import pactify.client.api.plsp.model.SimplePLSPRegex;
import pactify.client.api.plsp.packet.client.PLSPPacketPopupAlert;
import pactify.client.api.plsp.packet.client.PLSPPacketPopupConfirm;
import pactify.client.api.plsp.packet.client.PLSPPacketPopupPrompt;

public class PacketPopup {

    private String name;

    private PopupType type;

    private PLSPPacketPopupAlert popupAlert;
    private PLSPPacketPopupConfirm popupConfirm;
    private PLSPPacketPopupPrompt popupPrompt;

    private AZChatComponent textComponent;

    private AZChatComponent okComponent;
    private AZChatComponent cancelComponent;

    private String defaultValue;
    private boolean password;

    public PacketPopup(String name, PopupType type) {
        this.name = name;
        this.type = type;
        this.popupAlert = new PLSPPacketPopupAlert();
        this.popupConfirm = new PLSPPacketPopupConfirm();
        this.popupPrompt = new PLSPPacketPopupPrompt();
    }

    public PLSPPacketPopupAlert getPopupAlert() {
        return popupAlert;
    }

    public PLSPPacketPopupConfirm getPopupConfirm() {
        return popupConfirm;
    }

    public PLSPPacketPopupPrompt getPopupPrompt() {
        return popupPrompt;
    }

    public AZChatComponent getTextComponent() {
        return textComponent;
    }

    public AZChatComponent getOkComponent() {
        return okComponent;
    }

    public AZChatComponent getCancelComponent() {
        return cancelComponent;
    }

    public void setTextComponent(AZChatComponent textComponent) {
        this.textComponent = textComponent;
    }

    public void setOkComponent(AZChatComponent okComponent) {
        this.okComponent = okComponent;
    }

    public void setCancelComponent(AZChatComponent cancelComponent) {
        this.cancelComponent = cancelComponent;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public PopupType getType() {
        return type;
    }

    public boolean isPassword() {
        return password;
    }

    public void setPassword(boolean password) {
        this.password = password;
    }

    public void send(Player player) {
        for (AZChatComponent extra : textComponent.getExtra()) {
            extra.setText(extra.getText().replaceAll("%player%", player.getName()));
        }
        switch (type) {
            case ALERT:
                okComponent.getClickEvent().setValue(okComponent.getClickEvent().getValue().replaceAll("%player%", player.getName()));
                popupAlert.setText(textComponent);
                popupAlert.setCloseButton(okComponent);
                AZManager.sendPLSPMessage(player, popupAlert);
                break;
            case CONFIRM:
                if (okComponent.getClickEvent() != null) {
                    okComponent.getClickEvent().setValue(okComponent.getClickEvent().getValue().replaceAll("%player%", player.getName()));
                }
                if (cancelComponent.getClickEvent() != null) {
                    cancelComponent.getClickEvent().setValue(cancelComponent.getClickEvent().getValue().replaceAll("%player%", player.getName()));
                }
                popupConfirm.setText(textComponent);
                popupConfirm.setOkButton(okComponent);
                popupConfirm.setCancelButton(cancelComponent);
                AZManager.sendPLSPMessage(player, popupConfirm);
                break;
            case PROMPT:

                defaultValue = defaultValue.replaceAll("%player%", player.getName());
                SimplePLSPRegex regex = new SimplePLSPRegex(SimplePLSPRegex.Engine.RE2J, "(?s).*");
                popupPrompt.setText(textComponent);
                popupPrompt.setDefaultValue(defaultValue);
                popupPrompt.setPassword(password);
                popupPrompt.setFinalRegex(regex);
                popupPrompt.setTypingRegex(regex);
                popupPrompt.setOkButton(okComponent);
                popupPrompt.setCancelButton(cancelComponent);
                if (okComponent.getClickEvent() != null) {
                    okComponent.getClickEvent().setValue(okComponent.getClickEvent().getValue().replaceAll("%player%", player.getName()));
                    okComponent.getClickEvent().setValue(okComponent.getClickEvent().getValue().replaceAll("%value%", popupPrompt.getDefaultValue()));
                }
                if (cancelComponent.getClickEvent() != null) {
                    cancelComponent.getClickEvent().setValue(cancelComponent.getClickEvent().getValue().replaceAll("%player%", player.getName()));
                    cancelComponent.getClickEvent().setValue(cancelComponent.getClickEvent().getValue().replaceAll("%value%", popupPrompt.getDefaultValue()));
                }

                AZManager.sendPLSPMessage(player, popupPrompt);
                break;
        }
    }
}
