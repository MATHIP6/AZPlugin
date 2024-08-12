package fr.mathip.azplugin.bukkit.utils;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.Iterator;

public final class SchedulerUtil {

    public static void scheduleSyncDelayedTask(final JavaPlugin plugin, final Collection<Integer> tasksCollection, final Runnable task, final long delay) {
        new Task(tasksCollection, task).scheduleSyncDelayedTask(plugin, delay);
    }

    public static void cancelTasks(final JavaPlugin plugin, final Collection<Integer> tasksCollection) {
        final Iterator<Integer> it = tasksCollection.iterator();
        while (it.hasNext()) {
            plugin.getServer().getScheduler().cancelTask(it.next());
            it.remove();
        }
    }

    private SchedulerUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    private static class Task implements Runnable
    {
        private final Collection<Integer> tasksCollection;
        private final Runnable handle;
        private int taskId;

        @Override
        public void run() {
            this.tasksCollection.remove(this.taskId);
            this.handle.run();
        }

        public void scheduleSyncDelayedTask(final JavaPlugin plugin, final long delay) {
            this.taskId = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this, delay);
            if (this.taskId != -1) {
                this.tasksCollection.add(this.taskId);
            }
        }

        public Task(final Collection<Integer> tasksCollection, final Runnable handle) {
            this.tasksCollection = tasksCollection;
            this.handle = handle;
        }
    }
}
