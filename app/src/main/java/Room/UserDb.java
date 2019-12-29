package Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import Model.User;

@Database(entities = {User.class},version = 2,exportSchema = false)
public abstract class UserDb extends RoomDatabase {

  public abstract UserDao userDao();

    private static UserDb instance;

    public static synchronized UserDb getInstance(Context context){

        if(instance == null){

            instance = Room.databaseBuilder(context.getApplicationContext(),UserDb.class,"User_db")
                       .fallbackToDestructiveMigration().build();
        }

        return instance;

    }

}
