package com.example.common.first

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.common.databinding.FragmentFirstBinding
import com.example.common.util.DebugLog

class FirstFragment : Fragment() {

    private val logTag = FirstFragment::class.simpleName
    private lateinit var binding: FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DebugLog.i(logTag, "onCreateView-()")
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

}