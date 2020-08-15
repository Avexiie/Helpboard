package com.github.avexiie.board.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import com.github.avexiie.Session;
import com.github.avexiie.board.App;
import com.github.avexiie.board.HelpboardHolder;
import com.github.avexiie.util.Func;

/**
 * Created by Avexiie on 15-08-2020.
 */
public class EIntergrate implements Listener {

    private App app;

    public EIntergrate(App app)
    {
        this.app = app;
    }

    @EventHandler
    public void Intergrate(PlayerJoinEvent e)
    {

        if(app == null || !app.isdefault) return;
        if(e.getPlayer().isOp() && !Session.isuptodate)
            e.getPlayer().sendMessage(Func.color("&cYou are running an outdated version of Helpboard, please update as soon as possible for performance gain, security- or bugfixes."));
        new HelpboardHolder(app, e.getPlayer());
    }

}