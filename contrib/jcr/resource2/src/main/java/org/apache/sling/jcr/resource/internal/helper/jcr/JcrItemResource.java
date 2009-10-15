/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.sling.jcr.resource.internal.helper.jcr;

import java.util.Iterator;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.adapter.SlingAdaptable;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.jcr.resource.JcrResourceConstants;
import org.apache.sling.jcr.resource.JcrResourceTypeProvider;

abstract class JcrItemResource extends SlingAdaptable implements Resource {

    private final ResourceResolver resourceResolver;

    private final String path;

    private final ResourceMetadata metadata;

    protected final JcrResourceTypeProvider[] resourceTypeProviders;

    protected JcrItemResource(ResourceResolver resourceResolver,
                              String path,
                              JcrResourceTypeProvider[] resourceTypeProviders) {

        this.resourceResolver = resourceResolver;
        this.path = path;
        this.resourceTypeProviders = resourceTypeProviders;

        metadata = new ResourceMetadata();
        metadata.setResolutionPath(path);
    }

    public ResourceResolver getResourceResolver() {
        return resourceResolver;
    }

    public String getPath() {
        return path;
    }

    public ResourceMetadata getResourceMetadata() {
        return metadata;
    }

    /**
     * Compute the resource type of the given node, using either the
     * SLING_RESOURCE_TYPE_PROPERTY, or the node's primary node type, if the
     * property is not set
     */
    protected String getResourceTypeForNode(Node node)
            throws RepositoryException {
        String result = null;

        if (node.hasProperty(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY)) {
            result = node.getProperty(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY).getValue().getString();
        }

        if (result == null && this.resourceTypeProviders != null) {
            int index = 0;
            while ( result == null && index < this.resourceTypeProviders.length ) {
                result = this.resourceTypeProviders[index].getResourceTypeForNode(node);
                index++;
            }
        }

        if (result == null || result.length() == 0) {
            result = node.getPrimaryNodeType().getName();
        }

        return result;
    }

    /**
     * Returns an iterator over the child resources or <code>null</code> if
     * there are none.
     */
    abstract Iterator<Resource> listChildren();

}
