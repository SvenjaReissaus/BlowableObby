package me.svreissaus.blobby.commands

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*


class BlowCommand : CommandExecutor {
    private val commands: List<Command> = listOf(BlocksCommand(), LoadCommand(), SaveCommand(), VersionCommand())

    override fun onCommand(commandSender: CommandSender, command: Command?, label: String?, args: Array<String?>): Boolean {
        if (commandSender !is Player) {
            commandSender.sendMessage("You must be a player to execute this command.")
            return false
        }
        if (args.isEmpty()) {
            for (_command in commands) commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9/${label} " + _command.name + "&f - " + _command.description))
            return true
        }
        for (_command in commands) {
            if (_command.name.equals(args[0], ignoreCase = true)) _command.execute(commandSender, args[0], args.copyOfRange(1, args.size))
        }
        return true
    }
}