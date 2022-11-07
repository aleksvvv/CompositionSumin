package com.example.compositionsumin.presentation

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import com.example.compositionsumin.R
import com.example.compositionsumin.databinding.FragmentGameBinding
import com.example.compositionsumin.databinding.FragmentGameFinishedBinding
import com.example.compositionsumin.domain.entity.GameResult

class GameFinishedFragment : Fragment() {

    private lateinit var resultGame:GameResult
    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseResultGame()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Создаем слушатель. переопределен метод кнопки назад OnBackPressedCallback
        //объект onBackPressedDispatcher с методом addCallback (слушатель ), который принимает
        //объект OnBackPressedCallback(на кнопку назад)  через анонимный класс
        //viewLifecycleOwner добавили, чтобы удалять ссылку на созданный слушатель
//        requireActivity().onBackPressedDispatcher
//            .addCallback(viewLifecycleOwner,object : OnBackPressedCallback(true){
//                override fun handleOnBackPressed() {
//                    retryGame()
//                }
//            })
        //второй вариант написания
        val callback =object : OnBackPressedCallback(true){
                        override fun handleOnBackPressed() {
                            retryGame()
                        }
                    }
              requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner,callback)

        binding.buttonRetry.setOnClickListener{
            retryGame()
        }
        binding.emojiResult.setImageResource(getSmileResId())


        binding.tvRequiredAnswers.text = String.format(getString(R.string.required_score)
            , resultGame.gameSettings.minCountOfRightAnswers)
        binding.tvScoreAnswers.text = String.format(
            getString(R.string.score_answers),
            resultGame.countOfAnswers
        )
        binding.tvRequiredPercentage.text = String.format(
            getString(R.string.required_percentage),
            resultGame.gameSettings.minPercentOfRightAnswers
        )
        binding.tvScorePercentage.text = String.format(
            getString(R.string.score_percentage),
            getPercentOfRightAnswers()
        )
    }
    private fun getSmileResId(): Int {
        return if (resultGame.winner) {
            R.drawable.ic_smile
        } else {
            R.drawable.ic_sad
        }
    }
    private fun retryGame(){
        requireActivity().supportFragmentManager
            //переходим к GameFragment и удаляем его из стека, т.е. встаем перед ним
            .popBackStack(GameFragment.NAME_LEVEL,FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
    private fun parseResultGame(){
//        resultGame = requireArguments().getParcelable<GameResult>(KEY_RESULT_GAME) as GameResult
//второй вариант через let
         requireArguments().getParcelable<GameResult>(KEY_RESULT_GAME)?.let {
           resultGame = it
        }
    }
    private fun getPercentOfRightAnswers() = with(resultGame) {
        if (countOfQuestions == 0) {
            0
        } else {
            ((countOfAnswers / countOfQuestions.toDouble()) * 100).toInt()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object{
        const val KEY_RESULT_GAME = "result"
        fun newInstance(game: GameResult): GameFinishedFragment{
            val args = Bundle()
            args.putParcelable(KEY_RESULT_GAME,game)
            val fragment = GameFinishedFragment()
            fragment.arguments = args
            return fragment
        }
    }
}