package com.dax.walker;

import com.allatori.annotations.DoNotRename;
import com.dax.walker.models.*;
import com.dax.walker.models.exceptions.AuthorizationException;
import com.dax.walker.models.exceptions.RateLimitException;
import com.dax.walker.models.exceptions.UnknownException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;
import org.rspeer.RSPeer;
import org.rspeer.script.Script;
import org.rspeer.ui.Log;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

@DoNotRename
public class Server {

    private static final String BASE_URL = "https://api.dax.cloud/walker";

    private Gson gson;
    private String key;
    private String secret;
    private long rateLimit;
    private OkHttpClient okHttpClient;

    public Server(String key, String secret) {
        this.gson = new Gson();
        this.key = key;
        this.secret = secret;
        this.rateLimit = 0L;
        this.okHttpClient = new OkHttpClient();
    }

    public List<PathResult> getPaths(BulkPathRequest bulkPathRequest) {
        return makePathRequest(BASE_URL + "/generatePaths", gson.toJson(bulkPathRequest));
    }

    public List<PathResult> getBankPaths(BulkBankPathRequest bulkBankPathRequest) {
        return makePathRequest(BASE_URL + "/generateBankPaths", gson.toJson(bulkBankPathRequest));
    }

    private List<PathResult> makePathRequest(String url, String jsonPayload) {
        if (System.currentTimeMillis() - rateLimit < 5000L) throw new RateLimitException("Throttling requests because key rate limit.");

        Request request = generateRequest(url, RequestBody.create(MediaType.parse("application/json"), jsonPayload));
        long start = System.currentTimeMillis();
        try {
            Response response = okHttpClient.newCall(request).execute();
            switch (response.code()) {
                case 429:
                    Log.log(Level.WARNING, "Server", "Rate limit hit");
                    this.rateLimit = System.currentTimeMillis();
                    throw new RateLimitException(response.message());

                case 401:
                    throw new AuthorizationException(String.format("Invalid API Key [%s]", response.message()));

                case 200:
                    ResponseBody responseBody = response.body();
                    if (responseBody == null) throw new IllegalStateException("Illegal response returned from server.");
                    Log.info("DaxWalker", String.format("Generated path in %dms", System.currentTimeMillis() - start));
                    return gson.fromJson(responseBody.string(), new TypeToken<List<PathResult>>() {}.getType());
            }
        } catch (IOException e) {
            Log.log(Level.SEVERE, "Server", e.toString());
        }
        throw new UnknownException("Error connecting to server.");
    }

    private Request generateRequest(String url, RequestBody body) {
        return new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("key", key)
                .addHeader("secret", secret)
                .addHeader("Content-Type", "application/json")
                .addHeader("User-Agent", "RSPeer: " + ((Script)Thread.currentThread()).getMeta().name())
                .build();
    }

}
