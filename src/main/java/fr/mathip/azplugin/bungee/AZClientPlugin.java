package fr.mathip.azplugin.bungee;

import net.md_5.bungee.api.plugin.Plugin;

public class AZClientPlugin extends Plugin {

    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerListener(this, new HandshakeFix());
        getLogger().info("Plugin is now loaded!");
    }

    @Override
    public void onDisable() {

    }
}
