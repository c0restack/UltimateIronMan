package ultimateironmanplugin.ultimateironmanplugin;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.util.Scanner;

public final class UltimateIronManPlugin extends JavaPlugin implements Listener {

    String polku = "C:/Users/eeron/Desktop/papertestserver/plugins/";

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws FileNotFoundException {

        Player p = event.getPlayer();
        String name = p.getName();
        System.out.println(name + " is being processed");
       // BufferedReader br = null;
       try{
            File myObj = new File(polku + name +".txt");
            if (myObj.createNewFile()){
                try {
                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(polku +name+".txt", false)));
                    out.print(0);
                    out.close();
                } catch (IOException e) {

                }
                System.out.println("Tiedosto luotu: " + myObj.getName());
            }else {
                System.out.println("Tiedosto on jo olemassa");

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
       //print out file
        Scanner scanner = new Scanner(new File(polku + name +".txt"));
        int [] tall = new int [100];
        int deaths = scanner.nextInt();
        if (deaths == 0){
            p.setDisplayName(ChatColor.RED + p.getName());
            p.setPlayerListName(ChatColor.RED + p.getName());


            Bukkit.broadcastMessage(ChatColor.RED + "ULTIMATE IRONMAN " + name + " ON LIITTYNYT SERVERILLE!");
            for(Player all : Bukkit.getOnlinePlayers()){
                all.playSound(p.getLocation(), Sound.ITEM_GOAT_HORN_SOUND_1, 5, 0);
                all.playSound(p.getLocation(), Sound.ITEM_GOAT_HORN_SOUND_2, 5, 0);
                all.playSound(p.getLocation(), Sound.ITEM_GOAT_HORN_SOUND_0, 5, 0);
            }

        } if (deaths >= 10) {
            p.setGameMode(GameMode.SPECTATOR);
        }
        else if (deaths != 0){
            Bukkit.broadcastMessage(ChatColor.BLUE + "Lmao joku nobo joinas");
            //p.setGameMode(GameMode.SURVIVAL);
        }

        System.out.println(name + " on kuollut " + deaths + " kertaa.");
        setMaxHp(deaths, p);

    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) throws FileNotFoundException {

        Player p = event.getPlayer();
        String name = p.getName();
        //kattoo monesti kuollu

        Scanner scanner = new Scanner(new File(polku + name +".txt"));
        int [] tall = new int [100];
        int deaths = scanner.nextInt();
        int newDeaths = deaths + 1;
        //kirjaa uuden kuoleman
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(polku +name+".txt", false)));
            out.print(newDeaths);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (newDeaths >= 10){
            p.setGameMode(GameMode.SPECTATOR);
            Bukkit.broadcastMessage(ChatColor.DARK_GREEN + name + " on kuollu vitusti liian monta kertaa, ettei se saa enään pelata");
        }if (newDeaths == 1){
            Bukkit.broadcastMessage(ChatColor.DARK_GREEN + name + " on menettänyt ULTIMATE IRONMAN statuksensa!");
            for(Player all : Bukkit.getOnlinePlayers()){
                all.playSound(all.getLocation(), Sound.ITEM_AXE_STRIP, 5, 0);

            }
        }

        setMaxHp(newDeaths, p);

    }


    public void setMaxHp(int deaths, Player player){
        int deathLoose = deaths * 2;
        int deathMod = 20 - deathLoose;
        player.setMaxHealth(deathMod);
        String name = player.getName();

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        if (command.getName().equalsIgnoreCase("ironman")){

            if (sender instanceof Player){
                Player p = (Player) sender;
                String name = p.getName();

                if (args.length == 0){

                    //tarkistaa itsensä

                } else if (args.length > 1) {
                    p.sendMessage(ChatColor.GOLD + "Voit tarkistaa vaih yhden pelaajan kerrallaan.");
                }else {
                    String ironName = args[0];
                    Scanner scanner = null;
                    try {
                        scanner = new Scanner(new File(polku + ironName +".txt"));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    int [] tall = new int [100];
                    int deaths = scanner.nextInt();
                    if (deaths == 0){
                        p.sendMessage(ChatColor.GREEN+ ironName + " ON ULTIMATE IRONMAN!");
                    }else {
                        p.sendMessage(ChatColor.GOLD+ ironName + " ei ole ultimate ironman!");
                        int hp = 10 - deaths;
                        p.sendMessage(ChatColor.GOLD+ "Hänellä on "+ hp +" sydäntä jäljellä.");
                    }

                }


            }

        }

        return true;
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

//command to check global and personal stats