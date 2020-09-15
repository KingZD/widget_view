package com.kt.mp3.ui.main

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelStore
import com.kt.mp3.PlayerActivity
import com.kt.mp3.R
import com.kt.mp3.ui.main.gui.FloatPlayerView
import com.kt.mp3.ui.main.store.MusicStore
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        message.setOnClickListener { startActivity(Intent(activity, PlayerActivity::class.java)) }

    }


    override fun onResume() {
        super.onResume()
        println("播放状态:${viewModel.isPlayer()}")
        if (viewModel.isPlayer())
            FloatPlayerView.Builder.build(activity)
    }

    override fun getViewModelStore(): ViewModelStore {
        return MusicStore.store
    }
}