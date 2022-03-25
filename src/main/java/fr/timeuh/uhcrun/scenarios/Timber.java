package fr.timeuh.uhcrun.scenarios;

import fr.timeuh.uhcrun.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Timber implements Listener {
    private UHCRun uhcRun;
    private List<Material> logList;
    private List<Material> leavesList;

    public Timber(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
        this.logList = getValidLog();
        this.leavesList = getValidLeave();
    }

    @EventHandler
    public void onBreakTree(BlockBreakEvent event){
        if (uhcRun.checkEnabledScenario(Scenarios.TIMBER)){
            World world = Bukkit.getWorld("world");
            Block current = event.getBlock();
            Chunk source = Bukkit.getWorld("world").getChunkAt(current);
            if (logList.contains(current.getType())){
                breakTree(current, source);
                Bukkit.getScheduler().runTaskLater(uhcRun, () -> breakLeaves(current, source, world), 10);
            }
        }
    }

    public void breakTree(Block current, Chunk source){
        List<Block> blockBreak = new ArrayList<>();
        int minYLayer = current.getY() - 10;
        int yLayer = current.getY();
        int xLayer = current.getX();
        int zLayer = current.getZ();

        for (int y = minYLayer; y <= yLayer + 20; y++){
            for (int x = xLayer -3; x <= xLayer +3; x++){
                for (int z = zLayer -3; z <= zLayer +3; z++){
                    Block move = source.getBlock(x, y, z);
                    if (logList.contains(move.getType())) blockBreak.add(move);
                }
            }
        }
        for (Block block : blockBreak){
            block.breakNaturally();
        }
    }

    public void breakLeaves(Block current, Chunk source, World world){
        List<Block> leaveBreak = new ArrayList<>();
        int yLayer = current.getY();
        int xLayer = current.getX();
        int zLayer = current.getZ();

        for (int y = yLayer; y <= yLayer + 40; y++){
            for (int x = xLayer -5; x <= xLayer +5; x++){
                for (int z = zLayer -5; z <= zLayer +5; z++){
                    Block move = source.getBlock(x, y, z);
                    if (leavesList.contains(move.getType())) leaveBreak.add(move);
                }
            }
        }
        for (Block block : leaveBreak){
            int[] appleChances = getAppleChances();
            block.breakNaturally();
            if (appleChances[0] >= 29){
                world.dropItemNaturally(block.getLocation(), new ItemStack(Material.APPLE));
            }
            if (appleChances[1] == 200){
                world.dropItemNaturally(block.getLocation(), new ItemStack(Material.GOLDEN_APPLE));
            }
        }
    }

    public List<Material> getValidLog(){
        List<Material> logs = new ArrayList<>();
        logs.add(Material.LOG);
        logs.add(Material.LOG_2);
        return logs;
    }

    public List<Material> getValidLeave(){
        List<Material> leaves = new ArrayList<>();
        leaves.add(Material.LEAVES);
        leaves.add(Material.LEAVES_2);
        return leaves;
    }

    public int[] getAppleChances(){
        Random chance = new Random();
        int appleChance = chance.nextInt(30) +1;
        int gAppleChance = chance.nextInt(200) +1;
        return new int[]{appleChance, gAppleChance};
    }
}
