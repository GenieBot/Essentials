package io.sponges.bot.modules.essentials.cmd;

import io.sponges.bot.api.cmd.Command;
import io.sponges.bot.api.cmd.CommandManager;
import io.sponges.bot.api.cmd.CommandRequest;
import io.sponges.bot.api.module.Module;

import java.util.Collection;

public class HelpCommand extends Command {

    private final Module module;

    public HelpCommand(Module module) {
        super("shows command help", "commands", "help", "cmds", "usage");
        this.module = module;
    }

    @Override
    public void onCommand(CommandRequest commandRequest, String[] strings) {
        CommandManager commandManager = module.getCommandManager();
        Collection<Command> commands = commandManager.getCommands();
        StringBuilder builder = new StringBuilder("Commands:");
        for (Command command : commands) {
            builder.append(System.lineSeparator()).append(command.getNames()[0]);
            if (command.getNames().length > 1) {
                builder.append(" (");
                for (int i = 1; i < command.getNames().length; i++) {
                    if (i != 1) builder.append(", ");
                    builder.append(command.getNames()[i]);
                }
                builder.append(")");
            }
            String description = formatDescription(command.getDescription());
            builder.append(" - ").append(description);
        }
        commandRequest.reply(builder.toString());
    }

    private String formatDescription(String input) {
        return input.replace(input.charAt(0), (input.charAt(0) + "").toLowerCase().charAt(0));
    }
}
