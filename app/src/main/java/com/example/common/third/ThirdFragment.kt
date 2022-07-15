package com.example.common.third

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.common.databinding.FragmentSecondBinding
import com.example.common.databinding.FragmentThirdBinding
import com.example.common.util.DebugLog

class ThirdFragment : Fragment() {

    private val logTag = ThirdFragment::class.simpleName
    private lateinit var binding: FragmentThirdBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        DebugLog.i(logTag, "onCreateView-()")
        binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }
}
