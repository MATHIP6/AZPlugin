package fr.mathip.azplugin.bukkit;

public class PacketUiComponent {

    private String text;
    private String name;
    private String hoverText;
    private String commmand;

    public PacketUiComponent(String text, String name, String hoverText, String commmand) {
        this.text = text;
        this.name = name;
        this.hoverText = hoverText;
        this.commmand = commmand;
    }

    public String getName() {
        return name;
    }

    public String getCommmand() {
        return commmand;
    }

    public String getHoverText() {
        return hoverText;
    }

    public String getText() {
        return text;
    }


}
