package com.escrims.config;

import com.escrims.domain.moderation.AutoModerationHandler;
import com.escrims.domain.moderation.BotModerationHandler;
import com.escrims.domain.moderation.HumanModerationHandler;
import com.escrims.domain.moderation.ModerationHandler;
import com.escrims.domain.moderation.Moderator;
import com.escrims.domain.observer.DomainEventBus;
import com.escrims.domain.repository.UsuarioRepository;
import com.escrims.domain.services.MatchmakingService;
import com.escrims.domain.services.ModerationService;
import com.escrims.domain.services.ScrimLifecycleService;
import com.escrims.domain.strategy.ByHistoryStrategy;
import com.escrims.domain.strategy.ByLatencyStrategy;
import com.escrims.domain.strategy.ByMMRStrategy;
import com.escrims.domain.strategy.IMatchmakingStrategy;
import com.escrims.infrastructure.notifications.factory.DevNotifierFactory;
import com.escrims.infrastructure.notifications.factory.INotifierFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MatchmakingProperties.class)
public class AppConfig {

    @Bean
    DomainEventBus domainEventBus() {
        return new DomainEventBus();
    }

    @Bean
    INotifierFactory notifierFactory() {
        return new DevNotifierFactory();
    }

    @Bean
    IMatchmakingStrategy matchmakingStrategy(MatchmakingProperties properties) {
        return switch (properties.getStrategy().toUpperCase()) {
            case "BY_LATENCY" -> new ByLatencyStrategy(properties.getLatenciaMaximaMs());
            case "BY_HISTORY" -> new ByHistoryStrategy(
                    properties.getPesoMmr(),
                    properties.getPesoLatencia(),
                    properties.getPesoHistorial());
            default -> new ByMMRStrategy(properties.getDiferenciaMmrMaxima());
        };
    }

    @Bean
    MatchmakingService matchmakingService(IMatchmakingStrategy matchmakingStrategy) {
        return new MatchmakingService(matchmakingStrategy);
    }

    @Bean
    ScrimLifecycleService scrimLifecycleService(DomainEventBus eventBus) {
        return new ScrimLifecycleService(eventBus);
    }

    @Bean
    Moderator moderator() {
        return new Moderator(1L, "Moderador", "mod@escrims.local");
    }

    @Bean
    ModerationHandler moderationHandlerChain(DomainEventBus eventBus, Moderator moderator) {
        AutoModerationHandler auto = new AutoModerationHandler();
        BotModerationHandler bot = new BotModerationHandler();
        HumanModerationHandler human = new HumanModerationHandler(eventBus, moderator);
        auto.setNext(bot).setNext(human);
        return auto;
    }

    @Bean
    ModerationService moderationService(ModerationHandler moderationHandlerChain,
                                         UsuarioRepository usuarioRepository) {
        return new ModerationService(moderationHandlerChain, usuarioRepository);
    }
}
