package org.christophertwo.xlotl.presentation.screens.start

import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.brands.Google
import org.christophertwo.xlotl.R
import org.christophertwo.xlotl.presentation.components.AnimatedTextCarousel
import org.christophertwo.xlotl.presentation.components.ButtonPrimary
import org.christophertwo.xlotl.presentation.components.CreatedByOverride
import org.christophertwo.xlotl.presentation.components.backgroundAnimated
import org.christophertwo.xlotl.presentation.theme.XólotlTheme
import org.christophertwo.xlotl.core.RoutesApp

@Composable
fun StartRoot(
    viewModel: StartViewModel,
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    StartScreen(
        state = state,
        navController = navController,
        onAction = viewModel::onAction
    )
}

@Composable
private fun StartScreen(
    state: StartState,
    navController: NavController,
    onAction: (StartAction) -> Unit,
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result -> onAction(StartAction.LoginWithGoogle(result)) }
    )

    LaunchedEffect(key1 = state.isLoggedIn) {
        if (state.isLoggedIn) {
            navController.navigate(RoutesApp.Home.route) { // Asegúrate que "home_screen" sea tu ruta de destino
                popUpTo(RoutesApp.Start.route) { // Asegúrate que "start_screen" sea la ruta actual
                    inclusive = true
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = backgroundAnimated(
                        colorPrimary = colorScheme.secondaryContainer,
                        colorSecondary = colorScheme.background
                    )
                )
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Spacer(modifier = Modifier.weight(0.2f))
            Icon(
                painter = painterResource(R.drawable.overrideblanco),
                contentDescription = "Logo",
                modifier = Modifier.size(50.dp),
                tint = colorScheme.onBackground
            )
            Spacer(modifier = Modifier.weight(1f))
            AnimatedTextCarousel(
                phrases = listOf(
                    "Always be aware of your surroundings.",
                    "Trust your instincts; they are usually right.",
                    "Avoid walking alone at night if possible.",
                    "Keep emergency contacts readily available.",
                    "Secure your home before leaving or sleeping."
                ),
                modifier = Modifier
            )
            Spacer(modifier = Modifier.weight(1f))
            CreatedByOverride()
            Spacer(modifier = Modifier.height(16.dp))
            Buttons(
                context = LocalContext.current, // Pass context to Buttons if it needs it for other things
                onGoogleSignInClick = {
                    state.googleSignInClient?.signInIntent.let { input ->
                        launcher.launch(input = input ?: Intent())
                    }
                }
            )
        }
    )
}

@Composable
internal fun Buttons(
    context: Context,
    onGoogleSignInClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f)
            .background(
                color = colorScheme.surfaceContainerLowest,
                shape = RoundedCornerShape(
                    topStart = 32.dp,
                    topEnd = 32.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp // Asegúrate de que ambas esquinas inferiores sean 0 si es lo deseado
                )
            ),
        contentAlignment = Alignment.Center,
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.weight(1f))
                ButtonPrimary(
                    // Cambiado para usar el string de Google Sign-In
                    text = stringResource(R.string.continue_with_google), // Asegúrate de tener este string
                    onClick = onGoogleSignInClick, // Acción para Google Sign-In
                    modifier = Modifier.fillMaxWidth(.8f),
                    icon = FontAwesomeIcons.Brands.Google,
                    containerColor = colorScheme.primaryContainer,
                    contentColor = colorScheme.onPrimaryContainer,
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    )
}


@Preview
@Composable
private fun Preview() {
    XólotlTheme {
        StartScreen(
            state = StartState(),
            onAction = {},
            navController = NavController(LocalContext.current)
        )
    }
}