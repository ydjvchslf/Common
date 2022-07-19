package com.poliotmia.common.second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.poliotmia.common.databinding.FragmentSecondBinding
import com.poliotmia.common.util.DebugLog

class SecondFragment : Fragment() {

    private val logTag = SecondFragment::class.simpleName
    private lateinit var binding: FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        DebugLog.i(logTag, "onCreateView-()")
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }
}
