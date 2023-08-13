package plugins.vipertrollnew;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import plugins.vipertrollnew.Commands.vtroll;
import plugins.vipertrollnew.Database.Database;
import plugins.vipertrollnew.Listeners.OnHit;
import plugins.vipertrollnew.Listeners.OnPlayerJoin;
import plugins.vipertrollnew.TabCompleters.vtrollCompleter;
import plugins.vipertrollnew.Utils.TrollData;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public final class ViperTrollNew extends JavaPlugin {

    public static ViperTrollNew instance;

    public Map<String, TrollData> map = new HashMap<>();

    Database database;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        database = new Database();
        database.initialize();
        database.updateMap();

        getServer().getPluginManager().registerEvents(new OnHit(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);

        getCommand("vtroll").setExecutor(new vtroll());
        getCommand("vtroll").setTabCompleter(new vtrollCompleter());



    }

    public String format(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }



}
