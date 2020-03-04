package me.svreissaus.blobby.commands

import me.svreissaus.blobby.Startup
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import java.util.*

class LoadCommand : Command("load", "Loads config and/or lang from files.", "Use as /blow load <all|config|lang>", listOf("up")) {
    override fun execute(sender: CommandSender, label: String?, args: Array<String>): Boolean {
        if (args.isEmpty() || args[0] == "all") {
            Startup.configuration?.load()
            Startup.lang?.load()
            sender.sendMessage("Loaded newer data from disk.")
            return true
        }
        if (args[0] == "config") {
            Startup.configuration?.load()
            sender.sendMessage("Loaded newer configuration from disk.")
            return true
        }
        if (args[0] == "lang") {
            Startup.lang?.load()
            sender.sendMessage("Loaded newer lang from disk.")
            return true
        }
        return false
    }
}
