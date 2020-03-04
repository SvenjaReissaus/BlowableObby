package me.svreissaus.blobby.listeners

import me.svreissaus.blobby.Startup
import me.svreissaus.blobby.config.Config
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class PlayerListener : Listener {
    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if (event.isCancelled) return
        if (event.clickedBlock == null || event.clickedBlock.type == Material.AIR) return
        if (!Startup.configuration?.store?.blocks?.containsKey(event.clickedBlock.type)!!) return
        val id: String = "${event.clickedBlock.location.world.name}-${event.clickedBlock.location.x}-${event.clickedBlock.location.y}-${event.clickedBlock.location.z}"
        event.player.sendMessage("Looking for block with id $id")
        if (Startup.data.containsKey(id) && Startup.data[id] != null)
            event.player.sendMessage("This block has ${Startup.data[id]}/${Startup.configuration?.store?.blocks!![event.clickedBlock.type]?.health}")
        else event.player.sendMessage("This block hasn't been registered yet.")
    }
}