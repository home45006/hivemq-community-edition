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

    @Override
    public @NotNull ClusterNodeAddress getOwnAddress() {
        return null;
    }

    @Override
    public @NotNull String getOwnClusterId() {
        return null;
    }

    @Override
    public int getReloadInterval() {
        return 0;
    }
}
