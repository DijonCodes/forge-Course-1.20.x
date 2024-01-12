package net.dijon.mccourse.item.custom;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import  net.minecraft.world.level.block.Blocks;
import  net.minecraft.world.level.block.state.BlockState;


public class BlockDetectorItem extends Item {
    public BlockDetectorItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext){
        if (pContext.getLevel().isClientSide()){
            BlockPos positionClicked = pContext.getClickedPos();
            Player player = pContext.getPlayer();
            BlockState blockState = pContext.getLevel().getBlockState(positionClicked);
            outputBlockDesc(positionClicked, player, blockState.getBlock());
        }
        return InteractionResult.SUCCESS;
    }
    private void outputBlockDesc(BlockPos pos, Player player, Block block){
        player.sendSystemMessage(Component.literal("Block is: " + I18n.get(block.getDescriptionId())));
    }
}
