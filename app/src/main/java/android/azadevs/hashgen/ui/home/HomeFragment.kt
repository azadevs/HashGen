package android.azadevs.hashgen.ui.home

import android.azadevs.hashgen.R
import android.azadevs.hashgen.databinding.FragmentHomeBinding
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val viewmodel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        configureAutoCompleteTextView()

        binding.btnGenerate.setOnClickListener {
            onGenerateClicked()
        }

    }

    private fun onGenerateClicked() {
        if (binding.edtText.text.isEmpty()) {
            Snackbar.make(binding.root, "Field is empty", Snackbar.LENGTH_LONG).show()
        } else {
            lifecycleScope.launch {
                applyAnimations()
                viewmodel.navigateSuccessFragment.collect {
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragmentToSuccessFragment(
                            it
                        )
                    )
                }
            }
            val text = binding.edtText.text.toString()
            val algorithmType = binding.autoComplete.text.toString()
            viewmodel.getHash(text = text, algorithmType = algorithmType)
        }
    }

    private suspend fun applyAnimations() {
        binding.btnGenerate.isClickable = false
        binding.tvTitle.animate().alpha(0f).duration = 400L
        binding.btnGenerate.animate().alpha(0f).duration = 400L
        binding.textInputLayout.animate().alpha(0f).translationXBy(1200f).duration = 400L
        binding.edtText.animate().alpha(0f).translationXBy(-1200f).duration = 400L
        delay(300)
        binding.successBackground.animate().alpha(1f).duration = 600L
        binding.successBackground.animate().rotation(720f).duration = 600L
        binding.successBackground.animate().scaleXBy(900f).duration = 800L
        binding.successBackground.animate().scaleYBy(900f).duration = 800L
        delay(500)
        binding.ivSuccess.animate().alpha(1f).duration = 1000L
        delay(1500)
    }


    private fun configureAutoCompleteTextView() {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_drop_down,
            resources.getStringArray(R.array.hash_algorithms)
        )
        binding.autoComplete.setAdapter(adapter)
    }

    override fun onResume() {
        super.onResume()
        configureAutoCompleteTextView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}