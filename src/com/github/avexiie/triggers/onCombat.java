package com.github.avexiie.triggers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import com.github.avexiie.Main;
import com.github.avexiie.board.App;
import com.github.avexiie.board.HelpboardHolder;

/**
 * Created by Avexiie on 15-08-2020.
 */
public class onCombat implements Listener {

    @EventHandler
    public void onCombat(EntityDamageByEntityEvent e)
    {
        if(e.getDamager() instanceof Player)
        {
            Player damager = (Player) e.getDamager();
            damager.setScoreboard(Main.empty);
            for(App app : Main.apps.values())
                app.unregisterHolder(damager);

            Main.apps.get("combat").registerHolder(new HelpboardHolder(Main.apps.get("combat"), damager));
        }
    }

}
