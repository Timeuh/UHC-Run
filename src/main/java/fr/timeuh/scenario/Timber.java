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
        this.faces = getFaces();
        this.logList = getLogList();
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
        Block foot = getTreeFoot(block);
        while  (!(foot.getRelative(BlockFace.UP).getType().equals(Material.LEAVES) || foot.getRelative(BlockFace.UP).getType().equals(Material.LEAVES_2))){
            for (BlockFace face : faces){
                if (foot.getRelative(face).getType().equals(foot.getType())) return "Big";
            }
            foot = foot.getRelative(BlockFace.UP);
        }
        return "Thin";
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
            world.dropItemNaturally(block.getLocation(), new ItemStack(Material.LOG));
        }
    }

    private Block getTreeFoot(Block block){
        while (!(block.getRelative(BlockFace.DOWN).getType().equals(Material.DIRT) || block.getRelative(BlockFace.DOWN).getType().equals(Material.GRASS))){
            block = block.getRelative(BlockFace.DOWN);
        }
        return block;
    }

    private List<BlockFace> getFaces(){
        List<BlockFace> allFaces = new ArrayList<>();
        allFaces.add(BlockFace.EAST);
        allFaces.add(BlockFace.EAST_NORTH_EAST);
        allFaces.add(BlockFace.EAST_SOUTH_EAST);
        allFaces.add(BlockFace.NORTH_EAST);
        allFaces.add(BlockFace.NORTH_NORTH_EAST);
        allFaces.add(BlockFace.SOUTH_EAST);
        allFaces.add(BlockFace.SOUTH_SOUTH_EAST);

        allFaces.add(BlockFace.WEST);
        allFaces.add(BlockFace.WEST_NORTH_WEST);
        allFaces.add(BlockFace.WEST_SOUTH_WEST);
        allFaces.add(BlockFace.NORTH_NORTH_WEST);
        allFaces.add(BlockFace.NORTH_WEST);
        allFaces.add(BlockFace.SOUTH_SOUTH_WEST);
        allFaces.add(BlockFace.SOUTH_WEST);

        allFaces.add(BlockFace.NORTH);
        allFaces.add(BlockFace.SOUTH);
        return allFaces;
    }

    private List<Material> getLogList(){
        List<Material> logType = new ArrayList<>();
        logType.add(Material.LOG);
        logType.add(Material.LOG_2);
        return logType;
    }
}
