package com.majway.coinshot;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

import static org.bukkit.ChatColor.*;

public class PlayerInteract implements Listener {

    NamespacedKey uuidKey = new NamespacedKey(Coinshot.getInstance(), "coinshot-uuid-key");
    NamespacedKey locationKey = new NamespacedKey(Coinshot.getInstance(), "coinshot-location-key");

    @EventHandler
    public void onPlayerPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();

        //p.sendMessage("Block place event");
        if (b.getType() == Material.BIRCH_SIGN) {
            UUID uuid = UUID.randomUUID();
            Bukkit.getLogger().info("Got ID ready: " + uuid.toString());
            Sign sign = (Sign) b.getState();

            sign.getPersistentDataContainer().set(uuidKey, new UUIDDataType(), uuid);

            Location signLocation = sign.getLocation();
            Bukkit.getLogger().info("Location: " + signLocation.toString());

            int[] buildingLocation = new int[3];
            buildingLocation[0] = signLocation.getBlockX();
            buildingLocation[1] = signLocation.getBlockY();
            buildingLocation[2] = signLocation.getBlockZ();
            sign.getPersistentDataContainer().set(locationKey, PersistentDataType.INTEGER_ARRAY, buildingLocation);

            sign.setEditable(true);
            if(p.hasPermission("signs.all")) {
                sign.setLine(0, GOLD + "Coming Soon");
            } else {
                sign.setLine(0, "Coming Soon");
            }
            sign.setLine(1, "Owner:");
            sign.setLine(2, p.getDisplayName());

            sign.update();
            p.sendMessage(b.getType().toString());
            Bukkit.getLogger().info("Placed sign: " + uuidKey.toString());
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block b = e.getClickedBlock();
        Action a = e.getAction();

        if (a.equals(Action.LEFT_CLICK_BLOCK) | a.equals(Action.RIGHT_CLICK_BLOCK)) {
            if (b.getType() == Material.BIRCH_SIGN) {
                Sign sign = (Sign) b.getState();
                PersistentDataContainer signContainer = sign.getPersistentDataContainer();

                if(signContainer.has(uuidKey , new UUIDDataType())) {
                    UUID buildingId = signContainer.get(uuidKey, new UUIDDataType());
                    int[] buildingLocation = signContainer.get(locationKey, PersistentDataType.INTEGER_ARRAY);

                    p.sendMessage("You clicked this block: " + buildingId.toString());
                    p.sendMessage(String.format(
                            "Location x:%s y:%s, z:%s",
                            buildingLocation[0],
                            buildingLocation[1],
                            buildingLocation[2]
                    ));
                }
            }
        }
    }

    //TODO: Filter for special tag for CS plot marker signs, otherwise scope will be too broad (all signs, all time)
    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        if (e.getPlayer().hasPermission("signs.all")) {
            for (int i = 0; i < 4; i++) {
                String line = e.getLine(i);
                if (line != null && !line.equals("")) {
                    if (i == 0) {
                        line = UNDERLINE + line;
                        line = GOLD + line;
                        line = BOLD + line;
                        e.setLine(i, line );
                    }
                }
            }
        }
    }
}
