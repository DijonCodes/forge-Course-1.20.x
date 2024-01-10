package net.dijon.mccourse.item.custom;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.awt.*;

public class MetalDectectorItem extends Item {

    public MetalDectectorItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (pContext.getLevel().isClientSide()){
            BlockPos positionClicked = pContext.getClickedPos();
            Player player = pContext.getPlayer();
            boolean foundBlock = false;

            for (int i = 0; i<= positionClicked.getY() + 64; i++){
                BlockState blockState = pContext.getLevel().getBlockState(positionClicked.below(i));

                if (isvaluableBlock(blockState)){
                    outputValueCoordinates(positionClicked.below(i), player, blockState.getBlock());
                    foundBlock = true;
                    break;
                }
            }
            if (!foundBlock){
                outputNoValueCoordinates(player);
            }
        }

        pContext.getItemInHand().hurtAndBreak(1, pContext.getPlayer(),
                player -> player.broadcastBreakEvent(player.getUsedItemHand()));

        return InteractionResult.SUCCESS;
    }

    private void outputNoValueCoordinates(Player player) {
        player.sendSystemMessage(Component.translatable("item.mccourse.metal_detector.no_valuables"));
    }

    private void outputValueCoordinates(BlockPos below, Player player, Block block) {
        player.sendSystemMessage(Component.literal("Valuable Found: " + I18n.get(block.getDescriptionId())
        + " at (" + below.getX() + "," + below.getY() + "," + below.getZ() + ")"));
    }

    private boolean isvaluableBlock(BlockState blockState) {
        return blockState.is(Blocks.IRON_ORE) || blockState.is(Blocks.DEEPSLATE_IRON_ORE) || blockState.is(Blocks.DIAMOND_ORE);
    }
}
