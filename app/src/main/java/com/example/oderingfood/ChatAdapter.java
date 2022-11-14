package com.example.oderingfood;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Object> mObjects;
    public static final int FROM_OTHER = 0;
    public static final int TO_OTHER = 1;
    public ChatAdapter(Context context, List<Object> objects) {
        mContext = context;
        mObjects = objects;
    }

    @Override
    public int getItemViewType(int position) {
        if (mObjects.get(position) instanceof messageFromOther)
            return FROM_OTHER;
        else if (mObjects.get(position) instanceof messageToOther)
            return TO_OTHER;
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(mContext);
        switch (viewType) {
            case FROM_OTHER:
                View itemView0 = li.inflate(R.layout.chat_from_other, parent, false);
                return new FromOtherViewHolder(itemView0);
            case TO_OTHER:
                View itemView1 = li.inflate(R.layout.chat_to_other, parent, false);
                return new ToOtherViewHolder(itemView1);
            default:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case FROM_OTHER:
                messageFromOther user = (messageFromOther) mObjects.get(position);
                FromOtherViewHolder fromOtherViewHolder = (FromOtherViewHolder) holder;
                fromOtherViewHolder.message.setText(user.getMessage());
                fromOtherViewHolder.img.setImageResource(user.getImg());
                break;
            case TO_OTHER:
                messageToOther you = (messageToOther) mObjects.get(position);
                ToOtherViewHolder toOtherViewHolder= (ToOtherViewHolder) holder;
                toOtherViewHolder.message.setText(you.getMessage());
                toOtherViewHolder.img.setImageResource(you.getImg());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mObjects.size();
    }

    public class FromOtherViewHolder extends RecyclerView.ViewHolder {
        private TextView message;
        private ImageView img;
        public FromOtherViewHolder(View itemView) {
            super(itemView);
            message = (TextView) itemView.findViewById(R.id.ac_txt_from_other);
            img = (ImageView) itemView.findViewById(R.id.ac_img_from_other);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    public class ToOtherViewHolder extends RecyclerView.ViewHolder {
        private TextView message;
        private ImageView img;
        public ToOtherViewHolder(View itemView) {
            super(itemView);
            message = (TextView) itemView.findViewById(R.id.ac_txt_to_other);
            img = (ImageView) itemView.findViewById(R.id.ac_img_to_other);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}

class messageFromOther{
    public String message;
    public Integer img;
    messageFromOther(){
        message = "Test tin nhắn gửi đến Test tin nhắn gửi đến Test tin nhắn gửi đến Test tin nhắn gửi đến ";
        img = R.drawable.img_1;
    }
    public String getMessage() {
        return message;
    }

    public Integer getImg() {
        return img;
    }
}

class messageToOther{
    public String message;
    public Integer img;
    messageToOther(){
        message = "Test tin nhắn gửi đi Test tin nhắn gửi đi Test tin nhắn gửi đi Test tin nhắn gửi đi ";
        img = R.drawable.img_1;
    }
    public String getMessage() {
        return message;
    }

    public Integer getImg() {
        return img;
    }
}