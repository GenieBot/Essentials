package io.sponges.bot.modules.essentials.cmd;

import io.sponges.bot.api.cmd.Command;
import io.sponges.bot.api.cmd.CommandManager;
import io.sponges.bot.api.cmd.CommandRequest;
import io.sponges.bot.api.module.Module;

import java.util.Collection;
import java.util.Iterator;

public class HelpCommand extends Command {

    private final Module module;

    public HelpCommand(Module module) {
        super("shows command help", "commands", "help", "cmds", "usage");
        this.module = module;
    }

    @Override
    public void onCommand(CommandRequest commandRequest, String[] strings) {
        CommandManager commandManager = module.getCommandManager();
        StringBuilder builder = new StringBuilder("Commands: ");
        Collection<Command> commands = commandManager.getCommands();
        Iterator<Command> iterator = commands.iterator();
        while (iterator.hasNext()) {
            Command command = iterator.next();
            builder.append(command.getNames()[0]);
            if (iterator.hasNext()) {
                builder.append(", ");
            }
        }
        commandRequest.reply(builder.toString());
    }
}
