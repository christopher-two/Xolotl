package org.christophertwo.xlotl.presentation.screens.start

import androidx.activity.result.ActivityResult

sealed interface StartAction {
    data class LoginWithGoogle(val result: ActivityResult) : StartAction
}