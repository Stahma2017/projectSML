package com.example.stas.sml.di.module;

import com.example.stas.sml.data.repository.LocationRepository;
import com.example.stas.sml.domain.gateway.LocationGateway;

import dagger.Binds;
import dagger.Module;

@Module
public interface GatewayModule {

    @Binds
    LocationGateway provideLocationGateway(LocationRepository locationRepository);

}
