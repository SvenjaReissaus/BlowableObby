package me.svreissaus.blobby.commands

import me.svreissaus.blobby.Startup
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class BlocksCommand : Command("blocks", "Configures blocks.", "Use as /blow blocks <list|add|remove>", listOf("block", "bl")) {
    override fun execute(sender: CommandSender, label: String?, args: Array<String>): Boolean {
        if (args.isEmpty() || args[0] == "list") {
            val blocks = Startup.configuration?.store?.blocks;
            if (blocks == null || blocks.isEmpty()) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Startup.lang?.store?.commands?.block?.listEmpty))
                return true
            }

            for (block in blocks)
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Startup.lang?.store?.commands?.block?.listEach?.replace("%material%", block.key.name)?.replace("%health%", "${block.value.health.toInt()}")))
            return true
        }

        return false
    }
}