package com.github.avexiie.board;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import com.github.avexiie.Main;
import com.github.avexiie.Session;
import com.github.avexiie.board.events.EDeintergrate;
import com.github.avexiie.board.events.EIntergrate;
import com.github.avexiie.util.ConfigControl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Avexiie on 15-08-2020.
 */
public class App extends BukkitRunnable {

    private Row title;
    private ArrayList<Row> rows = new ArrayList<>();
    private ArrayList<Player> children = new ArrayList<>();
    public ArrayList<HelpboardHolder> holders = new ArrayList<>();
    public static boolean longline = false;
    public String board;
    public boolean isdefault = true;

    /**
     * Construct a new board driver
     * @param board
     */
    public App(String board)
    {
        // conf
        App.longline = ConfigControl.get().gc("settings").getBoolean("settings.longline"); // Are we in longline?
        this.board = board; // What is the current board?

        //Events
        Session.plugin.getServer().getPluginManager().registerEvents(new EIntergrate(this), Session.plugin); // Join event
        Session.plugin.getServer().getPluginManager().registerEvents(new EDeintergrate(this), Session.plugin); // Quit event

        // Setup title row
        List<String> lines = ConfigControl.get().gc("settings").getConfigurationSection(board + ".title").getStringList("liner"); // Get the lines
        int interval = ConfigControl.get().gc("settings").getInt(board + ".title.interval"); // Get the intervals
        title = new Row((ArrayList<String>) lines, interval); // Create the title row!

        for(int i = 1; i<200; i++) // Loop over all lines
        {
            ConfigurationSection section = ConfigControl.get().gc("settings").getConfigurationSection(board + ".rows." + i); // Get their rows
            if(null != section) // Is the section null?
            {
                Row row = new Row((ArrayList<String>)section.getStringList("liner"), section.getInt("interval")); // Create a new row
                rows.add(row); // Add this line to the row list
            }
        }

        // Register already joined players
        if(board == "board") for(Player player : Session.plugin.getServer().getOnlinePlayers()) new HelpboardHolder(this, player);

    }

    /**
     * Get all the rows
     * @return
     */
    public ArrayList<Row> getRows()
    {
        return rows;
    }

    /**
     * Ge the title
     * @return
     */
    public Row getTitle()
    {
        return title;
    }

    /**
     * Register a helpboardholder
     * @param holder
     */
    public void registerHolder(HelpboardHolder holder)
    {
        holders.add(holder);
    }

    /**
     * Unregister a holder
     * @param holder
     */
    public void unregisterHolder(HelpboardHolder holder)
    {
        holders.remove(holder);
    }

    /**
     * Unregister a holder via player
     * @param player
     */
    public void unregisterHolder(Player player)
    {
        for(HelpboardHolder holder : holders)
            if(holder.player == player)
            {
                holders.remove(holder);
                break;
            }
    }

    @Override
    public void run() {
        // Update rows
        title.update();
        for(Row row : rows)
            row.update();


        // Update helpboards
        for(HelpboardHolder holder : holders)
            holder.update();
    }
}
