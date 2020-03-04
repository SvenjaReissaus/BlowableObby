package me.svreissaus.blobby.commands

import me.svreissaus.blobby.Startup
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import java.util.*


class SaveCommand : Command("save", "Saves current config to file.", "Use as /blow save [all|lang|config]", listOf("down")) {
    override fun execute(sender: CommandSender, label: String, args: Array<String>): Boolean {
        if (args.isEmpty() || args[0] == "all") {
            Startup.lang?.save()
            Startup.configuration?.save()
            sender.sendMessage("Saved all data to disk.")
            return true
        }
        if (args[0] == "config") {
            Startup.configuration?.save()
            sender.sendMessage("Saved current configuration to disk.")
            return true
        }
        if (args[0] == "lang") {
            Startup.lang?.save()
            sender.sendMessage("Saved current data to disk.")
            return true
        }
        return false
    }
}