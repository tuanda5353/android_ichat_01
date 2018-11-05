package framgia.com.ichat.data.model;

import java.util.HashMap;

public class PrivateRoom {
    private String mId;
    private String mName;
    private HashMap<String,Message> mMessages;
    private String image;
    public PrivateRoom(String id, String name, HashMap<String,Message> messages) {
        mId = id;
        mName = name;
        mMessages = messages;
    }

    public PrivateRoom() {
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public HashMap<String,Message> getMessages() {
        return mMessages;
    }

    public void setMessages(HashMap<String,Message> messages) {
        mMessages = messages;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public class PrivateRoomKey {
        public static final String PRIVATE_ROOM = "private_room";
    }
}
