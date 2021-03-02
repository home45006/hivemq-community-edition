package com.hivemq.extensions.services.cluster;

import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.services.cluster.ClusterDiscoveryCallback;
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
 * @ClassName ClusterDiscoveryHandler
 * @Description: TODO
 * @Author: david
 * @create: 2021-03-02 15:48
 **/
public class ClusterDiscoveryHandler {

    private static final Logger log = LoggerFactory.getLogger(ClusterDiscoveryHandler.class);

    private final @NotNull PluginTaskExecutorService executorService;
    private final @NotNull PluginOutPutAsyncer asyncer;
    private final @NotNull HiveMQExtensions hiveMQExtensions;
    private final @NotNull ClusterDiscoveryCallback clusterDiscoveryCallback;

    @Inject
    private ClusterDiscoveryHandler(
            final @NotNull PluginTaskExecutorService executorService,
            final @NotNull PluginOutPutAsyncer asyncer,
            final @NotNull HiveMQExtensions hiveMQExtensions,
            final @NotNull ClusterDiscoveryCallback clusterDiscoveryCallback) {
        this.executorService = executorService;
        this.asyncer = asyncer;
        this.hiveMQExtensions = hiveMQExtensions;
        this.clusterDiscoveryCallback = clusterDiscoveryCallback;
    }

    public void registerClusterNode() {
        final HiveMQExtension extension =
                hiveMQExtensions.getExtensionForClassloader(executorService.getClass().getClassLoader());

        final ClusterDiscoveryInputImpl input = new ClusterDiscoveryInputImpl();
        final ExtensionParameterHolder<ClusterDiscoveryInputImpl> inputHolder = new ExtensionParameterHolder<>(input);

        final ClusterDiscoveryOutputImpl output = new ClusterDiscoveryOutputImpl(asyncer);
        final ExtensionParameterHolder<ClusterDiscoveryOutputImpl> outputHolder =
                new ExtensionParameterHolder<>(output);

        final ClusterDiscoveryCallbackContext context = new ClusterDiscoveryCallbackContext();

        final ClusterDiscoveryCallbackTask task =
                new ClusterDiscoveryCallbackTask(extension.getId(), clusterDiscoveryCallback);
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
        private final @NotNull ClusterDiscoveryCallback clusterDiscoveryCallback;

        private ClusterDiscoveryCallbackTask(
                final @NotNull String extensionId, final @NotNull ClusterDiscoveryCallback clusterDiscoveryCallback) {
            this.extensionId = extensionId;
            this.clusterDiscoveryCallback = clusterDiscoveryCallback;
        }

        @Override
        public @NotNull ClusterDiscoveryOutputImpl apply(
                final @NotNull ClusterDiscoveryInputImpl input, final @NotNull ClusterDiscoveryOutputImpl output) {

            try {
                clusterDiscoveryCallback.init(input, output);
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
            return clusterDiscoveryCallback.getClass().getClassLoader();
        }
    }
}
