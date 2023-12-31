package ru.restartserver;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class RestartServer extends JavaPlugin implements Listener {
    FileConfiguration config = getConfig();
    Boolean serverIsRestarting = false;

    public void onEnable() {
        saveDefaultConfig();
        System.out.println("Plugin is enabled!");
        Bukkit.getServer().getPluginManager().registerEvents(this, this);

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of(get("Timezone")));
        config.getStringList("RestartTimes").forEach(b -> {
            String[] numbers = b.split(":");
            int hour = Integer.parseInt(numbers[0]);
            int minute = Integer.parseInt(numbers[1]);
            int second = Integer.parseInt(numbers[2]);

            ZonedDateTime nextRun = now.withHour(hour).withMinute(minute).withSecond(second);
            if (now.compareTo(nextRun) > 0)
                nextRun = nextRun.plusDays(1);

            Duration duration = Duration.between(now, nextRun);
            long initalDelay = duration.getSeconds();

            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(() -> {
                Thread t = new Thread(() -> {
                    try {
                        restart();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                t.start();
            }, initalDelay, TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);
        });
    }

    public void restart() throws InterruptedException {
        if (serverIsRestarting) {
            return;
        }
        serverIsRestarting = true;
        String s = get("string");
        b(translate(s.replace("%time%", "15").replace("%timeword%", get("minutestring"))));
        sleep(300000);
        b(translate(s.replace("%time%", "10").replace("%timeword%", get("minutestring"))));
        sleep(300000);
        b(translate(s.replace("%time%", "5").replace("%timeword%", get("minutestring"))));
        sleep(180000);
        b(translate(s.replace("%time%", "2").replace("%timeword%", get("minutestring"))));
        sleep(105000);
        b(translate(s.replace("%time%", "15").replace("%timeword%", get("secondsstring"))));
        sleep(1000);
        b(translate(s.replace("%time%", "14").replace("%timeword%", get("secondsstring"))));
        sleep(1000);
        b(translate(s.replace("%time%", "13").replace("%timeword%", get("secondsstring"))));
        sleep(1000);
        b(translate(s.replace("%time%", "12").replace("%timeword%", get("secondsstring"))));
        sleep(1000);
        b(translate(s.replace("%time%", "11").replace("%timeword%", get("secondsstring"))));
        sleep(1000);
        b(translate(s.replace("%time%", "10").replace("%timeword%", get("secondsstring"))));
        sleep(1000);
        b(translate(s.replace("%time%", "9").replace("%timeword%", get("secondsstring"))));
        sleep(1000);
        b(translate(s.replace("%time%", "8").replace("%timeword%", get("secondsstring"))));
        sleep(1000);
        b(translate(s.replace("%time%", "7").replace("%timeword%", get("secondsstring"))));
        sleep(1000);
        b(translate(s.replace("%time%", "6").replace("%timeword%", get("secondsstring"))));
        sleep(1000);
        b(translate(s.replace("%time%", "5").replace("%timeword%", get("secondsstring"))));
        sleep(1000);
        b(translate(s.replace("%time%", "4").replace("%timeword%", get("secondsstring"))));
        sleep(1000);
        b(translate(s.replace("%time%", "3").replace("%timeword%", get("secondsstring"))));
        sleep(1000);
        b(translate(s.replace("%time%", "2").replace("%timeword%", get("secondsstring"))));
        sleep(1000);
        b(translate(s.replace("%time%", "1").replace("%timeword%", get("secondstring"))));
        sleep(1000);
        b(translate(get("finalstring")));
        Bukkit.getScheduler().runTask(this, () -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart");
        });
    }
    public String translate(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
    public String get(String s) {
        return config.getString(s);
    }
    public void b(String s) {
        Bukkit.broadcastMessage(s);
    }
}