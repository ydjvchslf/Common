package com.poliotmia.common.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.poliotmia.common.databinding.FragmentProfileBinding
import com.poliotmia.common.util.DebugLog

class ProfileFragment : Fragment() {

    private val logTag = ProfileFragment::class.simpleName
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        DebugLog.i(logTag, "onCreateView-()")
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
}
