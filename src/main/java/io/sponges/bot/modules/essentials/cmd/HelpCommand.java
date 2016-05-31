package io.sponges.bot.modules.essentials.cmd;

import io.sponges.bot.api.cmd.Command;
import io.sponges.bot.api.cmd.CommandRequest;
import io.sponges.bot.api.entities.User;
import io.sponges.bot.api.module.Module;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class HelpCommand extends Command {

    private final Module module;

    public HelpCommand(Module module) {
        super("shows command help", "commands", "help", "cmds", "usage");
        this.module = module;
    }

    @Override
    public void onCommand(CommandRequest request, String[] args) {
        StringBuilder builder = new StringBuilder("Commands (");
        Collection<Command> commands;
        if (args.length == 0) {
            commands = module.getCommandManager().getCommands();
            builder.append(commands.size());
        } else {
            String moduleId = args[0];
            Module module = this.module.getModuleManager().getModule(moduleId);
            if (module == null) {
                request.reply("Invalid module \"" + moduleId + "\". Use the modules command.");
                return;
            }
            commands = module.getCommandManager().getCommands(module);
            if (commands == null || commands.size() == 0) {
                request.reply("That module does not have any commands!");
                return;
            }
            builder.append(commands.size());
        }
        builder.append("): ");
        User user = request.getUser();
        boolean isOp = user.isOp();
        Iterator<Command> iterator = commands.iterator();
        while (iterator.hasNext()) {
            Command command = iterator.next();
            if (command.requiresOp() && !isOp) continue;
            List<String> permissions = command.getPermissions();
            boolean cont = true;
            if (permissions.size() > 0) {
                for (String permission : permissions) {
                    if (!user.hasPermission(permission)) {
                        cont = false;
                        break;
                    }
                }
                if (!cont) continue;
            }
            builder.append(command.getNames()[0]);
            if (iterator.hasNext()) {
                builder.append(", ");
            }
        }
        request.reply(builder.toString());
    }
}
