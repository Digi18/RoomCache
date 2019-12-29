package Room;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.app.roomcache.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Model.User;
import Remote.ApiService;
import Remote.RetrofitClient;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.internal.subscribers.InnerQueuedSubscriberSupport;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserRepository {

    private Context context;
    private UserDb userDb;
    private LiveData<List<User>> listLiveData;
    private LiveData<List<User>> checkDb;
    private UserDao userDao;


    public UserRepository(Context context) {
        this.context = context;
        userDb = UserDb.getInstance(context);
        userDao = userDb.userDao();
        listLiveData = userDao.getRoomUsers();
        checkDb = userDao.isDbEmpty();
    }

    public LiveData<List<User>> isDbEmpty(){
        return checkDb;
    }

    public void getUserList(){

              Retrofit retrofit = RetrofitClient.getInstance();
              ApiService apiService = retrofit.create(ApiService.class);

              Call<List<User>> userList = apiService.getUser();

              userList.enqueue(new Callback<List<User>>() {
                  @Override
                  public void onResponse(Call<List<User>> call, final Response<List<User>> response) {

                      Completable.fromAction(new Action() {
                              @Override
                              public void run() throws Exception {

                                  if(response.body() != null) {

                                      List<User> list = response.body();

                                      for (int i = 0; i < list.size(); i++) {

                                          String id = list.get(i).get_id();
                                          String names = list.get(i).getName();
                                          String age = list.get(i).getAge();

                                          User user = new User(id,names,age);

                                          userDb.userDao().Insert(user);

                                      }
                                  }
                              }
                          }).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new CompletableObserver() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onComplete() {

                                    Toast.makeText(context,"Data inserted",Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(Throwable e) {

                                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            });

                  }

                  @Override
                  public void onFailure(Call<List<User>> call, Throwable t) {
                      Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
                  }
              });

    }

    public LiveData<List<User>> getRoomUsers(){

        return listLiveData;
    }

    public void deleteItem(User... user){

//        userDb.userDao().Delete(user);

        new deleteItemAsynctask(userDao).execute(user);
    }

    public void addRemoteData(String str1,String str2){

        Retrofit retrofit = RetrofitClient.getInstance();
        ApiService apiService = retrofit.create(ApiService.class);

        apiService.saveRemoteData(str1,str2).subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Observer<String>() {
                                                @Override
                                                public void onSubscribe(Disposable d) {

                                                }

                                                @Override
                                                public void onNext(String s) {

                                                    if(s.equals("Successful")){

                                                        Intent i = new Intent(context, MainActivity.class);
                                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        context.startActivity(i);
                                                    }
                                                }

                                                @Override
                                                public void onError(Throwable e) {

                                                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();

                                                }

                                                @Override
                                                public void onComplete() {

                                                }
                                            });

                             }

    public void deleteUser(String str){

        Retrofit retrofit = RetrofitClient.getInstance();
        ApiService apiService = retrofit.create(ApiService.class);

        apiService.deleteRemoteUser(str).subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Observer<String>() {
                      @Override
                      public void onSubscribe(Disposable d) {

                      }

                      @Override
                      public void onNext(String s) {

                          if(s.equals("Deleted")){

                              Toast.makeText(context,"Item deleted",Toast.LENGTH_SHORT).show();
                          }
                      }

                      @Override
                      public void onError(Throwable e) {

                              Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                      }

                      @Override
                      public void onComplete() {

                      }
                  });
    }

    public static class deleteItemAsynctask extends AsyncTask<User,Void,Void>{

        private UserDao userDao;

        public deleteItemAsynctask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.Delete(users[0]);
            return null;
        }
    }
}
