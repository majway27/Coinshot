package com.majway.coinshot;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;


public class NewBuildingCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            ItemStack plotMarkerSign = new ItemStack(Material.SPRUCE_SIGN, 1);
            ItemMeta plotMarkerSignItemMeta = plotMarkerSign.getItemMeta();

            ArrayList<String> lore = new ArrayList<String>();
            lore.add("Place to start a new Coinshot Building");
            lore.add("This will be at the corner of the new building");
            plotMarkerSignItemMeta.setDisplayName("Coinshot Plot Marker");
            plotMarkerSignItemMeta.setLore(lore);

            plotMarkerSign.setItemMeta(plotMarkerSignItemMeta);
            player.getInventory().addItem(plotMarkerSign);
        }
        return true;
    }
}
