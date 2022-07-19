package com.poliotmia.common.third

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.poliotmia.common.databinding.FragmentThirdBinding
import com.poliotmia.common.util.DebugLog

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
