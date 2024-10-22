import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

import com.depi.myapplication.R

class EditPasswordFragment: Fragment(R.layout.fragment_edit_password) {

    private lateinit var inputNewPassword: EditText
    private lateinit var inputConfirmPassword: EditText
    private lateinit var btnToggleNewPassword: ImageButton
    private lateinit var btnToggleConfirmPassword: ImageButton
    private lateinit var btnContinue: Button
    private lateinit var btnBack: ImageButton

    private var isNewPasswordVisible = false
    private var isConfirmPasswordVisible = false

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inputNewPassword = view.findViewById(R.id.inputNewPassword) // Ensure correct ID is assigned
        inputConfirmPassword = view.findViewById(R.id.inputConfirmPassword)
        btnToggleNewPassword = view.findViewById(R.id.btnToggleNewPassword)
        btnToggleConfirmPassword = view.findViewById(R.id.btnToggleConfirmPassword)
        btnContinue = view.findViewById(R.id.btnContinue)
        btnBack = view.findViewById(R.id.btnBack)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Toggle password visibility for new password
        btnToggleNewPassword.setOnClickListener {
            isNewPasswordVisible = !isNewPasswordVisible
            togglePasswordVisibility(inputNewPassword, btnToggleNewPassword, isNewPasswordVisible)
        }

        // Toggle password visibility for confirm password
        btnToggleConfirmPassword.setOnClickListener {
            isConfirmPasswordVisible = !isConfirmPasswordVisible
            togglePasswordVisibility(inputConfirmPassword, btnToggleConfirmPassword, isConfirmPasswordVisible)
        }

        // Validate and update password
        btnContinue.setOnClickListener {
            val newPassword = inputNewPassword.text.toString()
            val confirmPassword = inputConfirmPassword.text.toString()

            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else if (newPassword != confirmPassword) {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                // Update password in Firebase
                updatePassword(newPassword)
            }
        }

        // Back button functionality
        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    // Function to update password in Firebase
    private fun updatePassword(newPassword: String) {
        val user = auth.currentUser

        if (user != null) {
            user.updatePassword(newPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Password updated successfully", Toast.LENGTH_SHORT).show()
                        // Navigate to success page after updating password
                        findNavController().navigate(R.id.action_edit_password_to_rest_password_success)
                    } else {
                        Toast.makeText(requireContext(), "Failed to update password: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        } else {
            Toast.makeText(requireContext(), "No user is signed in", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to toggle password visibility
    private fun togglePasswordVisibility(editText: EditText, toggleButton: ImageButton, isVisible: Boolean) {
        if (isVisible) {
            editText.inputType = android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            toggleButton.setImageResource(R.drawable.eye_off) // Change to visible eye icon
        } else {
            editText.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            toggleButton.setImageResource(R.drawable.eye_off) // Change to hidden eye icon
        }
        // Move the cursor to the end of the text
        editText.setSelection(editText.text.length)
    }
}
