package gregtech.api.interfaces.tileentity;

import cofh.api.energy.IEnergyReceiver;
import gregtech.api.GregTech_API;
import gregtech.api.util.GT_Utility;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static gregtech.api.enums.GT_Values.V;

/**
 * Interface for getting Connected to the GregTech Energy Network.
 * <p/>
 * This is all you need to connect to the GT Network.
 * IColoredTileEntity is needed for not connecting differently coloured Blocks to each other.
 * IHasWorldObjectAndCoords is needed for the InWorld related Stuff. @BaseTileEntity does implement most of that Interface.
 */
public interface IEnergyConnected extends IColoredTileEntity, IHasWorldObjectAndCoords, IEnergyAcceptor, IEnergyEmitter {

    /**
     * Inject Energy Call for Electricity. Gets called by EnergyEmitters to inject Energy into your Block
     * <p/>
     * Note: you have to check for @inputEnergyFrom because the Network won't check for that by itself.
     *
     * @param aSide 0 - 5 = Vanilla Directions of YOUR Block the Energy gets inserted to. 6 = No specific Side (don't do Side checks for this Side)
     * @return amount of used Amperes. 0 if not accepted anything.
     */
    long injectEnergyUnits(EnumFacing aSide, long aVoltage, long aAmperage);

    /**
     * Sided Energy Input
     */
    boolean inputEnergyFrom(EnumFacing aSide);

    /**
     * Sided Energy Output
     */
    boolean outputsEnergyTo(EnumFacing aSide);

    /**
     * Utility for the Network
     */
    class Util {

        /**
         * Emits Energy to the E-net. Also compatible with adjacent IC2 TileEntities.
         *
         * @return the used Amperage.
         */

        public static long emitEnergyToNetwork(long voltage, long amperage, IEnergyConnected emitter) {
            long rUsedAmperes = 0;
            for (EnumFacing side : EnumFacing.VALUES) {
                if (amperage <= rUsedAmperes) break;
                EnumFacing oppositeSide = side.getOpposite();
                if (emitter.outputsEnergyTo(side)) {
                    TileEntity tileEntity = emitter.getWorldObj().getTileEntity(emitter.getWorldPos().offset(side));
                    if (tileEntity instanceof IEnergyConnected) {
                        if (emitter.getColorization() >= 0) {
                            byte tColor = ((IEnergyConnected) tileEntity).getColorization();
                            if (tColor >= 0 && tColor != emitter.getColorization()) continue;
                        }
                        rUsedAmperes += ((IEnergyConnected) tileEntity).injectEnergyUnits(oppositeSide, voltage, amperage - rUsedAmperes);
                    } else if (tileEntity instanceof IEnergySink) {
                        if (((IEnergySink) tileEntity).acceptsEnergyFrom(emitter, oppositeSide)) {
                            while (amperage > rUsedAmperes && ((IEnergySink) tileEntity).getDemandedEnergy() > 0 &&
                                    ((IEnergySink) tileEntity).injectEnergy(oppositeSide, voltage, voltage) < voltage)
                                rUsedAmperes++;
                        }
                    } else if (tileEntity instanceof IEnergyReceiver) {
                        int rfOut = (int) (voltage * GregTech_API.mEUtoRF / 100);
                        if (((IEnergyReceiver) tileEntity).receiveEnergy(oppositeSide, rfOut, true) == rfOut) {
                            ((IEnergyReceiver) tileEntity).receiveEnergy(oppositeSide, rfOut, false);
                            rUsedAmperes++;
                        }
                    }
                }
            }
            return rUsedAmperes;
        }
    }

}