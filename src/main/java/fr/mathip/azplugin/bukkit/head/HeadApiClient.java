package fr.mathip.azplugin.bukkit.head;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import fr.mathip.azplugin.bukkit.config.ConfigManager;

public class HeadApiClient {

    private static final int PER_PAGE = 20;

    public CompletableFuture<HeadApiResponse> fetchHeads(int page) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String apiUrl = ConfigManager.getInstance().getHeadApiUrl();
                String urlString = apiUrl + "/heads?page=" + page + "&per_page=" + PER_PAGE;

                HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.setRequestProperty("Accept", "application/json");

                int responseCode = connection.getResponseCode();
                if (responseCode != 200) {
                    throw new RuntimeException("HTTP " + responseCode);
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JSONObject json = (JSONObject) JSONValue.parse(response.toString());
                int total = ((Long) json.get("total")).intValue();
                int currentPage = ((Long) json.get("page")).intValue();
                int totalPages = ((Long) json.get("total_pages")).intValue();

                List<HeadData> heads = new ArrayList<>();
                JSONArray headsArray = (JSONArray) json.get("heads");
                for (Object obj : headsArray) {
                    JSONObject headObj = (JSONObject) obj;
                    String value = (String) headObj.get("value");
                    heads.add(new HeadData(value));
                }

                return new HeadApiResponse(total, currentPage, PER_PAGE, totalPages, heads);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });
    }
}
