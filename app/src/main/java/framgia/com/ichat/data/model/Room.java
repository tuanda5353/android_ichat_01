package framgia.com.ichat.data.model;

import java.util.HashMap;

public class Room {
    private String mId;
    private String mName;
    private HashMap<String, Message> mMessages;
    private String mImage;

    public Room(String id, String name, HashMap<String, Message> messages) {
        mId = id;
        mName = name;
        mMessages = messages;
    }

    public Room() {
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

    public HashMap<String, Message> getMessages() {
        return mMessages;
    }

    public void setMessages(HashMap<String, Message> messages) {
        mMessages = messages;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public class PrivateRoomKey {
        public static final String PRIVATE_ROOM = "PRIVATE_ROOM";
        public static final String EXTRA_ID = "EXTRA_ID";
        public static final String NAME_DEFAULT = "New private room";
        public static final String IMAGE_DEFAULT = "http://mblogthumb2.phinf.naver.net/MjAxODAyMTdfMjY4/MDAxNTE4ODcyMjgzMjkx.bTHfo2nyfi6jkIbkD8iUrbDpTEl09zg6PgPA-IKugsYg.C5kpAXWEyUo2nbG1AlcoTpU2abpU2e7ofBJCQgqJ-RUg.JPEG.smilecat007/%EB%A1%A4_%EC%8B%9C%EC%A6%8C8_%EB%9F%BC%EB%B8%94_%EB%A3%AC_1.jpg?type=w800";
    }
}
