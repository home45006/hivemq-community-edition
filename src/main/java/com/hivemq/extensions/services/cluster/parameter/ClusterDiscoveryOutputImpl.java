package com.hivemq.extensions.services.cluster.parameter;

import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.interceptor.publish.parameter.PublishInboundOutput;
import com.hivemq.extension.sdk.api.services.cluster.parameter.ClusterDiscoveryOutput;
import com.hivemq.extension.sdk.api.services.cluster.parameter.ClusterNodeAddress;
import com.hivemq.extensions.executor.PluginOutPutAsyncer;
import com.hivemq.extensions.executor.task.AbstractAsyncOutput;

import java.util.List;

/**
 * @ClassName ClusterDiscoveryOutputImpl
 * @Description: TODO
 * @Author: david
 * @create: 2021-03-02 15:52
 **/
public class ClusterDiscoveryOutputImpl extends AbstractAsyncOutput<ClusterDiscoveryOutput> implements ClusterDiscoveryOutput {

    public ClusterDiscoveryOutputImpl(@NotNull PluginOutPutAsyncer asyncer) {
        super(asyncer);
    }

    @Override
    public void provideCurrentNodes(@NotNull List<ClusterNodeAddress> list) {

    }

    @Override
    public void setReloadInterval(int i) {

    }
}
