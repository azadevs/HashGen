package android.azadevs.hashgen.ui.success

import android.azadevs.hashgen.R
import android.azadevs.hashgen.databinding.FragmentSuccessBinding
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SuccessFragment : Fragment(R.layout.fragment_success) {

    private var _binding: FragmentSuccessBinding? = null

    private val binding get() = _binding!!

    private val args by navArgs<SuccessFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSuccessBinding.bind(view)

        binding.tvHashText.text = args.hashData

        binding.btnCopy.setOnClickListener {
            onCopyClicked()
        }

        binding.ivBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun onCopyClicked() {
        lifecycleScope.launch {
            applyAnimations()
            copyToClipboard(args.hashData)
        }
    }

    private fun copyToClipboard(hashData: String) {
        val clipboardManager =
            requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.setPrimaryClip(
            android.content.ClipData.newPlainText("Encrypted Text", hashData)
        )
    }

    private suspend fun applyAnimations() {
        binding.copiedMessage.tvCopied.animate().alpha(1f).duration = 200L
        delay(1500L)
        binding.copiedMessage.tvCopied.animate().alpha(0f).duration = 200L
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}