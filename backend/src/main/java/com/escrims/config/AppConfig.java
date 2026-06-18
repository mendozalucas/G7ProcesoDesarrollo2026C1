package com.escrims.config;

import com.escrims.domain.command.ScrimCommandHistory;
import com.escrims.domain.moderation.AutoModerationHandler;
import com.escrims.domain.moderation.BotModerationHandler;
import com.escrims.domain.moderation.HumanModerationHandler;
import com.escrims.domain.moderation.ModerationHandler;
import com.escrims.domain.observer.DomainEventBus;
import com.escrims.domain.repository.ScrimRepository;
import com.escrims.domain.repository.UsuarioRepository;
import com.escrims.domain.services.MatchmakingService;
import com.escrims.domain.services.ModerationService;
import com.escrims.domain.services.ParticipationService;
import com.escrims.domain.services.ScrimLifecycleService;
import com.escrims.domain.services.SearchAlertService;
import com.escrims.domain.services.StatisticsService;
import com.escrims.domain.strategy.IMatchmakingStrategy;
import com.escrims.domain.repository.BusquedaFavoritaRepository;
import com.escrims.domain.repository.EstadisticaRepository;
import com.escrims.domain.repository.PostulacionRepository;
import com.escrims.domain.repository.ReporteConductaRepository;
import com.escrims.infrastructure.notifications.factory.DevNotifierFactory;
import com.escrims.infrastructure.notifications.factory.INotifierFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    DomainEventBus domainEventBus() {
        return new DomainEventBus();
    }

    @Bean
    ScrimCommandHistory scrimCommandHistory() {
        return new ScrimCommandHistory();
    }

    @Bean
    INotifierFactory notifierFactory() {
        return new DevNotifierFactory();
    }

    @Bean
    IMatchmakingStrategy defaultMatchmakingStrategy() {
        return candidatos -> candidatos;
    }

    @Bean
    MatchmakingService matchmakingService(IMatchmakingStrategy defaultMatchmakingStrategy) {
        return new MatchmakingService(defaultMatchmakingStrategy);
    }

    @Bean
    ParticipationService participationService(ScrimRepository scrimRepository,
                                               PostulacionRepository postulacionRepository,
                                               UsuarioRepository usuarioRepository,
                                               DomainEventBus eventBus,
                                               ScrimCommandHistory commandHistory) {
        return new ParticipationService(scrimRepository, postulacionRepository,
                usuarioRepository, eventBus, commandHistory);
    }

    @Bean
    ScrimLifecycleService scrimLifecycleService(DomainEventBus eventBus) {
        return new ScrimLifecycleService(eventBus);
    }

    @Bean
    StatisticsService statisticsService(EstadisticaRepository estadisticaRepository,
                                        ScrimRepository scrimRepository,
                                        UsuarioRepository usuarioRepository) {
        return new StatisticsService(estadisticaRepository, scrimRepository, usuarioRepository);
    }

    @Bean
    SearchAlertService searchAlertService(BusquedaFavoritaRepository busquedaRepository) {
        return new SearchAlertService(busquedaRepository);
    }

    @Bean
    ModerationHandler moderationHandlerChain(DomainEventBus eventBus) {
        AutoModerationHandler auto = new AutoModerationHandler();
        BotModerationHandler bot = new BotModerationHandler();
        HumanModerationHandler human = new HumanModerationHandler(eventBus);
        auto.setNext(bot).setNext(human);
        return auto;
    }

    @Bean
    ModerationService moderationService(ModerationHandler moderationHandlerChain,
                                         UsuarioRepository usuarioRepository) {
        return new ModerationService(moderationHandlerChain, usuarioRepository);
    }
}
