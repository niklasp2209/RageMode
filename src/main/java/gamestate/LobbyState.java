package gamestate;

import countdowns.LobbyCountdown;

public class LobbyState extends GameState{

    private LobbyCountdown countdown;

    public LobbyState(GameStateUtils gameStateUtils){
        countdown = new LobbyCountdown(gameStateUtils);
    }

    @Override
    public void start() {
        countdown.startIdle();
    }

    @Override
    public void stop() {

    }

    public LobbyCountdown getCountdown() {
        return countdown;
    }
}
