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
package org.apache.sling.jcr.resource;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;

/**
 * The <code>SyntheticResource</code> class is a simple implementation of the
 * <code>Resource</code> interface which may be used to provide a resource
 * object which is related to a repository item.
 */
public class SyntheticResource implements Resource {

    /** The path of the synthetic resource */
    private String path;

    /** The type this synthetic resource assumes */
    private String resourceType;

    /** The metadat of this resource just containig the resource path */
    private ResourceMetadata resourceMetadata;

    /**
     * Creates a synthetic content with the given path and component Id.
     *
     * @param path The path of the synthetic content
     * @param resourceType The ID of the component rendering the synthetic
     *            content
     */
    public SyntheticResource(String path, String resourceType) {
        this.path = path;
        this.resourceType = resourceType;
        this.resourceMetadata = new ResourceMetadata();
        this.resourceMetadata.put(ResourceMetadata.RESOLUTION_PATH, path);
    }

    public String getURI() {
        return path;
    }

    public String getResourceType() {
        return resourceType;
    }

    public ResourceMetadata getResourceMetadata() {
        return resourceMetadata;
    }

    public Object getRawData() {
        return null;
    }

    public Object getObject() {
        return null;
    }

}
