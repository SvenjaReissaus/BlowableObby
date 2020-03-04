package me.svreissaus.blobby.listeners

import me.svreissaus.blobby.Startup
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class PlayerListener : Listener {
    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if (event.isCancelled) return
        if (event.clickedBlock == null || event.action != Action.RIGHT_CLICK_BLOCK) return
        if (event.item?.type != Startup.configuration?.store?.check?.material) return
        if (!Startup.configuration?.store?.blocks?.containsKey(event.clickedBlock.type)!!) return
        val id: String = "${event.clickedBlock.location.world.name}-${event.clickedBlock.location.x}-${event.clickedBlock.location.y}-${event.clickedBlock.location.z}"
        val health = Startup.configuration?.store?.blocks!![event.clickedBlock.type]?.health
        if (Startup.data.containsKey(id) && Startup.data[id] != null)
            event.player.sendMessage(Startup.lang?.store?.check?.replace("%health%", "${Startup.data[id]}")?.replace("%block_health%", "$health"))
        else event.player.sendMessage(Startup.lang?.store?.check?.replace("%health%", "$health")?.replace("%block_health%", "$health"))
    }
}