package ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import Model.User;
import Room.UserRepository;

public class UserViewModel extends AndroidViewModel {

    private UserRepository repo;
    private LiveData<List<User>> listLiveData;
    private LiveData<List<User>> checkDb;

    public UserViewModel(@NonNull Application application) {
        super(application);

        repo = new UserRepository(application);
        listLiveData = repo.getRoomUsers();
        checkDb = repo.isDbEmpty();
    }

    public LiveData<List<User>> getListLiveData() {
        return listLiveData;
    }

    public void deleteItem(User... user){
        repo.deleteItem(user);
    }

    public LiveData<List<User>> getCheckDb() {
        return checkDb;
    }
}
