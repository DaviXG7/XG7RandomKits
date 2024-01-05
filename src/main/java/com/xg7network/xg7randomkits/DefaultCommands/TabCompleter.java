package com.xg7network.xg7randomkits.DefaultCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        List<String> results = new ArrayList<>();

        switch (command.getName()) {
            case "xg7rkregion":
                if (strings.length == 1) {
                    results.add("set");
                    results.add("delete");
                    results.add("save");
                    results.add("check");
                    results.add("cancel");
                    results.add("reset");
                }
                break;
        }

        return results;
    }
}
