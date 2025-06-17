package org.joget.BrowserNotification;

import org.joget.plugin.base.DefaultPlugin;
import org.joget.plugin.base.PluginProperty;

import java.util.Map;

public class BrowserPushPlugin extends DefaultPlugin {
    @Override
    public String getName() {
        return "Browser Push Plugin";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String getDescription() {
        return "Registers a browser push service worker";
    }

    @Override
    public PluginProperty[] getPluginProperties() {
        return new PluginProperty[0];
    }

    @Override
    public Object execute(Map map) {
        return null;
    }

}

