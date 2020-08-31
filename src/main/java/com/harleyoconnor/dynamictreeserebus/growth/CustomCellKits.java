package com.harleyoconnor.dynamictreeserebus.growth;

import com.ferreusveritas.dynamictrees.ModConstants;
import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.cells.CellNull;
import com.ferreusveritas.dynamictrees.api.cells.ICell;
import com.ferreusveritas.dynamictrees.api.cells.ICellKit;
import com.ferreusveritas.dynamictrees.api.cells.ICellSolver;
import com.ferreusveritas.dynamictrees.cells.CellDarkOakLeaf;
import com.ferreusveritas.dynamictrees.cells.CellKits;
import com.ferreusveritas.dynamictrees.cells.LeafClusters;
import com.ferreusveritas.dynamictrees.util.SimpleVoxmap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class CustomCellKits {

    public CustomCellKits() {
        super();
        TreeRegistry.registerCellKit(new ResourceLocation(ModConstants.MODID, "asper"), this.asperCellKit);
    }

    private final ICellKit asperCellKit = new ICellKit() {

        private final ICell asperBranch = new ICell() {
            @Override
            public int getValue() {
                return 4;
            }

            final int map[] = {0, 3, 4, 4, 4, 4};

            @Override
            public int getValueFromSide(EnumFacing side) {
                return map[side.ordinal()];
            }
        };

        private final ICell asperLeafCells[] = {
                CellNull.NULLCELL,
                new CellDarkOakLeaf(1),
                new CellDarkOakLeaf(2),
                new CellDarkOakLeaf(3),
                new CellDarkOakLeaf(4)
        };

        private final CellKits.BasicSolver asperSolver = new CellKits.BasicSolver(new short[]{0x0513, 0x0412, 0x0311, 0x0211});

        @Override
        public ICell getCellForLeaves(int i) {
            return this.asperLeafCells[i];
        }

        @Override
        public ICell getCellForBranch(int i, int i1) {
            return i == 1 ? this.asperBranch : CellNull.NULLCELL;
        }

        @Override
        public ICellSolver getCellSolver() {
            return this.asperSolver;
        }

        @Override
        public SimpleVoxmap getLeafCluster() {
            return LeafClusters.darkoak;
        }

        @Override
        public int getDefaultHydration() {
            return 4;
        }
    };

}
