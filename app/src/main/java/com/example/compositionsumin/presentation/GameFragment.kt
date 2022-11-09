package com.example.compositionsumin.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.compositionsumin.R
import com.example.compositionsumin.databinding.FragmentGameBinding
import com.example.compositionsumin.domain.entity.GameResult
import com.example.compositionsumin.domain.entity.GameSettings
import com.example.compositionsumin.domain.entity.Level

class GameFragment : Fragment() {
    private lateinit var gameResult: GameResult
//    private lateinit var level: Level
//вариант 1 получения аргументов
private val args by navArgs<GameFragmentArgs>()

private val viewModelFactory by lazy {
    //вариант 1 получения аргументов
//    val args = GameFragmentArgs.fromBundle(requireArguments())
    GameViewFactory(requireActivity().application,args.level)
}

        private val viewModel: GameViewModel by lazy {
        ViewModelProvider(
            this, viewModelFactory
        )[GameViewModel::class.java]
    }

    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }
    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        parsArgs()
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameResult = GameResult(true, 1, 2, GameSettings(2, 4, 5, 6))
//        binding.tvLeftNumber.setOnClickListener{
//            launchGameFinishedFragment(gameResult)
//        }
//        Log.d("level_s","$level")
        observeModel()
        clickListenerOnOption()
//        viewModel.startGame(level)

    }

    private fun clickListenerOnOption() {
        for (tvOption in tvOptions) {
            tvOption.setOnClickListener {
                viewModel.chooseAnswer(tvOption.text.toString().toInt())
            }
        }
    }

    private fun observeModel() {
        viewModel.question.observe(viewLifecycleOwner) {
            binding.tvSum.text = it.sum.toString()
            binding.tvLeftNumber.text = it.visibleNumber.toString()
            for (i in 0 until tvOptions.size) {
                tvOptions[i].text = it.options[i].toString()
            }
//            binding.tvOption1.text = it.options[1].toString()
//            binding.tvOption2.text = it.options[2].toString()
//            binding.tvOption3.text = it.options[3].toString()
//            binding.tvOption4.text = it.options[4].toString()
//            binding.tvOption6.text = it.options[5].toString()
        }
        viewModel.enoughCountOfRightAnswers.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.setTextColor(getColorByState(it))
        }
        viewModel.enoughPercentOfRightAnswers.observe(viewLifecycleOwner) {
            val color =getColorByState(it)
            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
        }
        viewModel.formattedTime.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it
        }
        viewModel.progressAnswer.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.text = it
        }
        viewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
            binding.progressBar.setProgress(it, true)
        }
        viewModel.minPercent.observe(viewLifecycleOwner) {
            binding.progressBar.secondaryProgress = it
        }
        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameFinishedFragment(it)
        }

    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
       findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameFinishedFragment(gameResult))

//        val args = Bundle().apply {
//           putParcelable(GameFinishedFragment.KEY_RESULT_GAME,gameResult)
//       }
//        findNavController().navigate(R.id.gameFinishedFragment,args)

        //Log.d("launchGameFinishedFragment","gameResult $gameResult")
//        requireActivity().supportFragmentManager.beginTransaction()
//            .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
//            .addToBackStack(null)
//            .commit()
    }

//    private fun parsArgs() {
//        level = requireArguments().getParcelable<Level>(KEY_LEVEL) as Level
//        //второй вариант через let
//        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
//            level = it
//        }
//    }

    private fun getColorByState(goodState: Boolean): Int {
        val colorResId = if (goodState) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }
        return ContextCompat.getColor(requireContext(), colorResId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    companion object {
//        const val NAME_LEVEL = "GameFragment"
//        val KEY_LEVEL = "level"
//        fun newInstance(level: Level): GameFragment {
//        }
//        }


    // более длиинное и понятное описание
//            val args = Bundle()
//            args.putSerializable(KEY_LEVEL,level)
//            val fragment= GameFragment()
//            fragment.arguments =args
//            return fragment

//            return GameFragment().apply {
//                arguments = Bundle().apply {
//                    putParcelable(KEY_LEVEL, level)
//                }
//            }
//        }
//    }
}