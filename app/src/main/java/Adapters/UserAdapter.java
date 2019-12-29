package Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.app.roomcache.R;

import java.util.List;

import Model.User;
import Room.UserRepository;
import ViewModels.UserViewModel;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> userList;
    private Context context;
    private UserViewModel userModel;
    private UserRepository userRepo;

    public UserAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row_layout,parent,false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, final int position) {

        final User users = userList.get(position);

        holder.row_name.setText(users.getName());
        holder.row_age.setText(users.getAge());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userModel = ViewModelProviders.of((FragmentActivity) context).get(UserViewModel.class);
                userModel.deleteItem(users);
                userList.remove(users);
                notifyItemRemoved(position);

                userRepo = new UserRepository(context);
                String str = users.get_id();
                Log.d("userid",str);
                userRepo.deleteUser(str);

            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView row_name,row_age;
        ImageView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            row_name = itemView.findViewById(R.id.row_name);
            row_age = itemView.findViewById(R.id.row_age);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
