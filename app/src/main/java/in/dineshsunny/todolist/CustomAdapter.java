package in.dineshsunny.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Macbook on 26/07/16.
 */

public class CustomAdapter extends
        RecyclerView.Adapter<CustomAdapter.CustomViewHolder>{

    ArrayList<ActivityModel> amList;
    Context context;

    public CustomAdapter(Context context, ArrayList<ActivityModel>
                                 amList){
        this.amList = amList;
        this.context = context;

    }


    @Override
    public CustomViewHolder
        onCreateViewHolder(ViewGroup parent,
                           int viewType) {

        View v = LayoutInflater.from(
                parent.getContext())
                .inflate(R.layout.item_card, parent,
                        false);
        CustomViewHolder cv = new CustomViewHolder(v);
        return cv;
    }

    @Override
    public void onBindViewHolder(
            CustomViewHolder holder, final int position) {
        holder.name.setText(
                amList.get(position).getName());

        holder.trashIcon.setOnClickListener
                (new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int no_of_rows_deleted =
                        CustomDbAccess.deleteActivity(
                        amList.get(position).getPk());

                if(no_of_rows_deleted == 1){
                    MainActivity.showToast(
                            context, amList.get(position).getName() + " deleted");
                    notifyItemRemoved(position);

                }
                else{
                    MainActivity.showToast(
                            context, "something error occured");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return amList.size();
    }

    public class CustomViewHolder extends
            RecyclerView.ViewHolder{

        TextView name;
        ImageView trashIcon;

        public CustomViewHolder(View itemView) {
            super(itemView);

            name = (TextView)itemView
                    .findViewById(R.id.nameTextView);

            trashIcon = (ImageView) itemView
                    .findViewById(R.id.deleteBtn);

        }
    }
}
