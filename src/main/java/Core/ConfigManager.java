package Core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigManager {

    private HashMap<String, YamlConfiguration> configs = new HashMap();
    private HashMap<String, File> files = new HashMap();

    private File loadFile(String fileName){
        Util.print(Util.getPrefix() + "Loading file: " + fileName);
        File file = new File(Bukkit.getServer().getPluginManager().getPlugin("ManHuntCompass").getDataFolder(), fileName);
        if(!file.exists()){
            try{
                file.createNewFile();
            } catch(IOException e){
                Util.print(Util.getPrefix() + "Error loading file: " + fileName);
                e.printStackTrace();
            }
        }
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        try {
            yaml.save(file);
        } catch(IOException e){
            Util.print(Util.getPrefix() + "Error saving file: " + fileName);
            e.printStackTrace();
        }
        Util.print(Util.getPrefix() + "File loaded: fileName");
        configs.put(fileName, yaml);
        files.put(fileName, file);
        return file;
    }
    public YamlConfiguration getConfig(String str){
        return configs.get(str);
    }

    public void saveFiles(){
        for(String fileName : configs.keySet()) {
            try {
                configs.get(fileName).save(files.get(fileName));
            } catch (IOException e) {
                Util.print(Util.getPrefix() + "Error saving file: " + fileName);
                e.printStackTrace();
            }
        }
    }

}
