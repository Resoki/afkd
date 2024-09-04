package fr.resoki.afkmining.Afkmining.afkminingFINAL;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.DecentHologramsAPI;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class HologramManager extends JavaPlugin {

    @Override
    public void onEnable() {
        Location location = new Location(getServer().getWorld("world"), -55, 79, 130); // Remplacez par votre position
        createTemporaryHologram("exampleHologram", location, "Hello, World!");
    }

    public void createTemporaryHologram(String name, Location location, String line) {
        Location aboveLocation = location.clone().add(0, 1, 0);

        if (DHAPI.getHologram(name) != null) {
            getLogger().warning("Hologram with this name already exists.");
            return;
        }

        DHAPI.createHologram(name, aboveLocation, Arrays.asList(line));

        new BukkitRunnable() {
            @Override
            public void run() {
                if (DHAPI.getHologram(name) != null) DHAPI.removeHologram(name);
            }
        }.runTaskLater(this, 20L); // 20 ticks = 1 seconde
    }
}
