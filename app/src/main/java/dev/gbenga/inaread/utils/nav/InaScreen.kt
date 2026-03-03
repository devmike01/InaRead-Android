package dev.gbenga.inaread.utils.nav

import dev.gbenga.inaread.data.model.MonthlyUsage
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

    @Serializable
    data class AllUnitUsageDetailsScreen(val monthlyUsage: MonthlyUsage): InaScreen
}
