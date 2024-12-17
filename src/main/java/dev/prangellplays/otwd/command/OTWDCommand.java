package dev.prangellplays.otwd.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import dev.prangellplays.otwd.util.OTWDRegistyKeys;
import net.minecraft.command.argument.RegistryPredicateArgumentType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.world.gen.structure.Structure;

public class OTWDCommand {
    private static final DynamicCommandExceptionType STRUCTURE_INVALID_EXCEPTION = new DynamicCommandExceptionType((id) -> {
        return Text.translatable("commands.locate.structure.invalid", new Object[]{id});
    });
    public OTWDCommand() {
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register((LiteralArgumentBuilder<ServerCommandSource>) ((LiteralArgumentBuilder) CommandManager.literal("otwd").requires((source) -> {
            return source.hasPermissionLevel(2);
        })).then(CommandManager.literal("nest").then(CommandManager.argument("dragon_type", RegistryPredicateArgumentType.registryPredicate(OTWDRegistyKeys.DRAGON_NEST)).executes((context) -> {
            return executeNestSummon((ServerCommandSource)context.getSource(), RegistryPredicateArgumentType.getPredicate(context, "structure", OTWDRegistyKeys.DRAGON_NEST, STRUCTURE_INVALID_EXCEPTION));
        }))));
    }

    private static int executeNestSummon(ServerCommandSource source, RegistryPredicateArgumentType.RegistryPredicate<Structure> predicate) throws CommandSyntaxException {
        /*Registry<Structure> registry = source.getWorld().getRegistryManager().get(RegistryKeys.STRUCTURE);
        RegistryEntryList<Structure> registryEntryList = (RegistryEntryList)getStructureListForPredicate(predicate, registry).orElseThrow(() -> {
            return STRUCTURE_INVALID_EXCEPTION.create(predicate.asString());
        });
        BlockPos blockPos = BlockPos.ofFloored(source.getPosition());
        ServerWorld serverWorld = source.getWorld();
        Stopwatch stopwatch = Stopwatch.createStarted(Util.TICKER);
        Pair<BlockPos, RegistryEntry<Structure>> pair = serverWorld.getChunkManager().getChunkGenerator().locateStructure(serverWorld, registryEntryList, blockPos, 100, false);
        stopwatch.stop();
        if (pair == null) {
            throw STRUCTURE_NOT_FOUND_EXCEPTION.create(predicate.asString());
        } else {
            return sendCoordinates(source, predicate, blockPos, pair, "commands.locate.structure.success", false, stopwatch.elapsed());
        }*/
        return 0;
    }
}