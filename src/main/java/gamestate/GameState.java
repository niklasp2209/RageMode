package gamestate;

public abstract class GameState {

    public static final byte Lobby_State = 0,
                             Ingame_State = 1,
                             End_State = 2;

    public abstract void start();
    public abstract void stop();
}
