package gamestate;

import de.niklas.ragemode.Ragemode;

public class GameStateUtils {

    private Ragemode plugin;
    private GameState[] gameStates;
    private GameState currentGameState;

    public GameStateUtils(Ragemode plugin){
        this.plugin = plugin;
        gameStates = new GameState[3];
        gameStates[GameState.Lobby_State] = new LobbyState(this);
        gameStates[GameState.Ingame_State] = new IngameState(plugin);
        gameStates[GameState.End_State] = new EndState();
    }

    public void setGameState(int gameStateID){
        if(currentGameState != null)
            currentGameState.stop();
        currentGameState = gameStates[gameStateID];
        currentGameState.start();
    }

    public void stopGameState(){
        if(currentGameState != null) {
            currentGameState.stop();
            currentGameState = null;
        }
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public Ragemode getPlugin() {
        return plugin;
    }
}
