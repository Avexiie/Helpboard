package com.github.avexiie.board.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import com.github.avexiie.Main;
import com.github.avexiie.board.App;
import com.github.avexiie.board.HelpboardHolder;

/**
 * Created by Avexiie on 15-08-2020.
 */
public class EDeintergrate implements Listener {

    private App app;

    public EDeintergrate(App app)
    {
        this.app = app;
    }

    @EventHandler
    public void Deintergrate(PlayerQuitEvent e)
    {
        if(app == null) return;
        app.unregisterHolder(e.getPlayer());
        e.getPlayer().setScoreboard(Main.empty);
    }

}
