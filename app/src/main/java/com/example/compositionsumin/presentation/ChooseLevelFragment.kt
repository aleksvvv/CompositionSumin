package com.example.compositionsumin.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.compositionsumin.R
import com.example.compositionsumin.databinding.FragmentChooseLevelBinding
import com.example.compositionsumin.domain.entity.Level


class ChooseLevelFragment : Fragment() {

    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonLevelEasy.setOnClickListener{
            launchGame(Level.EASY)
        }
        binding.buttonLevelTest.setOnClickListener{
            launchGame(Level.TEST)
        }
        binding.buttonLevelNorm.setOnClickListener{
            launchGame(Level.NORMAL)
        }
        binding.buttonLevelHard.setOnClickListener{
            launchGame(Level.HARD)
        }
    }
    private fun launchGame(level: Level){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container,GameFragment.newInstance(level))
            .addToBackStack(GameFragment.NAME_LEVEL)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object{
        const val NAME = "ChooseLevelFragment"
        fun newInstance(): ChooseLevelFragment{
            return ChooseLevelFragment()
        }
    }
}