package Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Users")
public class User {

   // @PrimaryKey(autoGenerate = true)

    @NonNull
    @PrimaryKey
    @SerializedName("_id")
    private String _id;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    private String name;

    @ColumnInfo(name = "age")
    @SerializedName("age")
    @Expose
    private String age;

    public User(){}

    public User(@NonNull String _id,String name, String age) {
        this._id = _id;
        this.name = name;
        this.age = age;
    }

    @NonNull
    public String get_id() {
        return _id;
    }

    public void set_id(@NonNull String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
