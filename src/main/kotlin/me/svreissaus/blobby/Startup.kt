package me.svreissaus.blobby

import me.svreissaus.blobby.commands.BlowCommand
import me.svreissaus.blobby.listeners.BlockListener
import me.svreissaus.blobby.listeners.PlayerListener
import me.svreissaus.blobby.stores.ConfigStore
import me.svreissaus.blobby.stores.LangStore
import org.bukkit.Location
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin

class Startup : JavaPlugin() {

    companion object {
        var configuration: ConfigStore? = null
        var lang: LangStore? = null
        var version: String? = null
        val data: HashMap<String, Double> = HashMap<String, Double>()
        private var plugin: Startup? = null

        fun log(content: String) {
            plugin?.server?.broadcastMessage(content)
        }

        fun debug(content: String) {
            plugin?.logger?.info(content)
        }
    }

    override fun onEnable() {
        plugin = this
        debug("Checking for data folder")
        if (!this.dataFolder.exists()) this.dataFolder.mkdir()
        debug("Loading configuration file")
        configuration = ConfigStore(this.dataFolder)
        debug("Loading language file")
        lang = LangStore(this.dataFolder)
        debug("Loaded plugin version ${this.description.version}")
        version = this.description.version

        getCommand("blow").executor = BlowCommand()

        server.pluginManager.registerEvents(BlockListener() as Listener, this as Plugin)
        server.pluginManager.registerEvents(PlayerListener() as Listener, this as Plugin)
    }

    override fun onDisable() {
        debug("Saving data to files")
        configuration?.save()
        lang?.save()
    }
}