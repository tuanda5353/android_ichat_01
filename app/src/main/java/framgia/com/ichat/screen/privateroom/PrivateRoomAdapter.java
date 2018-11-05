package framgia.com.ichat.screen.privateroom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import framgia.com.ichat.GlideApp;
import framgia.com.ichat.R;
import framgia.com.ichat.data.model.Message;
import framgia.com.ichat.data.model.PrivateRoom;

public class PrivateRoomAdapter extends RecyclerView.Adapter<PrivateRoomAdapter.ViewHolder> {
    private Context mContext;
    private List<PrivateRoom> mPrivateRooms;

    public PrivateRoomAdapter(Context context, List<PrivateRoom> privateRooms) {
        mContext = context;
        mPrivateRooms = privateRooms;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_private_room, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        PrivateRoom privateRoom = mPrivateRooms.get(i);
        viewHolder.bindView(mContext, privateRoom);
    }

    @Override
    public int getItemCount() {
        return mPrivateRooms.isEmpty() ? 0 : mPrivateRooms.size();
    }

    public void addData(List<PrivateRoom> privateRooms) {
        mPrivateRooms.clear();
        mPrivateRooms.addAll(privateRooms);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageAvatar;
        public TextView mTextName;
        public TextView mTextLastMessageOfUser;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageAvatar = itemView.findViewById(R.id.image_avatar);
            mTextName = itemView.findViewById(R.id.text_name);
            mTextLastMessageOfUser = itemView.findViewById(R.id.text_last_message_of_user);
        }

        public void bindView(Context context, PrivateRoom privateRoom) {
            mTextName.setText(privateRoom.getName());
            GlideApp.with(context).load(privateRoom.getImage()).circleCrop().into(mImageAvatar);
            List<Message> messages = new ArrayList<>(privateRoom.getMessages().values());
            mTextLastMessageOfUser.setText(messages.get(messages.size() - 1).getContent());
        }
    }
}
