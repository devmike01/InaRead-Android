package dev.gbenga.inaread.ui.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.tokens.StringTokens
import dev.gbenga.inaread.ui.customs.AuthParentColumn
import dev.gbenga.inaread.ui.home.UnitLaunchEffect
import dev.gbenga.inaread.utils.rememberNavigationDelegate
import dev.gbenga.inaread.ui.customs.InaSingleTextField

@Composable
fun LoginScreen(loginViewModel: LoginViewModel = hiltViewModel(),
                navController: NavController) {

    AuthParentColumn(StringTokens.Auth.LoginTitle,
        subTitle = StringTokens.Auth.LoginDescription,
        modifier = Modifier){

        val navDelegate = rememberNavigationDelegate(navController)
        val fieldState = rememberLoginFieldState()

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
            placeholder = StringTokens.Auth.PasswordPlaceholder){
            fieldState.onPasswordChanged(it)
        }

        Box(modifier = Modifier
            //.padding(vertical = DimenTokens.Padding.xSmall)
            .fillMaxWidth()) {
            TextButton(onClick = {
                // handle click
                loginViewModel.gotoForgotPassword()
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

        Button(onClick = {
            loginViewModel.logIn("", "")
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
