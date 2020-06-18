package com.velaphi.tabby.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.velaphi.tabby.R;
import com.velaphi.tabby.data.remote.ApiService;
import com.velaphi.tabby.data.database.AppDatabase;
import com.velaphi.tabby.data.database.TabbyEntry;
import com.velaphi.tabby.data.model.Image;
import com.velaphi.tabby.data.remote.RemoteServiceApiClient;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabbyRepositoryImpl implements TabbyRepository{

    ApiService apiService;
    AppDatabase appDatabase;
    Application application;

    public TabbyRepositoryImpl(AppDatabase appDatabase, Application application) {
        apiService = RemoteServiceApiClient.getInstance();
        this.appDatabase = appDatabase;
        this.application = application;
    }


    @Override
    public void loadFromApi() {
        apiService.getListOfImages().enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                final List<Image> body = response.body();
                if (response.isSuccessful() && body != null) {
                    if (appDatabase.tabbyDao().retrieveListOfAllImages().getValue() == null) {
                        Executor executor = Executors.newSingleThreadExecutor();
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                    appDatabase.tabbyDao().insertAllTabbyEntries(mapListOfTabbyEntriesFromResponse(body));
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {
                Log.d("Failed", t.getMessage());
            }
        });

    }

    @NotNull
    @Override
    public LiveData<List<TabbyEntry>> loadFromDB() {
        return appDatabase.tabbyDao().retrieveListOfAllImages();
    }

    private List<TabbyEntry> mapListOfTabbyEntriesFromResponse(List<Image> imageList) {

        List<TabbyEntry> tabbyEntryList = new ArrayList<>();
        for (Image image : imageList) {
            String title = getRandom(application.getApplicationContext().getResources().getStringArray(R.array.random_cat_names_array));
            String description = getDescription(title);
            tabbyEntryList.add(new TabbyEntry(image.getId(), title, description, image.getUrl()));
        }
        return tabbyEntryList;
    }

    private String getDescription(String title) {

        String master = getRandom(application.getApplicationContext().getResources().getStringArray(R.array.description_array));
        String target = "%1$s";
        return master.replace(target, title);
    }

    private String getRandom(String[] list){
        Random random = new Random();
        int index = random.nextInt((list.length - 0) - 1) + 0;
        return list[index];
    }

}
