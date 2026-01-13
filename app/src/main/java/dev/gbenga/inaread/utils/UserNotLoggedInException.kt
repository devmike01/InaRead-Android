package dev.gbenga.inaread.utils

import dev.gbenga.inaread.tokens.StringTokens

class UserNotLoggedInException : Exception(StringTokens.UserNotAuthenticated)