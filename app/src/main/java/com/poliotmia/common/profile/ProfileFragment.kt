package com.poliotmia.common.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.poliotmia.common.databinding.FragmentProfileBinding
import com.poliotmia.common.util.DebugLog

class ProfileFragment : Fragment() {

    private val logTag = ProfileFragment::class.simpleName
    private lateinit var binding: FragmentProfileBinding
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        DebugLog.i(logTag, "onCreateView-()")
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        firestore = FirebaseFirestore.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DebugLog.i(logTag, "onViewCreated-()")
        firestore.collection("images").get().addOnCompleteListener { task ->
            if(task.isSuccessful) {
                var photo = task.result?.toObject(Photo::class.java)
                Glide.with(this).load(photo?.imageUrl).into(imageIv)
                descriptionTv.text=photo?.description
            }
        }

    }
}
