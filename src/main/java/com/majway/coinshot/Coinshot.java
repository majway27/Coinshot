package com.majway.coinshot;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Coinshot extends JavaPlugin {

    private static Coinshot pluginCoinshot;

    @Override
    public void onEnable() {
        getLogger().info("onEnable is called!");

        pluginCoinshot = this;
        this.getCommand("CoinshotNewBuilding").setExecutor(new NewBuildingCommand());
    }
    @Override
    public void onDisable() {
        pluginCoinshot = null;
        getLogger().info("onDisable is called!");
    }

    public static Coinshot getInstance() {
        return pluginCoinshot;
    }
}
