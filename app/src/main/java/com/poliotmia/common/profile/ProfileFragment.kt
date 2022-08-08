package com.poliotmia.common.profile

import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.poliotmia.common.Photo
import com.poliotmia.common.databinding.FragmentProfileBinding
import com.poliotmia.common.util.DebugLog

class ProfileFragment : Fragment() {

    private val logTag = ProfileFragment::class.simpleName
    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var myRef: DatabaseReference
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        DebugLog.i(logTag, "onCreateView-()")
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        myRef = Firebase.database.getReference("users")
        firestore = FirebaseFirestore.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DebugLog.i(logTag, "onViewCreated-()")
        auth.currentUser?.uid.let { uid ->
            DebugLog.d(logTag, "uid: $uid")
            // Read from the database
            myRef.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.child("$uid")
                    for(ds in user.children){
                        DebugLog.d(logTag, "======> ds.toString()")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    DebugLog.d(logTag, "읽어오기 실패")
                }
            })

            // [END read_message]

//            firestore.collection("images").get().addOnCompleteListener { task ->
//                if(task.isSuccessful) {
//
//                }
//            }
        }
    }
}
