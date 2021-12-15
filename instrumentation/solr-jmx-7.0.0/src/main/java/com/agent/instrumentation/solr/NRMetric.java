/*
 *
 *  * Copyright 2020 New Relic Corporation. All rights reserved.
 *  * SPDX-License-Identifier: Apache-2.0
 *
 */

package com.agent.instrumentation.solr;

import org.apache.solr.core.SolrInfoBean;

public abstract class NRMetric {

    protected static final String prefix = "JMX/solr/";

    public NRMetric(String r, String b) {
        registry = r;
        name = b;
    }

    protected String registry;

    protected String name;

    public String getRegistry() {
        return registry;
    }

    public abstract String getMetricName(String name);

    public abstract int reportMetrics();

    public abstract String getMetricBase();

}
