package ru.otus.crm.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionManager;
import ru.otus.crm.model.Client;

import java.util.Optional;

public class DbServiceClientWithCacheImpl extends DbServiceClientImpl {
    public DbServiceClientWithCacheImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate) {
        super(transactionManager, clientDataTemplate);
    }

    private final Cache<Long, Client> cache = CacheBuilder.newBuilder().softValues().build();

    @Override
    public Optional<Client> getClient(long id) {
        Client clientFromCache = cache.getIfPresent(id);
        if (clientFromCache != null) {
            return Optional.of(clientFromCache);
        } else {
            Optional<Client> clientOptional = super.getClient(id);
            clientOptional.ifPresent(client -> cache.put(id, client));
            return clientOptional;
        }
    }
}
