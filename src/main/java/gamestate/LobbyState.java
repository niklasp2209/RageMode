package gamestate;

import countdowns.LobbyCountdown;
import org.bukkit.Bukkit;

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
        Bukkit.broadcastMessage("§7[§6RageMode§7] §cAlle Spieler wurde teleportiert");
    }

    public LobbyCountdown getCountdown() {
        return countdown;
    }
}
