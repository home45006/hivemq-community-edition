package com.hivemq.extensions.services.cluster;

import com.hivemq.extension.sdk.api.annotations.Immutable;
import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.services.cluster.ClusterDiscoveryCallback;

/**
 * @ClassName Cluster
 * @Description: TODO
 * @Author: david
 * @create: 2021-03-01 20:19
 **/
public interface ClusterDiscovery {

    @Immutable
    @NotNull ClusterDiscoveryCallback getClusterDiscoveryCallback();

    void setClusterDiscoveryCallback(ClusterDiscoveryCallback clusterDiscoveryCallabck);
}
