package com.poliotmia.common.first

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.poliotmia.common.databinding.FragmentFirstBinding
import com.poliotmia.common.util.DebugLog

class FirstFragment : Fragment() {

    private val logTag = FirstFragment::class.simpleName
    private lateinit var binding: FragmentFirstBinding

    private var currentCountDownTimer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DebugLog.i(logTag, "onCreateView-()")
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.seekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
                    // binding.remainMinutesTextView.text = "$progress"
                    updateRemainTime(progress * 60 * 1000L)
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                    // 첫 seekBar 터치 이후, 재터치시엔 타이머 시간 종료 필요
//                    if (currentCountDownTimer != null){
//
//                    }
                    currentCountDownTimer?.cancel()
                    currentCountDownTimer = null
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    // seekBar 터치가 끝난 직후부터 timer 시작
                    seekBar ?: return
                    currentCountDownTimer = createCountDownTimer(seekBar.progress * 60 * 1000L).start()
                }
            }
        )
    }

    private fun createCountDownTimer(initialMillis: Long): CountDownTimer =
        object : CountDownTimer(initialMillis, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                // 초마다 UI 갱신
                updateRemainTime(millisUntilFinished)
                // seekBar 한 칸씩 줄어들도록 갱신
                updateSeekBar(millisUntilFinished)
            }

            override fun onFinish() {
                updateRemainTime(0) // 00 00 으로 초기화
                updateSeekBar(0) // 제일 왼쪽으로
            }
        }

    @SuppressLint("SetTextI18n")
    private fun updateRemainTime(remainMillis: Long) {
        val remainSeconds = remainMillis / 1000

        binding.remainMinutesTextView.text = "%02d".format(remainSeconds / 60)
        binding.remainSecondsTextView.text = "%02d".format(remainSeconds % 60)
    }

    private fun updateSeekBar(remainMillis: Long) {
        binding.seekBar.progress = (remainMillis / 1000 / 60).toInt()
    }
}