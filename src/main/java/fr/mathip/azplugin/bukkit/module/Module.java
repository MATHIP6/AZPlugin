package fr.mathip.azplugin.bukkit.module;

public interface Module {

    boolean isEnabled();

    void setEnable(boolean enable);

    String getConfigSection();

}
