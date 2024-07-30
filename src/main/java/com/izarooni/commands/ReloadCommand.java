package com.izarooni.commands;

import com.izarooni.Ezorsia;
import com.izarooni.blocks.VeinMiner;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements BasicCommand {

    private final Ezorsia ezorsia;

    public ReloadCommand(Ezorsia ezorsia) {
        this.ezorsia = ezorsia;
    }

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] strings) {
        VeinMiner.reload(ezorsia);
        stack.getSender().sendMessage("VeinMiner configuration reloaded");
    }
}
