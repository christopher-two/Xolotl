package org.christophertwo.xlotl.presentation.screens.start

import com.google.android.gms.auth.api.signin.GoogleSignInClient

data class StartState(
    val googleSignInClient: GoogleSignInClient? = null,
    val isLoggedIn: Boolean = false,
    val error: String? = null
)