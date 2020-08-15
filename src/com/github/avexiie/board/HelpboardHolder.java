package com.github.avexiie.board;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import com.github.avexiie.Main;
import com.github.avexiie.Session;
import com.github.avexiie.board.App;
import com.github.avexiie.board.Row;
import com.github.avexiie.board.slimboard.Slimboard;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Avexiie on 15-08-2020.
 */
public class HelpboardHolder {

    private App app;
    public Player player;
    private boolean disabled = false;

    private Slimboard slim;

    /**
     * Construct a new holder
     * @param app
     * @param player
     */
    public HelpboardHolder(App app, Player player)
    {
        this.app = app;
        this.player = player;

        slim = new Slimboard(Session.plugin, player, app.getRows().size());

        app.registerHolder(this);
    }

    /**
     * Update the holder and all the rows
     */
    public void update()
    {

        if(Session.disabled_players.contains(this.player))
        {
            if(!disabled)
                this.player.setScoreboard(Main.empty);
            disabled = true;
            return;
        } else if(Session.re_enable_players.contains(this.player)) {
            disabled = false;
            this.player.setScoreboard(this.slim.board);
            Session.re_enable_players.remove(this.player);
        }

        slim.setTitle(app.getTitle().getLine());

        int count = 0;
        HashMap<Integer, String> lines = new HashMap<>();
        for(Row row : app.getRows())
        {
            String line = row.getLine();
            if(row.placeholders) {
                // Check if the PAPI plugin is enabled and the string has a placeholder
                if(Session.enabled_dependencies.contains(Session.dependencies[0]) && org.bukkit.Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI") &&
                        PlaceholderAPI.containsPlaceholders(line)) {
                    line = PlaceholderAPI.setPlaceholders(player, line);
                }
            }
            slim.setLine(count, line);
            count++;
        }
    }

}