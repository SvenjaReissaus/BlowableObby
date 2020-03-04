package me.svreissaus.blobby.commands

import me.svreissaus.blobby.Startup
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class VersionCommand : Command("version", "Displays the version of the plugin.", "/blow version", emptyList<String>()) {
    override fun execute(sender: CommandSender?, commandLabel: String?, args: Array<out String>?): Boolean {
        if (sender == null) return false
        if (!sender.hasPermission("blow.version")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Startup.lang?.store?.commands?.permission))
            return false
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "${Startup.lang?.store?.commands?.version} ${Startup.version}"))
        return true
    }
}