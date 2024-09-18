package android.azadevs.hashgen.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.security.MessageDigest

/**
 * Created by : Azamat Kalmurzayev
 * 18/09/24
 */
class HomeViewModel : ViewModel() {

    private val _navigateSuccessFragment = MutableStateFlow("")
    val navigateSuccessFragment = _navigateSuccessFragment.asStateFlow()

    fun getHash(text: String, algorithmType: String) {
        val bytes = MessageDigest.getInstance(algorithmType).digest(text.toByteArray())
        navigateToSuccessFragment(toHex(bytes))
    }

    private fun toHex(byteArray: ByteArray): String {
        return byteArray.joinToString("") { "%02x".format(it) }
    }

    private fun navigateToSuccessFragment(hash: String) {
        _navigateSuccessFragment.value = hash
    }
}