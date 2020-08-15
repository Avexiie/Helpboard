package com.github.avexiie;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.github.avexiie.board.App;
import com.github.avexiie.board.Row;
import com.github.avexiie.board.HelpboardHolder;
import com.github.avexiie.util.ConfigControl;
import com.github.avexiie.util.Func;

/**
 * Created by Avexiie on 15/08/2020.
 */
public class ManagerCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(!(commandSender instanceof Player))
        {
            commandSender.sendMessage("Only players can command!");
        } else {

            Player player = (Player) commandSender;

            if(args.length < 1)
            {
                Func.msg(player, "Too few arguments!");
                help(player);
            } else {
                if(args[0].equalsIgnoreCase("reload")) {
                    if(Func.perm(player, "reload"))
                    {
                        Main.disolveBoards();
                        ConfigControl.get().reloadConfigs();
                        Main.loadBoards();
                        Func.smsg(player, "Helpboard reloaded");
                    }
                }  else {
                    Func.msg(player,"Unknown command!");
                    help(player);
                }
            }
        }

        return false;
    }

    private void help(Player player)
    {
        Func.smsg(player, "/hb reload (Reload config and application)");
    }
}
