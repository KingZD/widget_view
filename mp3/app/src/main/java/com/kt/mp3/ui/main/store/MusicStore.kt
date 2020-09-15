package com.kt.mp3.ui.main.store

import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

class MusicStore : ViewModelStoreOwner {

    companion object {
        var store = ViewModelStore()
    }

    override fun getViewModelStore(): ViewModelStore {
        return store
    }
}