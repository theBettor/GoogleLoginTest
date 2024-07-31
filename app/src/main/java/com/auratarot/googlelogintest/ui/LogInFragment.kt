package com.auratarot.googlelogintest.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.auratarot.googlelogintest.R
import com.auratarot.googlelogintest.databinding.FragmentLogInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.ktx.BuildConfig

class LogInFragment : Fragment() {
    private lateinit var getResult: ActivityResultLauncher<Intent>
    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LogInViewModel by viewModels()

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setGoogleLogin()
        viewModel.userLiveData.observe(viewLifecycleOwner) {
            binding.ibtLoginGoogle.text = it.displayName
        }
        binding.ibtLoginGoogle.setOnClickListener {
            login()
        }
//        getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//            if (it.resultCode == Activity.RESULT_OK) {
//                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
//                try {
//                    val account = task.getResult(ApiException::class.java)!!
//                    viewModel.getUser(account.idToken!!)
//                    Toast.makeText(requireContext(), "로그인 성공", Toast.LENGTH_SHORT)
//                        .show()
//                    findNavController().navigate(R.id.action_logInFragment_to_homeFragment)
//
//                } catch (e: ApiException) {
//                    Toast.makeText(requireContext(), e.localizedMessage, Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//        getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//                try {
//                    val account = task.getResult(ApiException::class.java)!!
//                    viewModel.getUser(account.idToken!!)
//                    Toast.makeText(requireContext(), "로그인 성공", Toast.LENGTH_SHORT).show()
//                    findNavController().navigate(R.id.action_logInFragment_to_homeFragment)
//                } catch (e: ApiException) {
//                    Toast.makeText(requireContext(), e.localizedMessage, Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
        getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) { //
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    viewModel.getUser(account.idToken!!)
                    Toast.makeText(requireContext(), "로그인 성공", Toast.LENGTH_SHORT).show()
                    Log.d("LogInFragment", "로그인 성공: ${account.displayName}")
                    findNavController().navigate(R.id.action_logInFragment_to_homeFragment)
                } catch (e: ApiException) {
                    Toast.makeText(requireContext(), e.localizedMessage, Toast.LENGTH_SHORT).show()
                    Log.e("LogInFragment", "로그인 실패: ${e.localizedMessage}")
                }
            } else {
                Log.e("LogInFragment", "결과 코드가 Activity.RESULT_OK가 아닙니다: ${result.resultCode}")
                Log.d("result", result.toString())
            }
        }
    }

    private fun login() {
        Log.d("LogInFragment", "로그인 되나?")
        getResult.launch(googleSignInClient.signInIntent)
    }

    private fun setGoogleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id)) // getString으로 추가해주니 unresolved refererences 해결됨
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("LoginFragment", "LoginFragment 성공")
    }

    companion object {
        private const val TAG = "LogInFragment"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}