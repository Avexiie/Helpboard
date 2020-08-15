package com.github.avexiie.board;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import com.github.avexiie.Session;
import com.github.avexiie.util.ConfigControl;

import java.util.ArrayList;
import java.util.List;

public class WorldManager extends BukkitRunnable {

    private ArrayList<String> disabled_worlds = new ArrayList<>();

    public WorldManager()
    {
        if(ConfigControl.get().gc("settings").getStringList("disabled-worlds") != null)
            for(String world : ConfigControl.get().gc("settings").getStringList("disabled-worlds"))
            {
                disabled_worlds.add(world.toLowerCase().trim());
            }
    }

    @Override
    public void run() {
        for(Player p : Bukkit.getOnlinePlayers())
        {
            if(disabled_worlds.contains(p.getWorld().getName().toLowerCase().trim()))
            {
                if(!Session.disabled_players.contains(p))
                    Session.disabled_players.add(p);
            } else {
                Session.disabled_players.remove(p);
                if(!Session.re_enable_players.contains(p))
                    Session.re_enable_players.add(p);
            }
        }
    }
}