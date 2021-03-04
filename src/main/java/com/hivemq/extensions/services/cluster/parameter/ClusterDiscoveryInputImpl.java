package com.hivemq.extensions.services.cluster.parameter;

import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.services.cluster.parameter.ClusterDiscoveryInput;
import com.hivemq.extension.sdk.api.services.cluster.parameter.ClusterNodeAddress;
import com.hivemq.extensions.executor.task.PluginTaskInput;

/**
 * @ClassName ClusterDiscoveryInputImpl
 * @Description: TODO
 * @Author: david
 * @create: 2021-03-02 15:50
 **/
public class ClusterDiscoveryInputImpl implements ClusterDiscoveryInput, PluginTaskInput {

    private ClusterNodeAddress ownAddress;
    private String clusterId;

    public ClusterDiscoveryInputImpl(ClusterNodeAddress clusterNodeAddress) {
        ownAddress = clusterNodeAddress;
        clusterId = "cluster-test";
    }

    @Override
    public @NotNull ClusterNodeAddress getOwnAddress() {
        return ownAddress;
    }

    @Override
    public @NotNull String getOwnClusterId() {
        return clusterId;
    }

    @Override
    public int getReloadInterval() {
        return 0;
    }
}
