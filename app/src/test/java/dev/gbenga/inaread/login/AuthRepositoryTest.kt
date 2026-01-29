package dev.gbenga.inaread.login

import dev.gbenga.inaread.data.auth.AccessToken
import dev.gbenga.inaread.data.auth.LoginRequest
import dev.gbenga.inaread.data.auth.LoginResponse
import dev.gbenga.inaread.data.auth.SignUpRequest
import dev.gbenga.inaread.data.auth.SignUpResponse
import dev.gbenga.inaread.data.db.UserDao
import dev.gbenga.inaread.data.db.entities.UserEntity
import dev.gbenga.inaread.data.model.ApiResult
import dev.gbenga.inaread.data.network.AuthenticationService
import dev.gbenga.inaread.data.repository.AuthRepositoryImpl
import dev.gbenga.inaread.domain.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class AuthRepositoryTest {

    private lateinit var authRepository: AuthRepository
    private val authApiService = mockk<AuthenticationService>()

    private val userDao = mockk<UserDao>()

    var testScheduler: TestCoroutineScheduler = TestCoroutineScheduler()

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
        authRepository = AuthRepositoryImpl(authApiService,
            userDao,
            StandardTestDispatcher(testScheduler))
    }

    @Test
    fun `test authentication success`() =  runTest {
        val loginRequest = LoginRequest(USERNAME, PASSWORD)
        coEvery { userDao.save(any()) } returns Unit
        coEvery { authApiService.authenticate(loginRequest) } returns
                ApiResult(data = LoginResponse(
                    USERNAME,
                    EMAIL,
                    "",1,
                    "",
                    false,
                    "",
                    "",
                    authToken = AccessToken(
                        access = "",
                        refresh = "",
                        0L
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
            data = SignUpResponse(
                message = SIGN_UP_SUCCESS_MSG
            )
        )

        val signUpResult = authRepository.signUp(signUpRequest)
        assertTrue(signUpResult.isSuccess)
        assertFalse(signUpResult.isError)
        assertEquals(SIGN_UP_SUCCESS_MSG, signUpResult.data?.message)
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
        assertEquals(INVALID_EMAIL_ERROR, signUpResult.error )
    }

    @Test
    fun `test local profile can be queried successfully`() = runTest{
        val userId = "ff22ddxx"
        coEvery { userDao.getProfile(userId) } returns listOf(
            UserEntity(
                userId,
                "dev01",
                "dev@gm.com",
                "1234543232",
                "NG",
                23,
                "15-Aug-2026",
                true,
                locked = false
            )
        )
        val profile = authRepository.getProfile(userId)
        assertTrue(profile.isSuccess)
        assertFalse(profile.isError)
        assertNotNull(profile.data)
        assertEquals("dev01", profile.data!!.username)
        assertEquals("dev@gm.com", profile.data.email)
        assertEquals("1234543232", profile.data.meterNo)
        assertEquals("NG", profile.data.countryId)
    }


    private val signUpRequest = SignUpRequest(USERNAME, PASSWORD, EMAIL)
}