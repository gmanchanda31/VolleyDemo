package uietdig.volleydemo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by akshay on 16/11/17.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {
    private ArrayList<ModelFields> mModelList;
    private RecyclerView mRecyclerView;
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int itemposition = mRecyclerView.getChildLayoutPosition(view);
            ModelFields item = mModelList.get(itemposition);
            Log.i("hello",item.getDescription());
            Intent i = new Intent(view.getContext(), DescriptionActivity.class);
            i.putExtra("DesObj",item);
            view.getContext().startActivity(i);

        }
    };
    public FeedAdapter(ArrayList<ModelFields> list){
        mModelList = new ArrayList();
        mModelList = list;
    }
    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflator = LayoutInflater.from(context);
        View view = inflator.inflate(R.layout.feed_layout,parent,false);
        view.setOnClickListener(mOnClickListener);

        FeedViewHolder viewHolder = new FeedViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {
        holder.bind(mModelList.get(position).getHeading(), mModelList.get(position).getShortDescription());
    }


    @Override
    public int getItemCount() {
        return mModelList.size();}
    class FeedViewHolder extends RecyclerView.ViewHolder{
        TextView heading;
        TextView shortDescription;
        public FeedViewHolder(View itemView) {

            super(itemView);
            heading = itemView.findViewById(R.id.heading);
            shortDescription = itemView.findViewById(R.id.shortDescription);



        }
        void bind(String Jheading, String Jdescription){
            heading.setText(Jheading);

            shortDescription.setText(Jdescription);
        }
    }
}
