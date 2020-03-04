package me.svreissaus.blobby.config

import com.google.gson.annotations.Expose

class Lang {
    @Expose
    var commands: LangCommands = LangCommands()

    class LangCommands {
        @Expose
        var permission: String = "&7You don't have permission to execute this command."
        @Expose
        var version: String = "&7Current plugin version is:"

        @Expose
        var block: LangCommandBlocks = LangCommandBlocks()

        class LangCommandBlocks {
            @Expose
            var listEmpty: String = "&7There are no block currently configured."

            @Expose
            var listEach: String = "&e%material%: &c%health%&aHP"
        }
    }
}