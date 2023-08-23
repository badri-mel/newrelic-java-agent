package org.springframework.cache;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import org.springframework.cache.Cache;

import java.util.logging.Level;

@Weave(type = MatchType.Interface, originalName = "org.springframework.cache.Cache")
public abstract class Cache_Instrumentation {
    public Cache.ValueWrapper get(Object key) {
        Cache.ValueWrapper result = Weaver.callOriginal();
        NewRelic.incrementCounter("Spring/Cache/" + getProviderClassName() + "/" + getName() + (result == null ? "/Misses" : "/Hits"));
        return result;
    }

    public void evict(Object key) {
        Weaver.callOriginal();
        NewRelic.incrementCounter("Spring/Cache/" + getProviderClassName() + "/" + getName() + "/Evict");
    }

    public void clear() {
        Weaver.callOriginal();
        NewRelic.incrementCounter("Spring/Cache/" + getProviderClassName() + "/" + getName() + "/Clear");
    }

    public abstract Object getNativeCache();

    public abstract String getName();

    private String getProviderClassName() {
        Object provider = getNativeCache();
        if (provider != null) {
            return provider.getClass().getName();
        } else {
            return "Unknown";
        }
    }
}
