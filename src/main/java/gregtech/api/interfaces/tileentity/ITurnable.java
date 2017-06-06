package gregtech.api.interfaces.tileentity;


import net.minecraft.util.EnumFacing;

/**
 * Implemented by all my Machines. However without any security checks, if the Players are even allowed to rotate it.
 */
public interface ITurnable {

    /**
     * Get the block's facing.
     *
     * @return front Block facing
     */
    EnumFacing getFrontFacing();

    /**
     * Set the block's facing
     *
     * @param aSide facing to set the block to
     */
    void setFrontFacing(EnumFacing aSide);

    /**
     * Get the block's back facing.
     *
     * @return opposite Block facing
     */
    EnumFacing getBackFacing();

    /**
     * Determine if the wrench can be used to set the block's facing.
     */
    boolean isValidFacing(EnumFacing aSide);

}