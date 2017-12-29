package io.github.zeleven.lemon.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class GitHub {
    public static final String CREATED = "created";
    public static final String UPDATED = "updated";
    public static final String PUSHED = "pushed";
    public static final String FULL_NAME = "full_name";

    public static List<GitHubRepository> getRepositories(String sort) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.github.com/users/zeleven/repos";
        if (sort != null) {
            url = url + "?sort=" + sort;
        }
        Request request = new Request.Builder().url(url).build();
        List<GitHubRepository> repositories = null;
        try {
            Response response = client.newCall(request).execute();
            Gson gson = new Gson();
            Type type = new TypeToken<List<GitHubRepository>>(){}.getType();
            repositories = gson.fromJson(response.body().string(), type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return repositories;
    }
}
