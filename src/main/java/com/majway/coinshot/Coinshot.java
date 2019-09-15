package com.majway.coinshot;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Coinshot extends JavaPlugin {

    private static Coinshot pluginCoinshot;
    private static Map<UUID,String> BusinessMap =
            new HashMap< UUID,String>();
    private static File businessConfigFile;
    private static FileConfiguration businessConfig;


    public static FileConfiguration getBusinessConfig() {
        return businessConfig;
    }

    private void createBusinessConfig() {
        businessConfigFile = new File(getDataFolder(), "coinshotBusiness.yml");
        if (!businessConfigFile.exists()) {
            businessConfigFile.getParentFile().mkdirs();
            saveResource("coinshotBusiness.yml", true);
        }

        businessConfig = new YamlConfiguration();
        try {
            businessConfig.load(businessConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static Coinshot getInstance() {
        return pluginCoinshot;
    }

    public static String getBusiness(UUID uuid) throws IOException {
        JavaPlugin.getPlugin(Coinshot.class).getLogger().info(String.format(
                "Request getBusiness: %s", uuid));
        if (BusinessMap.get(uuid) != null) {
            JavaPlugin.getPlugin(Coinshot.class).getLogger().info(String.format(
                    "Response getBusiness: %s", BusinessMap.get(uuid)));
            return BusinessMap.get(uuid);
        } else if (Coinshot.getBusinessConfig().getString(uuid.toString()) != null) {
            String savedValue = Coinshot.getBusinessConfig().getString(uuid.toString());
            updateBusiness(uuid, savedValue);
            return savedValue;
        } else {
            JavaPlugin.getPlugin(Coinshot.class).getLogger().info(
                    "Response getBusiness: Not found");
            return "Key not found";
        }
    }

    public static void updateBusiness(
            UUID uuid,
            String value) throws IOException {
        JavaPlugin.getPlugin(Coinshot.class).getLogger().info("Store value: " + value);
        BusinessMap.put(uuid, value);
        Coinshot.getBusinessConfig().set(uuid.toString(), value);
        Coinshot.getBusinessConfig().save("plugins/Coinshot/coinshotBusiness.yml");
    }

    @Override
    public void onEnable() {
        getLogger().info("onEnable is called!");
        createBusinessConfig();

        pluginCoinshot = this;
        BusinessMap.clear();

        this.getCommand("CoinshotNewBuilding").setExecutor(new NewBuildingCommand());
        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
    }
    @Override
    public void onDisable() {
        pluginCoinshot = null;
        BusinessMap.clear();

        getLogger().info("onDisable is called!");

    }
}
