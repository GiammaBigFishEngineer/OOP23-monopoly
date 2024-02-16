package app.game.apii;

import java.util.Optional;

public interface GameObserver {

    public boolean update(Optional<Object> obj, String str);

}
