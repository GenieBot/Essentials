package io.sponges.bot.modules.essentials.cmd;

import io.sponges.bot.api.cmd.Command;
import io.sponges.bot.api.cmd.CommandManager;
import io.sponges.bot.api.cmd.CommandRequest;
import io.sponges.bot.api.module.Module;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SearchCommand extends Command {

    private final Module module;

    public SearchCommand(Module module) {
        super("searches for a command", "search", "lookup");
        this.module = module;
    }

    @Override
    public void onCommand(CommandRequest request, String[] args) {
        if (args.length == 0) {
            request.reply("Usage: search [term]");
            return;
        }
        CommandManager commandManager = module.getCommandManager();
        Collection<String> names = commandManager.getNames();
        StringBuilder builder = new StringBuilder("Search results:");
        List<String> matches = findClosestMatches(5, names, args[0]);
        if (matches == null || matches.isEmpty()) {
            request.reply("Could not find any commands matching that term! Use -commands for the full list.");
            return;
        }
        for (int i = 0; i < matches.size(); i++) {
            String name = matches.get(i);
            Command command = commandManager.getCommand(name);
            String[] cmdNames = command.getNames();
            String[] aliases = new String[cmdNames.length - 1];
            System.arraycopy(cmdNames, 1, aliases, 0, cmdNames.length - 1);
            builder.append(System.lineSeparator()).append(i + 1).append(") ").append(cmdNames[0]).append(" - ")
                    .append(command.getDescription()).append(" ").append(Arrays.toString(aliases));
        }
        request.reply(builder.toString());
    }

    // the best searching algorithm ever (sarcasm)
    private List<String> findClosestMatches(int matches, Collection<String> collection, String target) {
        CommandManager commandManager = module.getCommandManager();
        List<String> col = new ArrayList<>(collection);
        List<String> entries = new ArrayList<>();
        for (int i = 0; i < matches; i++) {
            int distance = Integer.MAX_VALUE;
            String closest = null;
            for (String compareObject : col) {
                int currentDistance = StringUtils.getLevenshteinDistance(compareObject, target);
                if (currentDistance < distance) {
                    distance = currentDistance;
                    closest = compareObject;
                }
            }
            Command command = commandManager.getCommand(closest);
            entries.add(closest);
            for (String name : command.getNames()) {
                col.remove(name);
            }
        }
        return entries;
    }

}
