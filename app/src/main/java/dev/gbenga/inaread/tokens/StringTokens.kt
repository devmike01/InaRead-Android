package dev.gbenga.inaread.tokens

object StringTokens {

    const val DailyActivity = "Daily Activity"
    const val Hours = "Last Usage"
    const val Days = "Days"
    const val Cost = "Cost"
    const val SelectADate = "Select a Date"
    const val MeterSummary = "Summary of Usage"
    const val MonthlyUsageSummary = "Summary of Monthly Usage"
    const val UnknownErrorOccured = "An unknown message has occurred"
    const val UserNotAuthenticated = "User not found"
    const val AddMeterReading = "Add meter image"

    object AddReadingImage{
        const val CancelImageDescription = "Cancel image upload"
        const val Title = "Add Meter Image for Analysis"
        const val Subtitle = "Upload a meter photo to extract and analyze the meter readings"
        const val ReadMeterImage = "Read Meter Image"
        const val ViewAllTimeUsage = "View All"
    }

    object Greetings{
        const val GoodMorning = "morning \uD83C\uDF25\uFE0F!"
        const val GoodAfternoon = "afternoon \uD83C\uDF1E!"
        const val GoodEvening = "evening \uD83C\uDF19!"
    }

    object YearPicker {
        const val ArrowBack = "Go back to previous year"
        const val ArrowForward = "Go to next year"
    }

    object SignUp{
        const val GoBack = "Go back"
    }

    object Auth{
        const val UsernamePlaceholder = "Enter username"
        const val PasswordPlaceholder = "Enter password"
        const val ConfirmPasswordPlaceholder = "Confirm password"
        const val EmailPlaceholder = "Enter email"
        const val LoginTitle = "Log in with your Username"
        const val Login = "Log in"
        const val Email = "Email"
        const val ForgotPassword = "Forgot password"
        const val DontHaveAccount = "Don't have an account? "
        const val SignUp = "Sign Up"
        const val SignUpTitle = "Sign Up Now to Get Started"
        const val SignUpDescription = "Track your electricity usage and spending to better optimise and reduce your cost"
        const val LoginDescription = "Track your electricity usage and spending to better optimise and reduce your cost"
        const val AlreadyHaveAccount = "Already have an account? "
        const val NoSuchData = "Data is not available"
        const val NoProfileWithUserId = "No user with provided ID"
    }
}