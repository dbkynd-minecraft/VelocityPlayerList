package com.dbkynd.velocityplayerlist;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.ServerPing;

@Plugin(
        id = "velocityplayerlist",
        name = "Velocity Player List",
        version = BuildConstants.VERSION,
        authors = {"DBKynd"}
)
public class Velocityplayerlist {
    ProxyServer server;

    @Inject
    public void VelocityInit(ProxyServer server) {
        this.server = server;
    }

    @Subscribe
    public void onPing(ProxyPingEvent event) {
        ServerPing response = event.getPing();
        ServerPing.SamplePlayer[] playerInfo = server.getAllPlayers().stream().map(player -> new ServerPing.SamplePlayer(player.getUsername(), player.getUniqueId())).toArray(ServerPing.SamplePlayer[]::new);
        ServerPing newResponse = response.asBuilder().samplePlayers(playerInfo).build();
        event.setPing(newResponse);
    }
}
