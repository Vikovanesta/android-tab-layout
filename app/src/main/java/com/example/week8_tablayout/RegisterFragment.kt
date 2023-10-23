package com.example.week8_tablayout

import android.content.Context
import android.os.Bundle
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.week8_tablayout.databinding.FragmentRegisterBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    lateinit var dataPasser: OnDataPass

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
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val pager = activity?.findViewById<ViewPager2>(R.id.view_pager)

        with(binding) {

            btnRegister.setOnClickListener {
                val credential: MutableMap<String, String> = mutableMapOf(
                    "username" to editTextUsername.text.toString(),
                    "email" to editTextEmail.text.toString(),
                    "phone" to editTextPhone.text.toString(),
                    "password" to editTextPassword.text.toString()
                )
                // Validation
                var validated = true

                if (credential["username"]?.isEmpty() == true) {
                    editTextUsername.error = "Username is required"
                    validated = false
                }

                if (credential["email"]?.isEmpty() == true) {
                    editTextEmail.error = "Email is required"
                    validated = false
                } else if (!credential["email"]?.let { it1 ->
                        android.util.Patterns.EMAIL_ADDRESS.matcher(
                            it1
                        ).matches()
                    }!!){
                    editTextEmail.error = "Invalid email format"
                    validated = false
                }

                if (credential["phone"]?.isEmpty() == true) {
                    editTextPhone.error = "Phone is required"
                    validated = false
                }

                if (credential["password"]?.isEmpty() == true) {
                    editTextPassword.error = "Password is required"
                    validated = false
                } else if (credential["password"]?.length!! < 8) {
                    editTextPassword.error = "Password must be at least 8 characters long"
                    validated = false
                }

                if (validated) {
                    passData(credential)
                    if (pager != null) {
                        pager.currentItem = 1
                    }
                }
            }

            // Clickable span
            val spannableString = android.text.SpannableString(textViewRedirectLogin.text)
            val clickableSpan = object : android.text.style.ClickableSpan() {
                override fun onClick(widget: android.view.View) {
                    if (pager != null) {
                        pager.currentItem = 1
                    }
                }
            }

            spannableString.setSpan(clickableSpan, 25, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            textViewRedirectLogin.text = spannableString
            textViewRedirectLogin.movementMethod = LinkMovementMethod.getInstance()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface OnDataPass {
        fun onDataPass(data: MutableMap<String, String>?)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPasser = context as OnDataPass
    }

    fun passData(data: MutableMap<String, String>?) {
        dataPasser.onDataPass(data)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}