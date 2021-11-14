package com.dbkynd.playerlist;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.ServerPing;
import org.bstats.velocity.Metrics;

@Plugin(
        id = "playerlist",
        name = "Player List",
        version = BuildConstants.VERSION,
        authors = {"DBKynd"}
)
public class PlayerList {
    Metrics.Factory metricsFactory;
    ProxyServer server;

    @Inject
    public void VelocityInit(ProxyServer server, Metrics.Factory metricsFactory) {
        this.server = server;
        this.metricsFactory = metricsFactory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        metricsFactory.make(this, 13330);
    }

    @Subscribe
    public void onPing(ProxyPingEvent event) {
        ServerPing response = event.getPing();
        ServerPing.SamplePlayer[] playerInfo = server.getAllPlayers().stream().map(player -> new ServerPing.SamplePlayer(player.getUsername(), player.getUniqueId())).toArray(ServerPing.SamplePlayer[]::new);
        ServerPing newResponse = response.asBuilder().samplePlayers(playerInfo).build();
        event.setPing(newResponse);
    }
}
