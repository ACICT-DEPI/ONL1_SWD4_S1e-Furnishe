package com.depi.myapplication.ui.fragments.loginRegister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.depi.myapplication.R
import com.depi.myapplication.databinding.FragmentDigitCodeBinding

class OTPFragment : Fragment() {
    private lateinit var binding: FragmentDigitCodeBinding
    private lateinit var otpCode: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDigitCodeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the OTP code from arguments
        otpCode = arguments?.getString("otpCode", "") ?: ""
        if (otpCode.isEmpty()) {
            Toast.makeText(context, "No OTP found", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()  // Go back if OTP is empty
        } else {
            Toast.makeText(context, "OTP for verification is $otpCode", Toast.LENGTH_SHORT).show()
        }

        // Set up button listeners
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()  // Navigate back to the previous screen
        }

        binding.btnContinue.setOnClickListener {
            Toast.makeText(context, "Navigating to Reset Password", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_digitcode_to_resetPassword)
        }

    }

}