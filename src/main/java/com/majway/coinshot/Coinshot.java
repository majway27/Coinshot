package com.majway.coinshot;

import org.bukkit.plugin.java.JavaPlugin;

public class Coinshot extends JavaPlugin {

    private static Coinshot pluginCoinshot;

    public static Coinshot getInstance() {
        return pluginCoinshot;
    }

    @Override
    public void onEnable() {
        getLogger().info("onEnable is called!");

        pluginCoinshot = this;
        this.getCommand("CoinshotNewBuilding").setExecutor(new NewBuildingCommand());
        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
    }
    @Override
    public void onDisable() {
        pluginCoinshot = null;
        getLogger().info("onDisable is called!");
    }
}
