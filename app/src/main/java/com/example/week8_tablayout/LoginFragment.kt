package com.example.week8_tablayout

import android.content.Context
import android.os.Bundle
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.week8_tablayout.databinding.FragmentLoginBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    lateinit var dataReceiver: OnDataRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val pager = activity?.findViewById<ViewPager2>(R.id.view_pager)

        with(binding) {
            val credential = dataReceiver.getData()

            btnLogin.setOnClickListener {
                val loginUsername = editTextUsername.text.toString()
                val loginPassword = editTextPassword.text.toString()
                // Validation
                var validated = true

                if (loginUsername.isEmpty()) {
                    validated = false
                    editTextUsername.error = "Username is required"
                }

                if (loginPassword.isEmpty()) {
                    validated = false
                    editTextPassword.error = "Password is required"
                }

                if (loginUsername != credential["username"] ||
                    loginPassword != credential["password"]) {
                    validated = false
                    editTextUsername.error = "User not found"
                    editTextPassword.error = "User not found"
                }

                if (validated) {
                    pager?.currentItem = 0
                }
            }

            //Clickable span to redirect to register fragment
            val spannableString = android.text.SpannableString(textViewRedirectRegister.text)
            val clickableSpan = object : ClickableSpan() {
                override fun onClick(widget: android.view.View) {
                    pager?.currentItem = 0
                }
            }

            spannableString.setSpan(clickableSpan, 12, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            textViewRedirectRegister.text = spannableString
            textViewRedirectRegister.movementMethod = LinkMovementMethod.getInstance()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface OnDataRequest {
        fun getData() : MutableMap<String, String>
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataReceiver = context as OnDataRequest
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}