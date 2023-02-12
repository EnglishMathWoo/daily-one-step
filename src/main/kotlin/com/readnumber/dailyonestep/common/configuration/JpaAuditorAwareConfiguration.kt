package com.readnumber.dailyonestep.common.configuration

import com.readnumber.dailyonestep.security.common.AuthenticationFacade
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.*

@Configuration
@EnableJpaAuditing
class JpaAuditorAwareConfiguration(
    private val authenticationFacade: AuthenticationFacade,
    // private val auditorService : auditorService,
) : AuditorAware<Long> {
    override fun getCurrentAuditor(): Optional<Long> {

        // TODO auditor id 를 db 에서 가져오도록 변경
        // val type = authenticationFacade.getPrincipal().getType()
        // val id = authenticationFacade.getPrincipal().getId()
        // val auditorId = auditorService.getId(type, id)
        // return Optional.of(auditorId)
        return Optional.ofNullable(authenticationFacade.getPrincipal().getAuthorId())
    }
}