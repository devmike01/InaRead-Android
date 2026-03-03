package dev.gbenga.inaread.ui.signup

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dev.gbenga.inaread.data.auth.SignUpRequest
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.tokens.StringTokens
import dev.gbenga.inaread.ui.customs.AuthParentColumn
import dev.gbenga.inaread.ui.customs.InaSingleTextField
import dev.gbenga.inaread.ui.customs.TitledColumn
import dev.gbenga.inaread.ui.customs.UiStateLoadingListContent
import dev.gbenga.inaread.ui.customs.UiStateWithLoadingBox
import dev.gbenga.inaread.ui.dialogs.LoadingDialog
import dev.gbenga.inaread.ui.dropdown.country.CountrySuggestionTextField
import dev.gbenga.inaread.ui.home.UnitLaunchEffect
import dev.gbenga.inaread.ui.login.TextWithLink
import dev.gbenga.inaread.ui.theme.Dark
import dev.gbenga.inaread.utils.UiState
import dev.gbenga.inaread.utils.UiStateWithIdle
import dev.gbenga.inaread.utils.rememberNavigationDelegate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(signUpViewModel: SignUpViewModel,
                 navController: NavController) {

    val uiState by signUpViewModel.state.collectAsStateWithLifecycle()
    var meterCategoryState by remember{ mutableStateOf(0) }
    val verticalScrollState = rememberScrollState()
    val navigatorDelegate = rememberNavigationDelegate(navController)
    val fieldState = rememberSignUpFormState()
    var passwordVisible by rememberSaveable { mutableStateOf(false)}
    val isLoading by remember { derivedStateOf { uiState.signUp is UiStateWithIdle.Loading } }
    val errorMsg by remember { derivedStateOf { if (uiState.signUp is UiStateWithIdle.Error){
        (uiState.signUp as UiStateWithIdle.Error).requiredMessage
    } else{
        ""
    }
    } }
    var meterTypeSelection by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    var countryState by rememberSaveable { mutableStateOf("") }
    val isLoadingCountry by remember { derivedStateOf { uiState
        .countries is UiState.Loading } }
    val countrySuggestions by remember { derivedStateOf { when (val countryState = uiState.countries){
        is UiState.Success -> countryState.data
        else -> emptyList()
    }
    } }
    var countrySelection by remember { mutableStateOf("") }


    LaunchedEffect(meterCategoryState,
        meterTypeSelection,
        fieldState.confirmPassword,
        fieldState.password,
        fieldState.email,
        fieldState.username,
        fieldState.meterNo,
        countrySelection) {
        signUpViewModel.enableSubmit(
            meterCategory = meterCategoryState,
            meterTypeSelection= meterTypeSelection,
            username = fieldState.username,
            email = fieldState.email,
            password = fieldState.password,
            meterNumber = fieldState.meterNo,
            confirmPassword = fieldState.confirmPassword,
            countrySelection = countrySelection

        )
    }

    LaunchedEffect(errorMsg) {
        if (errorMsg.isNotBlank()){
            snackbarHostState.showSnackbar(errorMsg)
        }
    }

    LoadingDialog(isLoading)

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
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState){
                Snackbar(
                    containerColor = Dark,
                    snackbarData = it
                )
            }
        }
        ) {
        UnitLaunchEffect {
            signUpViewModel.navigator.collect { navigationEvent ->
                navigatorDelegate.handleEvents(navigationEvent)
            }
        }

        AuthParentColumn(StringTokens.Auth.SignUpTitle,
            subTitle = StringTokens.Auth.LoginDescription,
            modifier = Modifier.padding(it)
                .verticalScroll(verticalScrollState)){

            InaSingleTextField(
                value =fieldState.username,
                placeholder = StringTokens.Auth.UsernamePlaceholder){ username ->
                fieldState.onUsernameChanged(username)
            }

            InaSingleTextField(value =fieldState.email,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                placeholder = StringTokens.Auth.EmailPlaceholder){ email ->
                fieldState.onEmailChanged(email)
            }

            InaSingleTextField(value =fieldState.meterNo,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                placeholder = StringTokens.Auth.MeterNumber){ meterNo ->
                fieldState.onMeterNumberChanged(meterNo)
            }

            CountrySuggestionTextField(value = countryState,
                data = countrySuggestions,
                onValueChanged = { value ->
                    countryState = value
                    signUpViewModel.queryCountries(value)
                }, onCountrySelected = { selected->
                    countryState = selected.name
                    countrySelection = selected.iso
                })

            Column(horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()) {
                Text(StringTokens.SignUp.SelectMeterCategoryTitle,
                    style = MaterialTheme.typography.bodyMedium
                        .copy(fontWeight = FontWeight.W300),
                    modifier = Modifier
                        .padding(vertical = DimenTokens.Padding.XSmall))
                UiStateLoadingListContent(uiState.meterCategory, errorRequest = {
                    Box(modifier = Modifier.fillMaxWidth()){
                        Text(it, modifier = Modifier.align(Alignment.Center))
                    }
                }) { categories ->
                    MeterCategoryBar(meterCategoryState,
                        categories){ selectedId ->
                        // On select do something
                        meterCategoryState = selectedId
                    }
                }
            }


            Column(horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()) {
                Text(StringTokens.SignUp.SelectSubscriptionType,
                    style = MaterialTheme.typography.bodyMedium
                        .copy(fontWeight = FontWeight.W300),
                    modifier = Modifier
                        .padding(vertical = DimenTokens.Padding.XSmall))
                UiStateLoadingListContent(uiState.meterTypes, errorRequest = {
                    Box(modifier = Modifier.fillMaxWidth()){
                        Text(it, modifier = Modifier.align(Alignment.Center))
                    }
                }) { meterTypes ->
                    MeterSubscriptionType(meterTypeSelection,
                        meterTypes){ selection ->
                        // On select do something
                        meterTypeSelection = selection
                    }
                }
            }

            InaSingleTextField(value =fieldState.password,
                passwordVisible = passwordVisible,
                placeholder = StringTokens.Auth.PasswordPlaceholder){ password ->
                fieldState.onPasswordChanged(password)
            }

            InaSingleTextField(value =fieldState.confirmPassword,
                passwordVisible = passwordVisible,
                placeholder = StringTokens.Auth.ConfirmPasswordPlaceholder,
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisible = !passwordVisible
                    }) {
                        Icon(Icons.Default.VisibilityOff
                            .takeIf { passwordVisible } ?: Icons.Default.Visibility,
                            contentDescription = "")
                    }
                }){ confirmPassword ->
                fieldState.onConfirmPasswordChanged(confirmPassword)
            }



            Button(onClick = {
                signUpViewModel.signUp(SignUpRequest(
                    username = fieldState.username,
                    password = fieldState.password,
                    meterCategoryId = meterCategoryState,
                    countryId = countrySelection,
                    meterType = meterTypeSelection,
                    meterNo = fieldState.meterNo,
                    email = fieldState.email
                ))
            },
                enabled = uiState.enabledSubmit,
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