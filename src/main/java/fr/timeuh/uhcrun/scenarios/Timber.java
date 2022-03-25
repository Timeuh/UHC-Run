package fr.timeuh.uhcrun.scenarios;

import fr.timeuh.uhcrun.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.List;

public class Timber implements Listener {
    private UHCRun uhcRun;
    private List<Material> logList;

    public Timber(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
        this.logList = getValidLog();
    }

    @EventHandler
    public void onBreakTree(BlockBreakEvent event){
        if (uhcRun.checkEnabledScenario(Scenarios.TIMBER)){
            Block current = event.getBlock();
            Chunk source = Bukkit.getWorld("world").getChunkAt(current);
            if (logList.contains(current.getType())) breakTree(current, source);
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

    public List<Material> getValidLog(){
        List<Material> logs = new ArrayList<>();
        logs.add(Material.LOG);
        logs.add(Material.LOG_2);
        return logs;
    }
}
