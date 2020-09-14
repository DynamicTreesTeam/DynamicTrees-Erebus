package com.harleyoconnor.dynamictreeserebus.growth;

import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.cells.CellNull;
import com.ferreusveritas.dynamictrees.api.cells.ICell;
import com.ferreusveritas.dynamictrees.api.cells.ICellKit;
import com.ferreusveritas.dynamictrees.api.cells.ICellSolver;
import com.ferreusveritas.dynamictrees.cells.*;
import com.ferreusveritas.dynamictrees.util.SimpleVoxmap;
import com.harleyoconnor.dynamictreeserebus.DynamicTreesErebus;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public final class CustomCellKits {

    public CustomCellKits() {
        TreeRegistry.registerCellKit(new ResourceLocation(DynamicTreesErebus.MODID, "asper"), this.asperCellKit);
        TreeRegistry.registerCellKit(new ResourceLocation(DynamicTreesErebus.MODID, "cypress"), this.cypressCellKit);
        TreeRegistry.registerCellKit(new ResourceLocation(DynamicTreesErebus.MODID, "mossbark"), this.mossbarkCellKit);
    }

    private final ICellKit asperCellKit = new ICellKit() {

        private final ICell asperBranch = new ICell() {
            @Override
            public int getValue() {
                return 4;
            }

            final int[] map = {0, 3, 4, 4, 4, 4};

            @Override
            public int getValueFromSide(EnumFacing side) {
                return map[side.ordinal()];
            }
        };

        private final ICell[] asperLeafCells = {
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

    private final ICellKit cypressCellKit = new ICellKit() {

        private final ICell cypressBranchCell = new ICell() {
            final int[] map = new int[]{4, 3, 5, 5, 5, 5};

            @Override
            public int getValue() {
                return 5;
            }

            @Override
            public int getValueFromSide(EnumFacing side) {
                return map[side.ordinal()];
            }
        };

        private final ICell topCypressBranchCell = new ICell() {
            final int[] map = new int[]{4, 5, 3, 3, 3, 3};

            @Override
            public int getValue() {
                return 5;
            }

            @Override
            public int getValueFromSide(EnumFacing side) {
                return map[side.ordinal()];
            }
        };

        private final ICell[] cypressCells = {
                CellNull.NULLCELL,
                new CellConiferLeaf(1),
                new CellConiferLeaf(2),
                new CellConiferLeaf(3),
                new CellConiferLeaf(4),
                new CellConiferLeaf(5),
                new CellConiferLeaf(6),
                new CellConiferLeaf(7)
        };

        private final CellKits.BasicSolver cypressSolver = new CellKits.BasicSolver(new short[]{0x0514, 0x0413, 0x0312, 0x0211});

        @Override
        public ICell getCellForLeaves(int hydro) {
            return cypressCells[hydro];
        }

        @Override
        public ICell getCellForBranch(int radius, int meta) {
            if (meta == CellMetadata.CONIFERTOP) return this.topCypressBranchCell;
            else if (radius == 1) return this.cypressBranchCell;
            else return CellNull.NULLCELL;
        }

        @Override
        public SimpleVoxmap getLeafCluster() {
            return LeafClusters.deciduous;
        }

        @Override
        public ICellSolver getCellSolver() {
            return cypressSolver;
        }

        @Override
        public int getDefaultHydration() {
            return 4;
        }

    };

    // TODO: Make leaves grow down branches properly.
    private final ICellKit mossbarkCellKit = new ICellKit() {

        private final ICell mossbarkBranch = new ICell() {
            @Override
            public int getValue() {
                return 3;
            }

            final int[] map = {7, 3, 2, 2, 2, 2};

            @Override
            public int getValueFromSide(EnumFacing side) {
                return map[side.ordinal()];
            }
        };

        private final ICell[] mossbarkLeafCells = {
                CellNull.NULLCELL,
                new CellDarkOakLeaf(1),
                new CellDarkOakLeaf(2),
                new CellDarkOakLeaf(3),
                new CellDarkOakLeaf(4)
        };

        private final CellKits.BasicSolver mossbarkSolver = new CellKits.BasicSolver(new short[]{0x0513, 0x0412, 0x0311, 0x0211});

        @Override
        public ICell getCellForLeaves(int i) {
            return this.mossbarkLeafCells[i];
        }

        @Override
        public ICell getCellForBranch(int i, int i1) {
            return i == 1 ? this.mossbarkBranch : CellNull.NULLCELL;
        }

        @Override
        public ICellSolver getCellSolver() {
            return this.mossbarkSolver;
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
