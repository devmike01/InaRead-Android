package dev.gbenga.inaread.login

import dev.gbenga.inaread.data.auth.AccessToken
import dev.gbenga.inaread.data.auth.LoginRequest
import dev.gbenga.inaread.data.auth.LoginResponse
import dev.gbenga.inaread.data.auth.SignUpRequest
import dev.gbenga.inaread.data.auth.SignUpResponse
import dev.gbenga.inaread.data.db.UserDao
import dev.gbenga.inaread.data.db.entities.UserEntity
import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.mapper.toLoginInput
import dev.gbenga.inaread.data.model.ApiResult
import dev.gbenga.inaread.domain.services.AuthenticationApiService
import dev.gbenga.inaread.data.repository.AuthRepositoryImpl
import dev.gbenga.inaread.domain.repository.AuthRepository
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


class AuthRepositoryTest {

    private lateinit var authRepository: AuthRepository
    private val authApiService = mockk<AuthenticationApiService>()

    private val userDao = mockk<UserDao>()

    var testScheduler: TestCoroutineScheduler = TestCoroutineScheduler()

    val dispatcher = StandardTestDispatcher(testScheduler)

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
            dispatcher
            )
    }

    @Test
    fun `test authentication success`() =  runTest(dispatcher) {
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
        val result = authRepository.authenticate(loginRequest.toLoginInput())
        assertTrue(result is RepoResult.Success)
        assertFalse(result is RepoResult.Error)
        val (username, email) = requireNotNull(result.extractData())
        assertEquals(USERNAME, username)
        assertEquals(EMAIL, email)
        coVerify(exactly = 1) { authApiService.authenticate(loginRequest) }
        coVerify(exactly = 1) { userDao.save(any()) }
    }

    fun <T> RepoResult<T>.extractData(): T{
        return if(this is RepoResult.Success) {
            this.data
        }else throw IllegalArgumentException("result is not success")
    }

    fun <T> RepoResult<T>.extractErrorMessage(): String{
        return if (this is RepoResult.Error){
            this.message
        }else throw IllegalStateException("Result is not error")
    }


    @Test
    fun `test authentication error`() = runTest(dispatcher){
        val loginRequest = LoginRequest(USERNAME, PASSWORD)
        coEvery { authApiService.authenticate(loginRequest) } returns ApiResult(error = AUTH_ERROR_MSG)
        val result = authRepository.authenticate(loginRequest.toLoginInput())
        assertTrue(result is RepoResult.Error)
        assertFalse(result is RepoResult.Success)
        assertEquals(AUTH_ERROR_MSG, result.extractErrorMessage())
        coVerify(exactly = 1) { authApiService.authenticate(loginRequest) }
    }

    @Test
    fun `test sign up success`() = runTest(dispatcher) {

        coEvery { authApiService.signUp(signUpRequest) } returns ApiResult(
            data = SignUpResponse(
                message = SIGN_UP_SUCCESS_MSG
            )
        )

        val signUpResult = authRepository.signUp(signUpRequest)
        assertTrue(signUpResult is RepoResult.Success)
        assertFalse(signUpResult is RepoResult.Error)
        assertEquals(SIGN_UP_SUCCESS_MSG, signUpResult.extractData().message)
        coVerify(exactly = 1) { authApiService.signUp(signUpRequest) }
    }

    @Test
    fun `test sign up error`() = runTest(dispatcher) {
        coEvery { authApiService.signUp(signUpRequest) } returns ApiResult(
            error = INVALID_EMAIL_ERROR
        )
        val signUpResult = authRepository.signUp(signUpRequest)
        assertTrue(signUpResult is RepoResult.Error)
        assertFalse(signUpResult is RepoResult.Success)
        assertEquals(INVALID_EMAIL_ERROR, signUpResult.extractErrorMessage() )
        coVerify(exactly = 1) { authApiService.signUp(signUpRequest) }
    }

    @Test
    fun `test local profile can be queried successfully`() = runTest(dispatcher){
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

        assertTrue(profile is RepoResult.Success)
        assertFalse(profile is RepoResult.Error)
        val extractedData = profile.extractData()
        assertEquals("dev01", extractedData.username)
        assertEquals("dev@gm.com", extractedData.email)
        assertEquals("1234543232", extractedData.meterNo)
        assertEquals("NG", extractedData.countryId)
    }


    private val signUpRequest = SignUpRequest(
        USERNAME,
        PASSWORD,
        "prepaid",
        "ng",
        "w22121",
        EMAIL, 1)
}