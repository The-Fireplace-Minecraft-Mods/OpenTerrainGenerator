package com.pg85.otg.bukkit.commands;

import com.pg85.otg.LocalWorld;
import com.pg85.otg.bukkit.OTGPerm;
import com.pg85.otg.bukkit.OTGPlugin;
import com.pg85.otg.bukkit.util.WorldHelper;
import com.pg85.otg.configuration.standard.PluginStandardValues;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CheckCommand extends BaseCommand
{
    public CheckCommand(OTGPlugin _plugin)
    {
        super(_plugin);
        name = "check";
        perm = OTGPerm.CMD_CHECK.node;
        usage = "check World_Name";
        workOnConsole = true;
    }

    @Override
    public boolean onCommand(CommandSender sender, List<String> args)
    {
        // Get the Minecraft world
        Location location = getLocation(sender);
        World world;
        if (location == null)
        {
            if (args.isEmpty())
            {
                sender.sendMessage(ERROR_COLOR + "You need to specify the world name.");
                return true;
            }
            world = Bukkit.getWorld(args.get(0));
            if (world == null)
            {
                sender.sendMessage(ERROR_COLOR + "The world \"" + args.get(0) + "\" doesn't exist.");
                return true;
            }
        } else
        {
            world = location.getWorld();
        }

        // Check if the plugin is enabled for this world
        LocalWorld localWorld = WorldHelper.toLocalWorld(world);
        if (localWorld == null)
        {
            sender.sendMessage(ERROR_COLOR + PluginStandardValues.PLUGIN_NAME
                    + " is not enabled for the world \"" + world.getName() + "\".");
        } else
        {
            sender.sendMessage(MESSAGE_COLOR + PluginStandardValues.PLUGIN_NAME
                    + " is enabled for the world " + VALUE_COLOR + "\"" + world.getName() + "\""
                    + MESSAGE_COLOR + ".");
        }
        return true;
    }
}