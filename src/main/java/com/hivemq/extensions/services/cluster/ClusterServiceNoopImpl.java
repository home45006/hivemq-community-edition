/*
 * Copyright 2019-present HiveMQ GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hivemq.extensions.services.cluster;

import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.services.cluster.ClusterDiscoveryCallback;
import com.hivemq.extension.sdk.api.services.cluster.ClusterService;

import javax.inject.Inject;

/**
 * @author Silvio Giebl
 */
public class ClusterServiceNoopImpl implements ClusterService {

    private final @NotNull ClusterDiscovery clusterDiscovery;

    @Inject
    private ClusterServiceNoopImpl(ClusterDiscovery clusterDiscovery) {
        this.clusterDiscovery = clusterDiscovery;
    }

    @Override
    public void addDiscoveryCallback(final @NotNull ClusterDiscoveryCallback clusterDiscoveryCallback) {
//        throw new UnsupportedOperationException("Cluster discovery is not available in HiveMQ Community Edition");
        // 自动发现扩展
        clusterDiscovery.setClusterDiscoveryCallback(clusterDiscoveryCallback);
    }

    @Override
    public void removeDiscoveryCallback(final @NotNull ClusterDiscoveryCallback clusterDiscoveryCallback) {
//        throw new UnsupportedOperationException("Cluster discovery is not available in HiveMQ Community Edition");
        clusterDiscovery.setClusterDiscoveryCallback(null);
    }
}
