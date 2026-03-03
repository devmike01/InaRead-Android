package dev.gbenga.inaread.ui.signup

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gbenga.inaread.data.auth.SignUpRequest
import dev.gbenga.inaread.data.datastore.Messenger
import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.CountryResponse
import dev.gbenga.inaread.domain.usecase.CreateNewUserUseCase
import dev.gbenga.inaread.domain.usecase.GetCountryUseCase
import dev.gbenga.inaread.domain.usecase.GetMeterCategoryUseCase
import dev.gbenga.inaread.domain.usecase.MeterTypesUseCase
import dev.gbenga.inaread.ui.dropdown.country.CountryData
import dev.gbenga.inaread.ui.home.UiData
import dev.gbenga.inaread.ui.validator.AuthFieldsValidator
import dev.gbenga.inaread.utils.InaReadViewModel
import dev.gbenga.inaread.utils.InaReadViewModelV2
import dev.gbenga.inaread.utils.NavigationEvent
import dev.gbenga.inaread.utils.UiState
import dev.gbenga.inaread.utils.UiStateWithIdle
import dev.gbenga.inaread.utils.nav.InaScreen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val getMeterCategoryUseCase: GetMeterCategoryUseCase,
    private val createNewUserUseCase: CreateNewUserUseCase,
    private val meterTypesUseCase: MeterTypesUseCase,
    private val getCountryUseCase: GetCountryUseCase,
    private val messenger: Messenger,
    private val authFieldsValidator: AuthFieldsValidator) : InaReadViewModelV2<SignUpUiState> (SignUpUiState()){

        private var countries : List<CountryResponse>? = null

    init {
        fetchMeterCategory()
        fetchMeterTypes()
        fetchCountry()
    }


    fun queryCountries(query: String){
        val queryCountries = countries?.toMutableList()
        setState { it
            .copy(
                countries = UiState.Success(countries ?.let { country ->
                    queryCountries
                        ?.filter { qc -> qc.name.lowercase()
                            .startsWith(query.lowercase()) &&  query.isNotEmpty()}
                        ?.map { c -> CountryData(c.name, c.isoCode) }
                } ?: listOf(CountryData(
                    name = "Please wait...",
                    iso = ""
                )))) }
    }


    fun fetchCountry(){
        viewModelScope.launch {
            setState { it.copy(countries = UiState.Loading) }
            when(val countryState = getCountryUseCase()){
                is RepoResult.Success -> {
                    countries = countryState.data
                }
                is RepoResult.Error -> {
                    Log.d("fetchCountry@1", "fetchCountry: ${countryState.message}")
                    setState { it.copy(
                        countries = UiState.Error(countryState.message)
                    ) }
                }
            }
        }
    }

    private fun fetchMeterTypes(){
        viewModelScope.launch {
            when(val repoResult = meterTypesUseCase()){
                is RepoResult.Success -> {
                    setState { it.copy(meterTypes = UiState.Success(repoResult.data)) }
                }
                is RepoResult.Error -> {
                    setState { it.copy(meterTypes = UiState.Error(repoResult.message)) }
                }
            }
        }
    }

    private fun fetchMeterCategory(){
        viewModelScope.launch {
            when(val repoResult = getMeterCategoryUseCase()){
                is RepoResult.Success -> {
                    setState { it.copy(
                        meterCategory = UiState
                            .Success(repoResult.data
                                .map { response ->  MeterCategory(
                                    id = response.id,
                                    name = response.name
                                ) })
                    ) }
                }
                is RepoResult.Error -> {
                    setState{it.copy(
                        meterCategory = UiState.Error(repoResult.message))}
                }
            }
        }
    }

    fun enableSubmit(username: String,
                     meterTypeSelection: String,
                     email: String,
                     password: String,
                     confirmPassword: String,
                     meterNumber: String,
                     meterCategory: Int,
                     countrySelection: String){
        val isNotValid = listOf(authFieldsValidator
            .validateNumberOnly(meterNumber),
            authFieldsValidator.validateUsername(username),
            authFieldsValidator.validateEmail(email).also {
                Log.i("enableSubmit01", "$email: " +
                        "${it.exceptionOrNull()} _ ${it.getOrNull()}")
            },
            authFieldsValidator.validatePassword(password),
            authFieldsValidator.validatePassword(confirmPassword))
            .any {
                it.getOrDefault("").isBlank() }
        setState { it.copy(
            enabledSubmit = !isNotValid
                    && meterCategory > 0
                    && countrySelection.isNotEmpty()
                    && meterTypeSelection.isNotBlank()
                    && password == confirmPassword
        ) }

    }

    fun signUp(request: SignUpRequest){
        viewModelScope.launch {
            setState { it.copy(signUp = UiStateWithIdle.Loading) }
            when(val signUp = createNewUserUseCase(request)){
                is RepoResult.Success -> {
                    setState { it.copy(
                        signUp = UiStateWithIdle.Success("Congratulations ${signUp.data.username}!, your new account has been successfully created.")
                    ) }
                    messenger.sendMessage("Congratulations ${signUp.data.username}!, your new account has been successfully created.")
                    navigate(NavigationEvent.NavigateTaskTop(InaScreen.Login))
                }
                is RepoResult.Error -> {
                    setState { it.copy(
                        signUp = UiStateWithIdle.Error(signUp.message)
                    ) }
                }
            }
        }
    }

    fun gotoLogin(){
        navigate(NavigationEvent.NavigateBack)
    }


}



fun List<CountryData>.binarySearch(q: String): String{
    var low = 0
    var high = size
    while (low <= high){
        val mid = (low + high) / 2
        if ( this[mid].name < q){
            low = mid + 1
        }else if(this[mid].name > q){
            high = mid -1
        }else if (this[mid].name.equals(q, ignoreCase = true)
            || this[mid].name.lowercase()
                .startsWith(q.lowercase())){
            return this[mid].name
        }
    }
    return ""
}