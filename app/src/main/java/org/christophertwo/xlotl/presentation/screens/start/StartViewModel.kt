package org.christophertwo.xlotl.presentation.screens.start

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.christophertwo.xlotl.domain.usecase.SignInWithGoogleUseCase

class StartViewModel(
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val context: Context,
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(StartState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                try {
                    val gso =
                        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken("334453922289-0ob8m7j8iaq9em4hladpfa9819gdakis.apps.googleusercontent.com")
                            .requestEmail()
                            .build()
                    val googleSignInClient = GoogleSignIn.getClient(context, gso)
                    _state.update {
                        it.copy(
                            googleSignInClient = googleSignInClient
                        )
                    }
                } catch (e: Exception) {
                    _state.update {
                        it.copy(
                            error = e.message ?: "Unknown error"
                        )
                    }
                }
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = StartState()
        )

    fun onAction(action: StartAction) {
        when (action) {
            is StartAction.LoginWithGoogle -> {
                viewModelScope.launch {
                    try {
                        signInWithGoogleUseCase.invoke(
                            result = action.result,
                            onAuthComplete = {
                                _state.update { it.copy(isLoggedIn = true) }
                            },
                            onAuthError = {
                                _state.update { it.copy(error = it.error, isLoggedIn = false) }
                            }
                        )
                    } catch (e: Exception) {
                        _state.update {
                            it.copy(
                                error = e.message ?: "Unknown error"
                            )
                        }
                    }
                }
            }
        }
    }
}