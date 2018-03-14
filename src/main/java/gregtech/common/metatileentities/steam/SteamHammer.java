package gregtech.common.metatileentities.steam;

import gregtech.api.gui.IUIHolder;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.metatileentity.SteamMetaTileEntity;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.TextureArea;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class SteamHammer extends SteamMetaTileEntity {

    public SteamHammer() {
        super(RecipeMap.HAMMER_RECIPES);
    }

    @Override
    public IItemHandlerModifiable createImportItemHandler() {
        return new ItemStackHandler(1);
    }

    @Override
    public IItemHandlerModifiable createExportItemHandler() {
        return new ItemStackHandler(1);
    }

    @Override
    public ModularUI<IUIHolder> createUI(EntityPlayer player) {
        return ModularUI.builder(BRONZE_BACKGROUND_TEXTURE, 176, 166)
            .widget(0, new LabelWidget<>(6, 6, getMetaName()))
            .widget(1, new SlotWidget<>(this.importItems, 0, 53, 25)
                .setBackgroundTexture(BRONZE_SLOT_BACKGROUND_TEXTURE, SLOT_HAMMER_BACKGROUND))
            .widget(2, new ProgressWidget<>(workableHandler::getProgressPercent, 78, 25, 20, 18)
                .setProgressBar(TextureArea.fullImage("textures/gui/bronze/progress_bar_bronze_hammer.png"),
                    TextureArea.fullImage("textures/gui/bronze/progress_bar_bronze_hammer_filled.png"),
                    ProgressWidget.MoveType.VERTICAL))
            .widget(3, new ImageWidget<SteamHammer>(78, 41)
                .setImageLocation(new GTResourceLocation("textures/gui/bronze/overlay_bronze_hammer_base.png"))
                .setImageWidthHeight(21, 18))
            .widget(4, new SlotWidget<SteamHammer>(this.exportItems, 0, 107, 25, true, false)
                .setBackgroundTexture(BRONZE_SLOT_BACKGROUND_TEXTURE))
            .widget(5, new LabelWidget<>(8, 166 - 96 + 2, player.inventory.getName())) // 166 - gui imageHeight, 96 + 2 - from vanilla code
            .bindPlayerInventory(player.inventory, 6, BRONZE_SLOT_BACKGROUND_TEXTURE)
            .build(this, player);
    }
}
