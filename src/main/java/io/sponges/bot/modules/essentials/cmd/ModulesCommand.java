package io.sponges.bot.modules.essentials.cmd;

import io.sponges.bot.api.cmd.Command;
import io.sponges.bot.api.cmd.CommandRequest;
import io.sponges.bot.api.module.Module;
import io.sponges.bot.api.module.ModuleManager;
import io.sponges.bot.modules.essentials.Essentials;

import java.util.Collection;
import java.util.Iterator;

public class ModulesCommand extends Command {

    private final Module module;

    public ModulesCommand(Module module) {
        super("manages the bot modules", "modules", "module", "plugins", "plugin", "modul");
        this.module = module;
    }

    @Override
    public void onCommand(CommandRequest request, String[] args) {
        ModuleManager moduleManager = module.getModuleManager();
        if (args.length == 0) {
            StringBuilder builder = new StringBuilder();
            Collection<Module> modules = moduleManager.getModules();
            Iterator<Module> iterator = modules.iterator();
            if (!iterator.hasNext()) {
                builder.append("No loaded modules!");
            } else {
                builder.append("Loaded modules").append(" (").append(modules.size()).append("): ");
                while (iterator.hasNext()) {
                    Module module = iterator.next();
                    builder.append(Essentials.capitalise(module.getId())).append(" (").append(module.getVersion()).append(")");
                    if (iterator.hasNext()) builder.append(", ");
                }
            }
            builder.append(System.lineSeparator()).append("Subcommands: enable, disable");
            request.reply(builder.toString());
        } else {
            if (args.length == 2) {
                switch (args[0].toLowerCase()) {
                    case "enable": {
                        Module module = moduleManager.getModule(args[1].toLowerCase());
                        if (module == null) {
                            request.reply("Invalid module!");
                            return;
                        }
                        // TODO enabling
                        return;
                    }

                    case "disable": {
                        Module module = moduleManager.getModule(args[1].toLowerCase());
                        if (module == null) {
                            request.reply("Invalid module!");
                            return;
                        }
                        // TODO disabling
                        return;
                    }
                }
            }
            request.reply("Invalid subcommand!");
        }
    }
}
