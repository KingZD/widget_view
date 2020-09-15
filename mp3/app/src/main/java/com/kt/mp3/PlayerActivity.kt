package com.kt.mp3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelStore
import com.gyf.immersionbar.ImmersionBar
import com.kt.mp3.ui.main.MainViewModel
import com.kt.mp3.ui.main.store.MusicStore
import kotlinx.android.synthetic.main.activity_player.*


class PlayerActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        ImmersionBar.with(this).statusBarColor(R.color.transparent).titleBarMarginTop(ic_back)
            .init();

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        iv_cb_player.setOnCheckedChangeListener { _, isChecked ->
            println("点击播放${isChecked}")
            viewModel.startPlayer(
                isChecked
            )
        }

        ic_back.setOnClickListener {
            finish()
        }
    }

    override fun getViewModelStore(): ViewModelStore {
        return MusicStore.store
    }
}