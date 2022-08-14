package countdowns;

import de.niklas.ragemode.Ragemode;
import gamestate.GameState;
import gamestate.GameStateUtils;
import org.bukkit.Bukkit;

public class LobbyCountdown extends  Countdown{

    private int idleID;
    private int seconds;
    private GameStateUtils gameStateUtils;
    private final byte idle_Time = 15,
                       countdown_Time = 90;
    private boolean isIdling,
                    isRunning;

    public LobbyCountdown(GameStateUtils gameStateUtils){
        this.gameStateUtils = gameStateUtils;
        seconds = countdown_Time;
    }

    @Override
    public void start() {
        isRunning = true;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(gameStateUtils.getPlugin(), new Runnable() {
            @Override
            public void run() {
                switch (seconds){
                    case 90: case 60: case 30: case 15: case 10: case 3: case 2:
                        Bukkit.broadcastMessage(gameStateUtils.getPlugin().getPrefix()+"§7Die Runde beginnt in §e"+seconds+" §7Sekunden");
                        break;
                    case 1:
                        Bukkit.broadcastMessage(gameStateUtils.getPlugin().getPrefix()+"§7Die Runde beginnt in §e"+seconds+" §7Sekunde");
                        break;
                    case 0:
                        gameStateUtils.setGameState(GameState.Ingame_State);
                        stop();
                        break;

                    default:
                        break;
                }
                seconds--;
            }
        },0, 20);
    }

    @Override
    public void stop() {
        if(isRunning){
            Bukkit.getScheduler().cancelTask(taskID);
            isRunning = false;
            seconds = countdown_Time;
        }
    }

    public void startIdle(){
        isIdling = true;
        idleID = Bukkit.getScheduler().scheduleSyncRepeatingTask(gameStateUtils.getPlugin(), new Runnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage(gameStateUtils.getPlugin().getPrefix()+"§cEs wird noch auf §e"+(gameStateUtils.getPlugin().getMin_Players()-gameStateUtils.getPlugin().getPlayers().size())+" §eSpieler §cgewartet!");
            }
        }, 0, 20*idle_Time);
    }

    public void stopIdle(){
        if(isIdling){
            Bukkit.getScheduler().cancelTask(idleID);
            isIdling = false;
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
