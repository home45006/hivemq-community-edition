package com.hivemq.extensions.services.cluster;

import com.hivemq.extension.sdk.api.annotations.Immutable;
import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.services.cluster.ClusterDiscoveryCallback;
import com.hivemq.extensions.HiveMQExtensions;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @ClassName ClusterImpl
 * @Description: TODO
 * @Author: david
 * @create: 2021-03-01 20:24
 **/
@Singleton
public class ClusterDiscoveryImpl implements ClusterDiscovery {

    private ClusterDiscoveryCallback clusterDiscoveryCallback;

    @NotNull
    private final HiveMQExtensions hiveMQExtensions;

    @Inject
    public ClusterDiscoveryImpl(HiveMQExtensions hiveMQExtensions) {
        this.hiveMQExtensions = hiveMQExtensions;
    }

    @Override
    public @Immutable @NotNull ClusterDiscoveryCallback getClusterDiscoveryCallback() {
        return clusterDiscoveryCallback;
    }

    @Override
    public void setClusterDiscoveryCallback(ClusterDiscoveryCallback clusterDiscoveryCallback) {
        this.clusterDiscoveryCallback = clusterDiscoveryCallback;
    }
}
