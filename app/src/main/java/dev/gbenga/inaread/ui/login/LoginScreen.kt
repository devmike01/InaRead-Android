package dev.gbenga.inaread.ui.login

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withLink
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.tokens.StringTokens
import dev.gbenga.inaread.ui.customs.AuthParentColumn
import dev.gbenga.inaread.ui.home.UnitLaunchEffect
import dev.gbenga.inaread.utils.rememberNavigationDelegate
import dev.gbenga.inaread.ui.customs.InaSingleTextField
import dev.gbenga.inaread.ui.customs.UiStateWithLoadingBox
import dev.gbenga.inaread.ui.dialogs.LoadingDialog
import dev.gbenga.inaread.ui.theme.Dark
import dev.gbenga.inaread.utils.UiStateWithIdle
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    navController: NavController
) {

    val snackbarHost = remember { SnackbarHostState() }
    val uiState by loginViewModel.state.collectAsStateWithLifecycle()

    UnitLaunchEffect {
        loginViewModel.snackBarEvent.collect { msg ->
            if (msg.isNotEmpty()){
                snackbarHost.showSnackbar(msg)
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHost){
                Snackbar(
                    containerColor = Dark,
                    snackbarData = it
                )
            }
        }
    ) {

        AuthParentColumn(
            modifier = Modifier.padding(it),
            title = StringTokens.Auth.LoginTitle,
            subTitle = StringTokens.Auth.LoginDescription,){
            val navDelegate = rememberNavigationDelegate(navController)

            val fieldState = rememberLoginFieldState()
            val showLoading = uiState.login is UiStateWithIdle.Loading
            val inState = remember { derivedStateOf { uiState.login } }

            when(val login = inState.value){
                is UiStateWithIdle.Success<*> -> {
                    loginViewModel.navigateToDashboard()
                }
                is UiStateWithIdle.Error -> {
                    loginViewModel.showSnackBar(login.requiredMessage)
                }
                else -> {
                }
            }

            LoadingDialog(showLoading)

            UnitLaunchEffect {
                loginViewModel.navigator.collect {
                    navDelegate.handleEvents(it)
                }
            }

            InaSingleTextField(
                value =fieldState.username,
                placeholder = StringTokens.Auth.UsernamePlaceholder){
                fieldState.onUsernameChanged(it)
            }

            InaSingleTextField(value =fieldState.password,
                placeholder = StringTokens.Auth.PasswordPlaceholder,
                passwordVisible = false,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)){
                fieldState.onPasswordChanged(it)
            }

            ForgotPasswordContent {
                loginViewModel.gotoForgotPassword()
            }
            Button(onClick = {
                loginViewModel.logIn(fieldState.username, fieldState.password)
            },
                modifier = Modifier
                    .padding(vertical = DimenTokens.Padding.Normal)
                    .height(DimenTokens.Auth.ButtonHeight)
                    .fillMaxWidth()) {
                Text(StringTokens.Auth.Login,
                    style = MaterialTheme.typography.bodyMedium)
            }

            TextWithLink(modifier = Modifier.align(Alignment.BottomCenter),
                text = StringTokens.Auth.DontHaveAccount,
                linkText = StringTokens.Auth.SignUp){
                loginViewModel.gotoSignUp()
            }
        }
    }
}


@Composable
fun ForgotPasswordContent(onLinkClick: () -> Unit){

    Box(modifier = Modifier
        //.padding(vertical = DimenTokens.Padding.xSmall)
        .fillMaxWidth()) {
        TextButton(onClick = {
            // handle click
            onLinkClick()
            //loginViewModel.logIn(fieldState.username, fieldState.password)
        },
            colors = ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.tertiary
            ),
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Text(StringTokens.Auth.ForgotPassword,
                style = MaterialTheme.typography
                    .bodySmall.copy(
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.W700
                    ))
        }
    }
}



@Composable
fun TextWithLink(
    modifier: Modifier,
    text: String,
    linkText: String,
    onLinkClick: () -> Unit){

    val annotatedDontHaveAcct = buildAnnotatedString {
        append(text)
        withLink(link = LinkAnnotation.Clickable(
            tag = linkText,
            TextLinkStyles(SpanStyle(
                color = MaterialTheme.colorScheme.tertiary,
                fontWeight = FontWeight.W700)),
        ){
            onLinkClick()
            //loginViewModel.gotoSignUp()
        }){
            append(linkText)
        }
    }
    Text(annotatedDontHaveAcct,
        modifier = modifier,
        style = MaterialTheme.typography
            .bodyMedium)
}
