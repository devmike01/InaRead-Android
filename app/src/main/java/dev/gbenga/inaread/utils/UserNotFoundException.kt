package dev.gbenga.inaread.utils

import dev.gbenga.inaread.tokens.StringTokens

class UserNotFoundException : Exception(StringTokens.UserNotAuthenticated)