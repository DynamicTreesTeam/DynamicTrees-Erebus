package com.harleyoconnor.dynamictreeserebus.trees;

import com.ferreusveritas.dynamictrees.systems.DirtHelper;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.harleyoconnor.dynamictreeserebus.AddonConstants;
import com.harleyoconnor.dynamictreeserebus.AddonContent;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.BiomeDictionary;

/**
 * Eucalyptus tree class.
 *
 * @author Harley O'Connor
 */
public final class TreeEucalyptus extends TreeFamily {

    public static final Block primitiveLog = Block.getBlockFromName("erebus:log_eucalyptus");
    public static final Block primitiveLeaves = Block.getBlockFromName("erebus:leaves_eucalyptus");

    public static final class SpeciesEucalyptus extends Species {

        public SpeciesEucalyptus(TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, AddonContent.eucalyptusLeavesProperties);

            // Set growing parameters.
            this.setBasicGrowingParameters(0.15F, 24.0F, 6, 8, 0.7F);

            // Setup environment factors.
            this.envFactor(BiomeDictionary.Type.COLD, 0.75F);
            this.envFactor(BiomeDictionary.Type.DRY, 0.5F);
            this.envFactor(BiomeDictionary.Type.HOT, 1.2F);
            this.envFactor(BiomeDictionary.Type.WET, 1.2F);
            this.envFactor(BiomeDictionary.Type.FOREST, 1.1F);
            this.envFactor(BiomeDictionary.Type.DENSE, 1.1F);

            // Setup seeds.
            this.generateSeed();
            this.setupStandardSeedDropping();
            this.addAcceptableSoils(DirtHelper.MUDLIKE);
        }

    }

    public TreeEucalyptus() {
        super(new ResourceLocation(AddonConstants.MOD_ID, "eucalyptus"));

        this.setPrimitiveLog(primitiveLog.getDefaultState(), new ItemStack(primitiveLog, 1, 0));
        AddonContent.eucalyptusLeavesProperties.setTree(this);

        this.addConnectableVanillaLeaves(((state) -> state.getBlock() == primitiveLeaves));
    }

    @Override
    public void createSpecies() {
        this.setCommonSpecies(new SpeciesEucalyptus(this));
    }

    @Override
    public ItemStack getPrimitiveLogItemStack(int qty) {
        return new ItemStack(primitiveLog, qty, 0);
    }

}
