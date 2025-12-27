package fr.mathip.azplugin.bukkit.utils;

import com.google.gson.*;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import pactify.client.api.mcprotocol.NotchianPacketBuffer;
import pactify.client.api.mcprotocol.model.NotchianChatComponent;
import pactify.client.api.mcprotocol.util.NotchianPacketUtil;

import java.util.ArrayList;
import java.util.List;

public class AZChatComponent implements NotchianChatComponent {
    private String text;
    private ClickEvent clickEvent;
    private HoverEvent hoverEvent;

    private List<AZChatComponent> extra;

    public AZChatComponent(String text) {
        this.text = text;
        this.extra = new ArrayList<>();
    }

    public AZChatComponent(TextComponent textComponent) {
        this.text = textComponent.getText();
        this.extra = new ArrayList<>();
        if (textComponent.getExtra() != null && textComponent.getExtra().size() != 0) {
            for (BaseComponent baseComponent : textComponent.getExtra()) {
                AZChatComponent azChatComponent = new AZChatComponent((TextComponent) baseComponent);
                this.extra.add(azChatComponent);
            }
        }
        if (textComponent.getClickEvent() != null) {
            this.clickEvent = new ClickEvent(textComponent.getClickEvent().getAction().name().toLowerCase(),
                    textComponent.getClickEvent().getValue());
        }
        if (textComponent.getHoverEvent() != null) {
            StringBuilder sb = new StringBuilder();
            for (BaseComponent baseComponent : textComponent.getHoverEvent().getValue()) {
                sb.append(baseComponent.toLegacyText());
            }
            this.hoverEvent = new HoverEvent(textComponent.getHoverEvent().getAction().name().toLowerCase(),
                    sb.toString());
        }
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public ClickEvent getClickEvent() {
        return clickEvent;
    }

    public HoverEvent getHoverEvent() {
        return hoverEvent;
    }

    public void setClickEvent(ClickEvent clickEvent) {
        this.clickEvent = clickEvent;
    }

    public void setHoverEvent(HoverEvent hoverEvent) {
        this.hoverEvent = hoverEvent;
    }

    public List<AZChatComponent> getExtra() {
        return extra;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(gson.toJson(this));
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        jsonObject.remove("extra");
        if (this.clickEvent == null) {
            jsonObject.remove("clickEvent");
        }
        if (this.hoverEvent == null) {
            jsonObject.remove("hoverEvent");
        }
        if (this.extra.size() > 0) {
            JsonArray jsonArray = new JsonArray();
            jsonArray.add(jsonObject);
            for (AZChatComponent chatComponent : this.extra) {
                JsonElement jsonElement1 = parser.parse(chatComponent.toString());
                // JsonObject jsonObject1 = jsonElement1.getAsJsonObject();
                jsonArray.add(jsonElement1);
            }

            return jsonArray.toString();
        } else {
            return jsonObject.toString();
        }
    }

    @Override
    public void write(NotchianPacketBuffer notchianPacketBuffer) {
        // notchianPacketBuffer.writeByte(1);
        // String jsonMessage = "{\"text\":\"Click
        // me!\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/help\"}}";
        NotchianPacketUtil.writeString(notchianPacketBuffer, this.toString(), 9999);
        // notchianPacketBuffer.writeByte(0);
    }

    @Override
    public NotchianChatComponent shallowClone() {
        try {
            return (AZChatComponent) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public static class ClickEvent {
        private String action;
        private String value;

        public ClickEvent(String action, String value) {
            this.action = action;
            this.value = value;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static class HoverEvent {
        private String action;
        private String value;

        public HoverEvent(String action, String value) {
            this.action = action;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getAction() {
            return action;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public void setAction(String action) {
            this.action = action;
        }
    }

    @Override
    public NotchianChatComponent deepClone() {
        try {
            return (AZChatComponent) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

}
