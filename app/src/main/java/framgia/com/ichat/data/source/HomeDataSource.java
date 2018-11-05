package framgia.com.ichat.data.source;

import framgia.com.ichat.data.model.User;

public interface HomeDataSource {
    public interface Remote{
        User getCurrentUser();
    }
}
