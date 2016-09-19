package io.sponges.bot.modules.essentials.cmd;

import io.sponges.bot.api.cmd.Command;
import io.sponges.bot.api.cmd.CommandRequest;
import io.sponges.bot.api.entities.manager.NetworkModuleManager;
import io.sponges.bot.api.exception.ModuleNotFoundException;
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
        if (!request.getUser().isPlatformAdmin() && !request.getUser().isOp()) {
            request.reply("No permission. Must be admin in this network!");
            return;
        }
        ModuleManager moduleManager = module.getModuleManager();
        NetworkModuleManager networkModuleManager = request.getNetwork().getModuleManager();
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
                    boolean enabled = networkModuleManager.isEnabled(module);
                    String enabledString = enabled ? "enabled" : "disabled";
                    builder.append(Essentials.capitalise(module.getName())).append(" (").append(enabledString).append(")");
                    if (iterator.hasNext()) builder.append(", ");
                }
            }
            builder.append(System.lineSeparator()).append("Subcommands: enable, disable");
            request.reply(builder.toString());
        } else {
            if (args.length == 2) {
                int moduleId;
                try {
                    moduleId = moduleManager.getModuleId(args[0].toLowerCase());
                } catch (ModuleNotFoundException e) {
                    request.reply("Invalid module \"" + args[0] + "\". Usage: modules <module> <enable/disable>");
                    return;
                }
                switch (args[1].toLowerCase()) {
                    case "enable": {
                        Module module = moduleManager.getModule(moduleId);
                        if (module == null) {
                            request.reply("Invalid module!");
                            return;
                        }
                        if (networkModuleManager.isEnabled(module)) {
                            request.reply("Module already enabled!");
                            return;
                        }
                        networkModuleManager.setEnabled(module, true);
                        request.reply("Module enabled!");
                        return;
                    }
                    case "disable": {
                        Module module = moduleManager.getModule(moduleId);
                        if (module == null) {
                            request.reply("Invalid module!");
                            return;
                        }
                        if (module.isRequired()) {
                            request.reply("You cannot disable this module.");
                            return;
                        }
                        if (!networkModuleManager.isEnabled(module)) {
                            request.reply("Module already disabled!");
                            return;
                        }
                        networkModuleManager.setEnabled(module, false);
                        request.reply("Module disabled!");
                        return;
                    }
                }
            }
            request.reply("Invalid subcommand!");
        }
    }
}
