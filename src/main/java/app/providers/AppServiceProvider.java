package app.providers;

import app.repositories.QuotesRepositoryInterface;
import app.repositories.QuotesRepository;
import framework.providers.Provider;
import framework.inject.DependencyInjectionContainer;

public class AppServiceProvider extends Provider {

    @Override
    public void register(DependencyInjectionContainer dependencyInjectionContainer) {
        dependencyInjectionContainer.bind(QuotesRepositoryInterface.class, QuotesRepository.class);
    }
}
