package com.hivemq.extensions.services.cluster;

import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.services.cluster.parameter.ClusterNodeAddress;
import com.hivemq.extensions.HiveMQExtension;
import com.hivemq.extensions.HiveMQExtensions;
import com.hivemq.extensions.executor.PluginOutPutAsyncer;
import com.hivemq.extensions.executor.PluginTaskExecutorService;
import com.hivemq.extensions.executor.task.PluginInOutTask;
import com.hivemq.extensions.executor.task.PluginInOutTaskContext;
import com.hivemq.extensions.handler.ExtensionParameterHolder;
import com.hivemq.extensions.services.cluster.parameter.ClusterDiscoveryInputImpl;
import com.hivemq.extensions.services.cluster.parameter.ClusterDiscoveryOutputImpl;
import com.hivemq.util.Exceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * @ClassName ClusterDiscoveryService
 * @Description: TODO
 * @Author: david
 * @create: 2021-03-02 15:48
 **/
public class ClusterDiscoveryServiceImpl implements  ClusterDiscoveryService {

    private static final Logger log = LoggerFactory.getLogger(ClusterDiscoveryServiceImpl.class);

    private final @NotNull PluginTaskExecutorService executorService;
    private final @NotNull PluginOutPutAsyncer asyncer;
    private final @NotNull HiveMQExtensions hiveMQExtensions;
    private final @NotNull ClusterDiscovery clusterDiscovery;

    @Inject
    private ClusterDiscoveryServiceImpl(
            final @NotNull PluginTaskExecutorService executorService,
            final @NotNull PluginOutPutAsyncer asyncer,
            final @NotNull HiveMQExtensions hiveMQExtensions,
            final @NotNull ClusterDiscovery clusterDiscovery) {
        this.executorService = executorService;
        this.asyncer = asyncer;
        this.hiveMQExtensions = hiveMQExtensions;
        this.clusterDiscovery = clusterDiscovery;
    }

    @Override
    public void registerClusterNode() {
        final HiveMQExtension extension =
                hiveMQExtensions.getExtension("hivemq-etcd-cluster-discovery-extension");

        ClusterNodeAddress clusterNodeAddress = new ClusterNodeAddress("127.0.0.1", 9999);
        final ClusterDiscoveryInputImpl input = new ClusterDiscoveryInputImpl(clusterNodeAddress);
        final ExtensionParameterHolder<ClusterDiscoveryInputImpl> inputHolder = new ExtensionParameterHolder<>(input);

        final ClusterDiscoveryOutputImpl output = new ClusterDiscoveryOutputImpl(asyncer);
        final ExtensionParameterHolder<ClusterDiscoveryOutputImpl> outputHolder =
                new ExtensionParameterHolder<>(output);

        final ClusterDiscoveryCallbackContext context = new ClusterDiscoveryCallbackContext();

        final ClusterDiscoveryCallbackTask task =
                new ClusterDiscoveryCallbackTask(extension.getId(), clusterDiscovery);
        executorService.handlePluginInOutTaskExecution(context, inputHolder, outputHolder, task);
    }

    private class ClusterDiscoveryCallbackContext extends PluginInOutTaskContext<ClusterDiscoveryOutputImpl>
            implements Runnable {

        ClusterDiscoveryCallbackContext() {
            super("ClusterDiscoveryCallback");
        }

        @Override
        public void pluginPost(@NotNull ClusterDiscoveryOutputImpl pluginOutput) {

        }

        @Override
        public void run() {

        }
    }

    private static class ClusterDiscoveryCallbackTask
            implements PluginInOutTask<ClusterDiscoveryInputImpl, ClusterDiscoveryOutputImpl> {

        private final @NotNull String extensionId;
        private final @NotNull ClusterDiscovery clusterDiscovery;

        private ClusterDiscoveryCallbackTask(
                final @NotNull String extensionId, final @NotNull ClusterDiscovery clusterDiscovery) {
            this.extensionId = extensionId;
            this.clusterDiscovery = clusterDiscovery;
        }

        @Override
        public @NotNull ClusterDiscoveryOutputImpl apply(
                final @NotNull ClusterDiscoveryInputImpl input, final @NotNull ClusterDiscoveryOutputImpl output) {

            try {
                clusterDiscovery.getClusterDiscoveryCallback().init(input, output);
            } catch (final Throwable e) {
                log.warn("Uncaught exception was thrown from extension with id \"{}\" on inbound ClusterDiscovery. " + "Extensions are responsible for their own exception handling.",
                        extensionId);
                log.debug("Original exception:", e);
                Exceptions.rethrowError(e);
            }
            return output;
        }

        @Override
        public @NotNull ClassLoader getPluginClassLoader() {
            return clusterDiscovery.getClass().getClassLoader();
        }
    }
}
