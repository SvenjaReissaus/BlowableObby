package me.svreissaus.blobby.stores

import me.svreissaus.blobby.config.Lang
import me.svreissaus.blobby.storage.Store
import java.io.File

class LangStore constructor(folder: File) {
    private val _store: Store<Lang>

    var store: Lang = Lang()

    fun save(): Lang? {
        return _store.saveStore(store)
    }

    fun load(): Lang? {
        store = _store.loadStore(Lang::class.java)
        return store
    }

    init {
        _store = Store(store, folder)
        store = _store.createStore(Lang::class.java)
    }
}