package Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import Model.User;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Insert(User... users);

    @Query("SELECT * FROM Users")
    LiveData<List<User>> getRoomUsers();

    @Delete
    void Delete(User... user);

    @Query("SELECT * FROM Users")
    LiveData<List<User>> isDbEmpty();
}
