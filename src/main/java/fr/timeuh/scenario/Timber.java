package fr.timeuh.scenario;

import fr.timeuh.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
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
    private List<BlockFace> faces;
    private List<Material> logList;

    public Timber(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
        this.logs = getLogs();
        this.faces = uhcRun.getChop().getFaces();
        this.logList = getLogs();
    }

    @EventHandler
    public void treeChop(BlockBreakEvent event){
        if (uhcRun.getScenario().isEnabled(Scenario.TIMBER)){
            Block block = event.getBlock();
            Block top = null;
            for (Material log : logs){
                if (block.getType().equals(log)){
                    String treeType = uhcRun.getChop().getTreeType(block);
                    if (treeType.equals("Big")){
                        top = getTreeTop(block);
                        event.setCancelled(true);
                        breakBigTree(block);
                        if (top != null) uhcRun.getChop().breakLeaves(top,3);
                    } else if (treeType.equals("Thin")){
                        top = getTreeTop(block);
                        event.setCancelled(true);
                        breakThinTree(block);
                        if (top != null) uhcRun.getChop().breakLeaves(top,2);
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

    private void breakThinTree(Block block){
        Block foot = uhcRun.getChop().getTreeFoot(block);
        Block up = foot;
        while (!(up.getType().equals(Material.LEAVES_2) || up.getType().equals(Material.LEAVES))){
            up = up.getRelative(BlockFace.UP);
            foot.setType(Material.AIR);
            Bukkit.getWorld("world").dropItem(foot.getLocation(), new ItemStack(Material.LOG));
            foot = up;
        }
    }

    public void breakBigTree(Block current){
        List<Block> blockBreak = new ArrayList<>();
        World world = Bukkit.getWorld("world");

        int minYLayer = current.getY() - 10;
        int yLayer = current.getY();
        int xLayer = current.getX();
        int zLayer = current.getZ();

        for (int y = minYLayer; y <= yLayer + 20; y++){
            for (int x = xLayer -3; x <= xLayer +3; x++){
                for (int z = zLayer -3; z <= zLayer +3; z++){
                    Block move = world.getBlockAt(x, y, z);
                    if (logList.contains(move.getType())) blockBreak.add(move);
                }
            }
        }
        for (Block block : blockBreak){
            block.setType(Material.AIR);
            world.dropItem(block.getLocation(), new ItemStack(Material.LOG));
        }
    }

    private Block getTreeTop(Block block){
        while (!(block.getRelative(BlockFace.UP).getType().equals(Material.LEAVES) || block.getRelative(BlockFace.UP).getType().equals(Material.LEAVES_2))){
            block = block.getRelative(BlockFace.UP);
        }
        return block;
    }
}
