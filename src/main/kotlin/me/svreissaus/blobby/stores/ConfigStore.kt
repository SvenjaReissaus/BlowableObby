package me.svreissaus.blobby.stores

import me.svreissaus.blobby.config.Config
import me.svreissaus.blobby.storage.Store
import java.io.File

class ConfigStore(folder: File) {
    private val _store: Store<Config>

    var store: Config = Config()

    fun save(): Config? {
        return _store.saveStore(store)
    }

    fun load(): Config? {
        store = _store.loadStore(Config::class.java)
        return store
    }

    init {
        _store = Store(store, folder)
        store = _store.createStore(Config::class.java)
    }
}
