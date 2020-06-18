package com.velaphi.tabby.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.velaphi.tabby.data.database.AppDatabase;
import com.velaphi.tabby.data.database.TabbyEntry;
import com.velaphi.tabby.data.repository.TabbyRepository;
import com.velaphi.tabby.data.repository.TabbyRepositoryImpl;

import java.util.List;

public class TabbyViewModel extends AndroidViewModel {

    private LiveData<List<TabbyEntry>> imageListLiveData;

    public TabbyViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        TabbyRepository tabbyRepository = new TabbyRepositoryImpl(appDatabase, application);
        imageListLiveData = tabbyRepository.loadFromDB();
        if (imageListLiveData.getValue() == null) {
            tabbyRepository.loadFromApi();
        }
    }

    public LiveData<List<TabbyEntry>> getImageListLiveData() {
        return imageListLiveData;
    }
}
