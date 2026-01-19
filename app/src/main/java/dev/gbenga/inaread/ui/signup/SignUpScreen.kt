package dev.gbenga.inaread.ui.signup

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.tokens.StringTokens
import dev.gbenga.inaread.ui.customs.AuthParentColumn
import dev.gbenga.inaread.ui.customs.InaSingleTextField
import dev.gbenga.inaread.ui.home.UnitLaunchEffect
import dev.gbenga.inaread.ui.login.TextWithLink
import dev.gbenga.inaread.utils.rememberNavigationDelegate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(signUpViewModel: SignUpViewModel = hiltViewModel(),
                 navController: NavController) {

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                Text("Back")
            }, navigationIcon = {
                IconButton(onClick = {
                    signUpViewModel.gotoLogin()
                }) {
                    Icon(Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = StringTokens.SignUp.GoBack)
                }
            },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ))
        }) {

        val navigatorDelegate = rememberNavigationDelegate(navController)

        UnitLaunchEffect {
            signUpViewModel.navigator.collect { navigationEvent ->
                navigatorDelegate.handleEvents(navigationEvent)
            }
        }

        AuthParentColumn(StringTokens.Auth.SignUpTitle,
            subTitle = StringTokens.Auth.LoginDescription,
            modifier = Modifier.padding(it)){
            val fieldState = rememberSignUpFormState()

            InaSingleTextField(
                value =fieldState.username,
                placeholder = StringTokens.Auth.UsernamePlaceholder){
                fieldState.onUsernameChanged(it)
            }

            InaSingleTextField(value =fieldState.email,
                placeholder = StringTokens.Auth.EmailPlaceholder){
                fieldState.onEmailChanged(it)
            }


            InaSingleTextField(value =fieldState.password,
                placeholder = StringTokens.Auth.PasswordPlaceholder){
                fieldState.onPasswordChanged(it)
            }

            InaSingleTextField(value =fieldState.confirmPassword,
                placeholder = StringTokens.Auth.ConfirmPasswordPlaceholder){
                fieldState.onConfirmPasswordChanged(it)
            }



            Button(onClick = {},
                modifier = Modifier
                    .padding(vertical = DimenTokens.Padding.Normal)
                    .height(DimenTokens.Auth.ButtonHeight)
                    .fillMaxWidth()) {
                Text(StringTokens.Auth.SignUp,
                    style = MaterialTheme.typography.bodyMedium)
            }

            TextWithLink(modifier = Modifier.align(Alignment.BottomCenter),
                text = StringTokens.Auth.AlreadyHaveAccount,
                linkText = StringTokens.Auth.Login){
                // Go to login
                signUpViewModel.gotoLogin()
            }
        }
    }
}