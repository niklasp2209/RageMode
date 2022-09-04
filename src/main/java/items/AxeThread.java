package items;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

public class AxeThread implements Runnable{

    private Thread thread;
    private Player player;
    private double radius;
    private boolean running;
    private Item item;

    public AxeThread(Player player, double radius, Item item){
        this.player = player;
        this.radius = radius;
        this.item = item;
        this.thread = new Thread(this);
    }

    public void start(){
        this.running=true;
        this.thread.start();
    }

    public void stop(){
        this.running=false;
        this.thread.stop();
    }

    @Override
    public void run() {
        while(running){
//                for(Player current : Bukkit.getOnlinePlayers()){
//                    if(current.getLocation().distance(item.getLocation()) < 2){
//                       current.setHealth(0);
//                       this.item.remove();
//                       this.stop();
//                    }
//                }
//            }
                for (Player current : Bukkit.getOnlinePlayers()){
                    if (item.isOnGround()) {
                        this.item.remove();
                        this.stop();
                    }
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                }
            }
        }
    }
}
