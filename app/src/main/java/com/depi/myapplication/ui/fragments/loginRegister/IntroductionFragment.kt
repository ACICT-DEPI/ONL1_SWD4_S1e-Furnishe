package com.depi.myapplication.ui.fragments.loginRegister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.depi.myapplication.R
import com.depi.myapplication.databinding.FragmentIntrodcutionBinding
import com.depi.myapplication.ui.viewmodel.loginregister.IntroductionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroductionFragment : Fragment(R.layout.fragment_introdcution) {
    private var _binding: FragmentIntrodcutionBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<IntroductionViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIntrodcutionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonStart.setOnClickListener {
            findNavController().navigate(R.id.action_introductionFragment_to_accountOptionsFragment)
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

