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
    // This method is called, when somebody uses our command
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            ItemStack cornerStone = new ItemStack(Material.BEDROCK, 1);
            ItemMeta itemStackMeta = cornerStone.getItemMeta();

            itemStackMeta.setDisplayName("Coinshot Cornerstone");

            ArrayList<String> lore = new ArrayList<String>();

            lore.add("Place to start a new Coinshot Building");
            lore.add("This will be the cornerstone of the new building");

            itemStackMeta.setLore(lore);

            cornerStone.setItemMeta(itemStackMeta);
            player.getInventory().addItem(cornerStone);
        }
        return true;
    }
}
