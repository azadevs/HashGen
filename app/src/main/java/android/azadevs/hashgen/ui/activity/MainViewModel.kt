package android.azadevs.hashgen.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Created by : Azamat Kalmurzayev
 * 20/09/24
 */
class MainViewModel : ViewModel() {

    private var _showSplashScreen = MutableStateFlow(true)
    val showSplashScreen = _showSplashScreen.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1500L)
            _showSplashScreen.value = false
        }

    }
}