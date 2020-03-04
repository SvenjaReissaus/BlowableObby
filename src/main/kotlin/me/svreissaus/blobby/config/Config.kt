package me.svreissaus.blobby.config

import com.google.gson.annotations.Expose
import org.bukkit.Material
import org.bukkit.entity.EntityType

class Config {
    // Could be of use at some point.
    @Expose
    var lang: String = "en"

    // Configuration for the explosions
    @Expose
    var explosions: ConfigExplosion = ConfigExplosion()

    // List of configuration for protecting blocks
    @Expose
    var blocks: HashMap<Material, ConfigBlock> = HashMap()

    @Expose
    var check: ConfigCheck = ConfigCheck()

    class ConfigBlock {
        // Health the block should have
        @Expose
        var health: Double = 5.0

        // Blacklist of explosions that do not harm this block
        @Expose
        var except: ArrayList<EntityType>? = ArrayList<EntityType>()
    }

    class ConfigCheck {
        @Expose
        var material: Material = Material.POTATO_ITEM
    }

    class ConfigExplosion {
        // The default damage radius of explosions
        @Expose
        var radius: Double = 3.0

        // The default damage multiplier (block health wise)
        @Expose
        var damage: Double = 1.0

        // Custom configurations for explosion-specific modifications
        @Expose
        var custom: HashMap<EntityType, ConfigCustomExplosion> = HashMap<EntityType, ConfigCustomExplosion>()

        class ConfigCustomExplosion {

            // Radius boost the explosion has
            @Expose
            var radius: Double = 1.0

            // Damage boost the explosion has
            @Expose
            var damage: Double = 1.0

            // Should we enable it?
            @Expose
            var enabled: Boolean = true

            // Do no harm on this worlds
            @Expose
            var disabledWorlds: ArrayList<String>? = ArrayList()
        }
    }
    init {
        explosions.custom[EntityType.PRIMED_TNT] = ConfigExplosion.ConfigCustomExplosion()
        explosions.custom[EntityType.CREEPER] = ConfigExplosion.ConfigCustomExplosion()
        explosions.custom[EntityType.WITHER_SKULL] = ConfigExplosion.ConfigCustomExplosion()

        blocks[Material.OBSIDIAN] = ConfigBlock()
        blocks[Material.OBSIDIAN]!!.except?.add(EntityType.CREEPER)
        blocks[Material.GLASS] = ConfigBlock()
        blocks[Material.IRON_BLOCK] = ConfigBlock()
    }
}