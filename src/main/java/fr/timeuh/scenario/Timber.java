package fr.timeuh.scenario;

import fr.timeuh.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Timber implements Listener {

    private UHCRun uhcRun;
    private List<Material> logs;

    public Timber(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
        this.logs = getLogs();
    }

    @EventHandler
    public void treeChop(BlockBreakEvent event){
        if (uhcRun.getScenario().isEnabled(Scenario.TIMBER)){
            Block block = event.getBlock();
            for (Material log : logs){
                if (block.getType().equals(log)){
                    String treeType = getTreeType(block);
                    if (treeType.equals("Big")){
                        event.setCancelled(true);
                        breakBigTree(block);
                    } else if (treeType.equals("Thin")){
                        event.setCancelled(true);
                        breakThinTree(block);
                    }
                }
            }
        }
    }

    private List<Material> getLogs(){
        List<Material> logs = new ArrayList<>();
        logs.add(Material.LOG);
        logs.add(Material.LOG_2);
        return logs;
    }

    private String getTreeType(Block block){
        if (block.getRelative(BlockFace.EAST).getType().equals(block.getType()) || block.getRelative(BlockFace.EAST).getType().equals(block.getType())
                || block.getRelative(BlockFace.NORTH).getType().equals(block.getType()) || block.getRelative(BlockFace.SOUTH).getType().equals(block.getType())){
            return "Big";
        } else {
            return "Thin";
        }
    }

    private void breakThinTree(Block block){
        Block foot = getTreeFoot(block);
        Block up = foot;
        while (!(up.getType().equals(Material.LEAVES_2) || up.getType().equals(Material.LEAVES))){
            up = up.getRelative(BlockFace.UP);
            foot.setType(Material.AIR);
            Bukkit.getWorld("world").dropItemNaturally(foot.getLocation(), new ItemStack(Material.LOG));
            foot = up;
        }
    }

    private void breakBigTree(Block block){
        Block foot = getTreeFoot(block);
    }

    private Block getTreeFoot(Block block){
        while (!(block.getRelative(BlockFace.DOWN).getType().equals(Material.DIRT) || block.getRelative(BlockFace.DOWN).getType().equals(Material.GRASS))){
            block = block.getRelative(BlockFace.DOWN);
        }
        return block;
    }
}
