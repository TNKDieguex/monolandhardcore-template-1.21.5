package net.dieguex.monoland.mobGeneration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EndCrystalEntityMod {
    private static int ticks = 0;
    private static final int RESET_INTERVAL = 8 * 60 * 20; // 8 minutos en ticks
    private static Set<BlockPos> endCrystalPositions = new HashSet<>();

    public static void register() {

        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(world instanceof ServerWorld serverWorld))
                return;
            if (!(entity instanceof EndCrystalEntity crystal))
                return;
            if (!serverWorld.getRegistryKey().equals(World.END))
                return;
            BlockState baseBlock = serverWorld.getBlockState(crystal.getBlockPos().down());
            if (baseBlock.isOf(Blocks.BEDROCK)) {
                BlockPos pos = crystal.getBlockPos();
                int x = pos.getX();
                int z = pos.getZ();

                if (x >= -3 && x <= 3 && z >= -3 && z <= 3) {
                    return; // salir
                }
                endCrystalPositions.add(pos);
            }
        });

        ServerTickEvents.END_WORLD_TICK.register(world -> {
            if (!world.getRegistryKey().equals(World.END))
                return;

            ticks++;
            if (ticks >= RESET_INTERVAL) {
                ticks = 0;
                regenerateCrystal((ServerWorld) world);
            }
        });

    }

    private static void regenerateCrystal(ServerWorld world) {
        boolean isDragonAlive = !world.getEntitiesByType(EntityType.ENDER_DRAGON, d -> d.isAlive()).isEmpty();
        if (!isDragonAlive)
            return;

        List<BlockPos> shuffled = new ArrayList<>(endCrystalPositions);
        Collections.shuffle(shuffled); // Para elegir posiciones aleatorias cada ciclo

        for (BlockPos pos : shuffled) {
            var crystals = world.getEntitiesByType(
                    EntityType.END_CRYSTAL,
                    e -> e.getBlockPos().equals(pos));

            if (crystals.isEmpty()) {
                EndCrystalEntity newCrystal = new EndCrystalEntity(world, pos.getX() + 0.5, pos.getY(),
                        pos.getZ() + 0.5);
                newCrystal.setInvulnerable(false);
                world.spawnEntity(newCrystal);

                world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXPLODE.value(), SoundCategory.BLOCKS, 1.0f,
                        1.0f);
                world.spawnParticles(ParticleTypes.END_ROD,
                        pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,
                        20, 0.3, 0.5, 0.3, 0.1);

                break; // Solo regeneramos uno por ciclo
            }
        }
    }
}
