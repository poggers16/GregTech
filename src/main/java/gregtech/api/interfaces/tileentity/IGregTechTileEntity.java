package gregtech.api.interfaces.tileentity;

import gregtech.api.interfaces.IDescribable;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A simple compound Interface for all my TileEntities.
 * <p/>
 * Also delivers most of the Informations about my TileEntities.
 * <p/>
 */
public interface IGregTechTileEntity extends ICoverable, IFluidHandler, ITurnable, IGregTechDeviceInformation, IUpgradableMachine, IDigitalChest, IDescribable, IMachineBlockUpdateable {
    /**
     * gets the Error displayed on the GUI
     */
    int getErrorDisplayID();

    /**
     * sets the Error displayed on the GUI
     */
    void setErrorDisplayID(int aErrorID);

    /**
     * @return the MetaID of the Block or the MetaTileEntity ID.
     */
    int getMetaTileID();

    /**
     * Internal Usage only!
     */
    int setMetaTileID(short aID);

    /**
     * @return the MetaTileEntity which is belonging to this, or null if it doesnt has one.
     */
    IMetaTileEntity getMetaTileEntity();

    /**
     * Sets the MetaTileEntity.
     * Even though this uses the Universal Interface, certain BaseMetaTileEntities only accept one kind of MetaTileEntity
     * so only use this if you are sure its the correct one or you will get a Class cast Error.
     *
     */
    void setMetaTileEntity(IMetaTileEntity aMetaTileEntity);

    /**
     * Causes a general Texture update.
     * <p/>
     * Only used Client Side to mark Blocks dirty.
     */
    void issueTextureUpdate();

    /**
     * Causes the Machine to send its initial Data, like Covers and its ID.
     */
    void issueClientUpdate();

    /**
     * causes Explosion. Strength in Overload-EU
     */
    void doExplosion(long aExplosionEU);

    /**
     * Sets the Block on Fire in all 6 Directions
     */
    void setOnFire();

    /**
     * Sets the Owner of the Machine. Returns the set uuid.
     */
    UUID setOwnerId(UUID ownerId);

    /**
     * gets the uuid of the Machines Owner or null if not set.
     */
    UUID getOwnerId();

    /**
     * Sets initial Values from NBT
     *
     * @param aNBT is the NBTTag of readFromNBT
     * @param aID  is the MetaTileEntityID
     */
    void setInitialValuesAsNBT(NBTTagCompound aNBT, short aID);

    /**
     * Called when leftclicking the TileEntity
     */
    void onLeftclick(EntityPlayer aPlayer);

    /**
     * Called when rightclicking the TileEntity
     */
    boolean onRightclick(EntityPlayer aPlayer, EnumFacing aSide, float xOff, float yOff, float zOff, EnumHand hand);

    float getBlastResistance(EnumFacing aSide);

    ArrayList<ItemStack> getDrops();

    /**
     * 255 = 100%
     */
    int getLightOpacity();
    void setLightOpacity(int lightOpacity);



}