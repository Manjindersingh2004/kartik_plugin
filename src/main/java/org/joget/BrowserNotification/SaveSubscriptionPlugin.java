package org.joget.BrowserNotification;

import org.joget.apps.app.service.AppUtil;
import org.joget.apps.form.dao.FormDataDao;
import org.joget.apps.form.service.FormUtil;
import org.joget.plugin.base.DefaultPlugin;
import org.joget.plugin.base.DefaultPluginWebSupport;
import org.joget.plugin.base.ExtDefaultPlugin;
import org.joget.plugin.base.PluginWebSupport;
import org.joget.workflow.util.WorkflowUtil;
import org.json.JSONObject;
import org.json.JSONException;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class SaveSubscriptionPlugin extends ExtDefaultPlugin implements PluginWebSupport {

    @Override
    public Object execute(Map props) {
        return null; // Not used
    }

    @Override
    public String getName() {
        return "Save Browser Push Subscription";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String getDescription() {
        return "Saves the push subscription object to Joget database";
    }

    @Override
    public void webService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response) {
        response.setContentType("application/json");
        try {
            StringBuilder jb = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;

            while ((line = reader.readLine()) != null)
                jb.append(line);

            JSONObject json = new JSONObject(jb.toString());

            String username = json.getString("username");
            JSONObject subscription = json.getJSONObject("subscription");

            String endpoint = subscription.getString("endpoint");
            JSONObject keys = subscription.getJSONObject("keys");
            String p256dh = keys.getString("p256dh");
            String auth = keys.getString("auth");

            // Save to Joget form (form ID: push_subscription)
            Map<String, String> data = new HashMap<>();
            data.put("id", username); // use username as ID
            data.put("username", username);
            data.put("endpoint", endpoint);
            data.put("p256dh", p256dh);
            data.put("auth", auth);

            FormDataDao formDataDao= (FormDataDao) AppUtil.getApplicationContext().getBean("formDataDao");
            formDataDao.saveOrUpdate("app_push", data); // replace app ID if needed

            response.getWriter().write("{\"status\":\"success\"}");

        } catch (JSONException e) {
            e.printStackTrace();
            try {
                response.getWriter().write("{\"status\":\"error\",\"message\":\"Invalid JSON\"}");
            } catch (Exception ex) {}
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.getWriter().write("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
            } catch (Exception ex) {}
        }
    }
}

