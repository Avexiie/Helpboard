package com.github.avexiie;

import org.apache.commons.lang3.StringEscapeUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import com.github.avexiie.board.App;
import com.github.avexiie.board.WorldManager;
import com.github.avexiie.util.ConfigControl;

import java.util.HashMap;

/**
 * Created by Avexiie on 15-08-2020.
 */
public class Main extends JavaPlugin {

    public static Scoreboard empty;

    public static HashMap<String, App> apps = new HashMap<>();

    @Override
    public void onEnable() {
        init();
    }

    /**
     * Initiate the plugin
     */
    private void init()
    {
        Session.plugin = this;
        Session.isUpToDate("14754");
        ConfigControl.get().createDataFiles();
        empty = getServer().getScoreboardManager().getNewScoreboard();

        autoloadDependencies();
        setupCommands();
        loadBoards();

        new Metrics(this);

        new WorldManager().runTaskTimer(this, 20L, 40L);

        finished();
    }

    /**
     * Load in dependencies
     */
    private void autoloadDependencies()
    {
        for(String dependency : Session.dependencies)
            if(Bukkit.getPluginManager().isPluginEnabled(dependency))
                Session.enabled_dependencies.add(dependency);
    }

    /**
     * Create the commands
     */
    private void setupCommands()
    {
        getCommand("sb").setExecutor(new ManagerCMD());
    }

    /**
     * Load in all board drivers
     */
    public static void loadBoards()
    {
        newApp("board", true); // Default board

        for(String s : ConfigControl.get().gc("settings").getStringList("enabled-boards"))
        {
            Session.plugin.getLogger().info("Attempting to start app-creator for: " + s);
            if(ConfigControl.get().gc("settings").isConfigurationSection(s)) newApp(s, false);
            else Session.plugin.getLogger().severe("Tried enabling board '" + s + "', but it does not exist!");
        }
    }

    /**
     * Unload all board drivers
     */
    public static void disolveBoards()
    {
        for(App app : apps.values())
            app.cancel();
        apps.clear();
    }

    /**
     * Construct a new app
     * @param board
     * @param isdefault
     */
    public static void newApp(String board, boolean isdefault)
    {
        App app = new App(board);
        if(ConfigControl.get().gc("settings").getBoolean("settings.safe-mode"))
            app.runTaskTimer(Session.plugin, 1L, 1L);
        else app.runTaskTimerAsynchronously(Session.plugin, 1L, 1L);
        apps.put(board, app);
        Session.plugin.getLogger().info("Loaded app handler for board: " + board);
        app.isdefault = isdefault;
    }

    /**
     * Log to the console that we're done
     */
    public static void finished()
    {
        System.out.println("Helpboard is online! Helpboard version: " + Session.plugin.getDescription().getVersion() +
                " (" + (Session.isuptodate ? "UP TO DATE" : "OUTDATED") + ")");

        System.out.println();
    }

}