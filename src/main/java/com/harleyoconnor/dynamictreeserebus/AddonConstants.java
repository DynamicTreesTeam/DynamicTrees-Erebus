package com.harleyoconnor.dynamictreeserebus;

import com.ferreusveritas.dynamictrees.ModConstants;
import com.google.common.collect.ImmutableList;

/**
 * Class to hold common constants.
 *
 * @author Harley O'Connor
 */
public final class AddonConstants {

    public static final String MOD_NAME = "Dynamic Trees for The Erebus";
    public static final String MOD_ID = "dynamictreeserebus";
    public static final String MOD_DEPENDENCIES = ModConstants.REQAFTER + ModConstants.DYNAMICTREES_LATEST + ModConstants.NEXT + ModConstants.REQAFTER + AddonConstants.EREBUS_LATEST;

    public static final String PACKAGE_GROUP = "com.harleyoconnor.dynamictreeserebus";

    public static final String EREBUS_MOD_ID = "erebus";
    public static final String EREBUS_LATEST = EREBUS_MOD_ID + "@[1.0.31,)";

    public static final String ASPER = "asper";
    public static final String BALSAM = "balsam";
    public static final String CYPRESS = "cypress";
    public static final String EUCALYPTUS = "eucalyptus";
    public static final String MAHOGANY = "mahogany";
    public static final String MOSSBARK = "mossbark";

    public static final ImmutableList<String> EREBUS_TREES = ImmutableList.of(ASPER, BALSAM, CYPRESS, EUCALYPTUS, MAHOGANY, MOSSBARK);

}
