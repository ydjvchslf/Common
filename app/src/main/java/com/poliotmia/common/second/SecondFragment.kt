package com.poliotmia.common.second

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.poliotmia.common.Photo
import com.poliotmia.common.Profile
import com.poliotmia.common.databinding.FragmentSecondBinding
import com.poliotmia.common.util.DebugLog
import java.text.SimpleDateFormat
import java.util.*

class SecondFragment : Fragment() {

    private val logTag = SecondFragment::class.simpleName
    private lateinit var binding: FragmentSecondBinding
    private var auth: FirebaseAuth = Firebase.auth
    private var storage: FirebaseStorage = Firebase.storage
    private lateinit var myRef: DatabaseReference
    private var imageUri : Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        DebugLog.i(logTag, "onCreateView-()")
        binding = FragmentSecondBinding.inflate(inflater, container, false)

        //auth = Firebase.auth
        val database = Firebase.database
        myRef = database.getReference("users")

        return binding.root

    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DebugLog.i(logTag, "onViewCreated-()")

        //var imageUri : Uri?
        var profileCheck = false

        //이미지 등록
        val getContent =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if(result.resultCode == RESULT_OK) {
                    imageUri = result.data?.data //이미지 경로 원본
                    binding.profileImage.setImageURI(imageUri) //이미지 뷰를 바꿈
                    DebugLog.d(logTag, "이미지 가져오기 성공")
                    //profile.profileUrl = imageUri.toString() TODO:: 왜안돼?
                }
                else{
                    DebugLog.d(logTag, "이미지 가져오기 실패")
                }
            }

        binding.profileImage.setOnClickListener {
                val intentImage = Intent(Intent.ACTION_PICK)
                intentImage.type = MediaStore.Images.Media.CONTENT_TYPE
                getContent.launch(intentImage)
                profileCheck = true
        }

        binding.addBtn.setOnClickListener {
            val inputNickname = binding.inputNickname.text.toString()
            val inputOneLine = binding.inputOneLine.text.toString()

            if(binding.inputNickname.text.isNotEmpty() || binding.inputOneLine.text.isNotEmpty() && profileCheck) {
                val uid = auth.currentUser?.uid
                val fileName =
                    SimpleDateFormat("yyyyMMddHHmmss").format(Date()) // 파일명이 겹치면 안되기 떄문에 시년월일분초 지정

                val photo = Photo (
                    fileName = fileName,
                    imageUrl = imageUri.toString()
                )

                val profile = Profile(
                    nickName = inputNickname,
                    oneLine = inputOneLine,
                    photo = photo
                )

                DebugLog.d(logTag, "photo => $photo")
                DebugLog.d(logTag, "profile => $profile")

                addProfile(uid, profile)
            } else {
                DebugLog.d(logTag, "세 항목 다 채워주세요 => profileCheck: $profileCheck")
            }
        }
    }

    private fun addProfile(uid: String?, profile: Profile) {
        myRef.child("$uid").setValue(profile)

        var storageRef = storage.reference.child("images").child(profile.photo?.fileName.toString())
        storageRef.putFile(imageUri!!).addOnSuccessListener {
            DebugLog.d(logTag, "업로드 성공")
        }
    }
}
