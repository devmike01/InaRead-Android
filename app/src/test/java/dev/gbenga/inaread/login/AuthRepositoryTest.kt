package dev.gbenga.inaread.login

import dev.gbenga.inaread.data.auth.AccessToken
import dev.gbenga.inaread.data.auth.LoginRequest
import dev.gbenga.inaread.data.auth.LoginResponse
import dev.gbenga.inaread.data.auth.SignUpRequest
import dev.gbenga.inaread.data.model.ApiResult
import dev.gbenga.inaread.data.repository.AuthRepositoryImpl
import dev.gbenga.inaread.domain.repository.AuthRepository
import dev.gbenga.inaread.domain.services.AuthApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import javax.inject.Inject
import kotlin.math.sign


class AuthRepositoryTest {

    private lateinit var authRepository: AuthRepository
    private val authApiService = mockk<AuthApiService>()

    @Inject
    private lateinit var testScheduler: TestCoroutineScheduler

    companion object{
        private const val USERNAME = "dev01"
        private const val PASSWORD = "12fG#DDSf"
        private const val EMAIL = "dev01@gm.com"
        private const val AUTH_ERROR_MSG = "Invalid password and username"
        private const val SIGN_UP_SUCCESS_MSG = "Sign up was successful"
        private const val INVALID_EMAIL_ERROR = "Email is not valid"
    }


    @Before
    fun init(){
        authRepository = AuthRepositoryImpl(authApiService, StandardTestDispatcher(testScheduler))
    }

    @Test
    fun `test authentication success`() =  runTest {
        val loginRequest = LoginRequest(USERNAME, PASSWORD)
        coEvery { authApiService.authenticate(loginRequest) } returns
                ApiResult(data = LoginResponse(
                    USERNAME,
                    EMAIL, access = AccessToken(
                        access = "",
                        refresh = ""
                    )))
        val result = authRepository.authenticate(loginRequest)
        assertTrue(result.isSuccess)
        assertFalse(result.isError)
        val (username, email) = requireNotNull(result.data)
        assertEquals(USERNAME, username)
        assertEquals(EMAIL, email)
        coVerify(exactly = 1) { authApiService.authenticate(loginRequest) }
    }


    @Test
    fun `test authentication error`() = runTest{
        val loginRequest = LoginRequest(USERNAME, PASSWORD)
        coEvery { authApiService.authenticate(loginRequest) } returns ApiResult(error = AUTH_ERROR_MSG)
        val result = authRepository.authenticate(loginRequest)
        assertFalse(result.isSuccess)
        assertTrue(result.isError)
        assertEquals(AUTH_ERROR_MSG, result.error)
        coVerify(exactly = 1) { authApiService.authenticate(loginRequest) }
    }

    @Test
    fun `test sign up success`() = runTest {

        coEvery { authApiService.signUp(signUpRequest) } returns ApiResult(
            data = SIGN_UP_SUCCESS_MSG
        )

        val signUpResult = authRepository.signUp(signUpRequest)
        assertTrue(signUpResult.isSuccess)
        assertFalse(signUpResult.isError)
        assertEquals(SIGN_UP_SUCCESS_MSG, signUpResult.data)
        coVerify(exactly = 1) { authApiService.signUp(signUpRequest) }
    }

    @Test
    fun `test sign up error`() = runTest {
        coEvery { authApiService.signUp(signUpRequest) } returns ApiResult(
            error = INVALID_EMAIL_ERROR
        )
        val signUpResult = authRepository.signUp(signUpRequest)
        assertTrue(signUpResult.isError)
        assertFalse(signUpResult.isSuccess)
        assertEquals(SIGN_UP_SUCCESS_MSG, signUpResult.data )
    }

    fun `get user profile`(){

    }

    private val signUpRequest = SignUpRequest(USERNAME, PASSWORD, EMAIL)
}