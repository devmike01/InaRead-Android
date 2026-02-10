package dev.gbenga.inaread.utils.nav

import kotlinx.serialization.Serializable


sealed interface InaScreen {

    @Serializable
    object Dashboard : InaScreen

    @Serializable
    object Login: InaScreen

    @Serializable
    object SignUp: InaScreen

    @Serializable
    object ForgotPassword: InaScreen

    @Serializable
    object AllUnitUsage : InaScreen

    @Serializable
    object Waiting: InaScreen
}
