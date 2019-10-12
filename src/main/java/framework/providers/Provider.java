package framework.providers;

import framework.inject.DependencyInjectionContainer;

public abstract class Provider {
    public abstract void register(DependencyInjectionContainer dependencyInjectionContainer);
}
