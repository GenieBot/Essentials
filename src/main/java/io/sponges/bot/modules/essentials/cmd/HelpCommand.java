package io.sponges.bot.modules.essentials.cmd;

import io.sponges.bot.api.cmd.Command;
import io.sponges.bot.api.cmd.CommandManager;
import io.sponges.bot.api.cmd.CommandRequest;
import io.sponges.bot.api.module.Module;
import io.sponges.bot.api.module.ModuleManager;

import java.util.Collection;
import java.util.Iterator;

public class HelpCommand extends Command {

    private final ModuleManager moduleManager;
    private final CommandManager commandManager;

    public HelpCommand(Module module) {
        super("shows command help", "commands", "help", "cmds", "usage");
        this.moduleManager = module.getModuleManager();
        this.commandManager = module.getCommandManager();
    }

    @Override
    public void onCommand(CommandRequest request, String[] args) {
        StringBuilder builder = new StringBuilder("Commands (");
        Collection<Command> commands;
        if (args.length == 0) {
            commands = commandManager.getCommands();
            builder.append(commands.size());
        } else {
            String moduleId = args[0];
            Module module = moduleManager.getModule(moduleId);
            if (module == null) {
                request.reply("Invalid module \"" + moduleId + "\". Use the modules command.");
                return;
            }
            if (!commandManager.hasCommands(module)) {
                request.reply("That module does not have any commands!");
                return;
            }
            commands = commandManager.getCommands(module);
            builder.append(commands.size());
        }
        builder.append("): ");
        Iterator<Command> iterator = commands.iterator();
        while (iterator.hasNext()) {
            Command command = iterator.next();
            builder.append(command.getNames()[0]);
            if (iterator.hasNext()) {
                builder.append(", ");
            }
        }
        request.reply(builder.toString());
    }
}
