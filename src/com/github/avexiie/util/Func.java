package com.github.avexiie.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by Avexiie on 15-08-2020.
 */
public class Func {

    public static void msg(Player p, String message)
    {
        p.sendMessage(color("&cHelpboard: &7" + message));
    }

    public static void smsg(Player p, String message)
    {
        p.sendMessage(color("&c[HB] &7" + message));
    }

    public static String color(String s)
    {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static boolean perm(Player p, String perm)
    {
        if(p.hasPermission("helpboard." + perm))
        {
            return true;
        } else{
            smsg(p, "You do not have permission &chelpboard." + perm);
            return false;
        }
    }

}