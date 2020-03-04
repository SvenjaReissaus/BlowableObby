package me.svreissaus.blobby.listeners

import me.svreissaus.blobby.Startup
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityExplodeEvent
import kotlin.math.ceil

class BlockListener : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBlockExplode(event: EntityExplodeEvent) {
        if (event.isCancelled) return
        if (event.entityType == null) return
        val type: EntityType = event.entityType
        val explosion = Startup.configuration?.store?.explosions?.custom?.get(type)
        if (Startup.configuration?.store?.explosions?.custom?.containsKey(type)!! && !explosion?.enabled!!) return
        Startup.log("Started explosion of type ${type.name}")
        var radius: Double = Startup.configuration?.store?.explosions?.radius!!
        if (explosion != null)
            radius *= explosion.radius
        var multiplier: Double = 1.0
        if (explosion != null)
            multiplier *= explosion.damage
        val blocks: List<Block> = explosionObserver(event.location, event.yield, ArrayList(event.blockList()), radius, multiplier, type)
        event.blockList().clear()
        for (block: Block in blocks) event.blockList().add(block)
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.isCancelled) return
        if (event.block.type == Material.AIR) return
        val block: Block = event.block
        val id: String = "${block.location.world.name}-${block.location.x}-${block.location.y}-${block.location.z}"
        if (Startup.configuration?.store?.blocks?.containsKey(block.type)!!)
            if (Startup.data.containsKey(id))
                Startup.data.remove(id)
    }

    private fun explosionObserver(source: Location, yield: Float, blocks: ArrayList<Block>, radiusSource: Double, multiplier: Double, explosion: EntityType): List<Block> {
        var dRadius: Double = radiusSource
        if (`yield` > 1) dRadius += `yield`/10
        val radius = ceil(dRadius).toInt()
        for (block: Block in blocks)
            if (Startup.configuration?.store?.blocks?.containsKey(block.type)!!) blocks.remove(block)
        for (x in -radius..radius) {
            for (y in -radius..radius) {
                for (z in -radius..radius) {
                    val loc = Location(source.world, x + source.x, y + source.y, z + source.z)
                    if (source.distance(loc) <= radius) {
                        val block = loc.block
                        val conf = Startup.configuration?.store?.blocks?.get(block.type)
                        if (conf != null && !conf.except.contains(explosion)) { // Get distance and damage
                            val distance = loc.distance(source)
                            var damage = 1.0
                            // Yield
                            if (`yield` > 0.5) damage += 1.0 else if (`yield` > 8) damage += 2.0 else if (`yield` > 16) damage += 3.0 else if (`yield` > 22) damage += 4.0 else if (`yield` > 28) damage += 5.0
                            // Damage the block
                            damage *= multiplier
                            if (damage > 0) {
                                if (distance > 1) damage /= (distance * 0.7)
                                val id: String = "${block.location.world.name}-${block.location.x}-${block.location.y}-${block.location.z}"
                                if (Startup.data.containsKey(id))
                                    Startup.data[id] = (Startup.data[id]!! - damage)
                                else Startup.data[id] = (conf?.health!! - damage)
                                if (Startup.data[id]!! <= 0) {
                                    blocks.add(block)
                                    Startup.data.remove(id)
                                }
                            }
                        }
                    }
                }
            }
        }
        return blocks
    }
}