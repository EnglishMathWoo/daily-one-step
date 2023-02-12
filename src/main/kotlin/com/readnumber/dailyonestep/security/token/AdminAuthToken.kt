package com.readnumber.dailyonestep.security.token

import mu.KotlinLogging
import com.readnumber.dailyonestep.security.common.AuthToken
import io.jsonwebtoken.*

class AdminAuthToken(
    private val parser: JwtParser,
    private val token: String
) : AuthToken<Claims> {

    private val logger = KotlinLogging.logger {}

    override fun getValue(): Claims {
        return parser.parseClaimsJws(token).body
    }

    override fun isValid(): Boolean {
        try {
            parser.parseClaimsJws(token)
            return true
        } catch (e: MalformedJwtException) {
            logger.info("Invalid JWT signature trace: {}", e)
        } catch (e: ExpiredJwtException) {
            logger.info("Expired JWT token trace: {}", e)
        } catch (e: UnsupportedJwtException) {
            logger.info("Unsupported JWT token trace: {}", e)
        } catch (e: IllegalArgumentException) {
            logger.info("JWT token compact of handler are invalid trace: {}", e)
        }
        return false
    }
}