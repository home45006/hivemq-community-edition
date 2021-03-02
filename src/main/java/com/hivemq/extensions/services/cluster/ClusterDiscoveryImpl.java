package com.hivemq.extensions.services.cluster;

import com.hivemq.extension.sdk.api.annotations.Immutable;
import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.services.cluster.ClusterDiscoveryCallback;

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

    @Override
    public @Immutable @NotNull ClusterDiscoveryCallback getClusterDiscoveryCallback() {
        return clusterDiscoveryCallback;
    }

    @Override
    public void setClusterDiscoveryCallback(ClusterDiscoveryCallback clusterDiscoveryCallback) {
        this.clusterDiscoveryCallback = clusterDiscoveryCallback;
    }
}
