package com.readnumber.dailyonestep.common.configuration

import com.readnumber.dailyonestep.security.common.AuthenticationFacade
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.*

@Configuration
@EnableJpaAuditing
class JpaAuditorAwareConfiguration(
    private val authenticationFacade: AuthenticationFacade
) : AuditorAware<Long> {
    override fun getCurrentAuditor(): Optional<Long> {
        return Optional.ofNullable(authenticationFacade.getPrincipal().getId())
    }
}