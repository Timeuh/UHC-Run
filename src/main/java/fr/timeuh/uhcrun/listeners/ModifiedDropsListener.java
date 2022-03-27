package fr.timeuh.uhcrun.listeners;

import fr.timeuh.uhcrun.UHCRun;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class ModifiedDropsListener implements Listener {
    private UHCRun uhcRun;

    public ModifiedDropsListener(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
    }

    @EventHandler
    public void onMobKill(EntityDeathEvent event){
        Entity mob = event.getEntity();
        World world = event.getEntity().getWorld();
        ItemStack leather = new ItemStack(Material.LEATHER);
        ItemStack beef = new ItemStack(Material.COOKED_BEEF, 2);
        ItemStack chicken = new ItemStack(Material.RAW_CHICKEN);
        ItemStack bow = new ItemStack(Material.BOW, 1, (short) 200);
        ItemStack arrows = new ItemStack(Material.ARROW, 4);
        Location mobDeathPoint = mob.getLocation();
        Random chance = new Random();

        if (mob instanceof Cow){
            world.dropItemNaturally(mobDeathPoint, leather);
        } else if (mob instanceof Sheep){
            event.getDrops().removeAll(event.getDrops());
            world.dropItemNaturally(mobDeathPoint, leather);
            world.dropItemNaturally(mobDeathPoint, beef);
        } else if (mob instanceof Chicken){
            event.getDrops().remove(chicken);
            world.dropItemNaturally(mobDeathPoint, leather);
            world.dropItemNaturally(mobDeathPoint, beef);
        } else if (mob instanceof Pig){
            event.getDrops().removeAll(event.getDrops());
            world.dropItemNaturally(mobDeathPoint, leather);
            world.dropItemNaturally(mobDeathPoint, beef);
        } else if (mob instanceof Zombie){
            world.dropItemNaturally(mobDeathPoint, leather);
        } else if (mob instanceof Skeleton){
            event.getDrops().removeAll(event.getDrops());
            world.dropItemNaturally(mobDeathPoint, arrows);
            int bowChance = chance.nextInt(2)+1;
            if (bowChance == 2) world.dropItemNaturally(mobDeathPoint, bow);
        }
    }
}
