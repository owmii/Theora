package xieao.theora.block;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.TypeReferences;
import xieao.theora.Theora;
import xieao.theora.block.cauldron.TileCauldron;
import xieao.theora.block.heat.TileHeat;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ITiles {
    public static final Map<ResourceLocation, TileEntityType<?>> TYPES = new HashMap<>();
    public static final TileEntityType<TileHeat> HEAT = register("heat", TileHeat::new);
    public static final TileEntityType<TileCauldron> CAULDRON = register("cauldron", TileCauldron::new);


    @SuppressWarnings({"unchecked", "ConstantConditions"})
    static <T extends TileEntity> TileEntityType<T> register(String id, Supplier<? extends T> factoryIn) {
        Type<?> type = null;
        try {
            type = DataFixesManager.getDataFixer().getSchema(DataFixUtils.makeKey(1519))
                    .getChoiceType(TypeReferences.BLOCK_ENTITY, Theora.loc(id).toString());
        } catch (IllegalArgumentException illegalstateexception) {
            if (SharedConstants.developmentMode) {
                throw illegalstateexception;
            }
            Theora.LOGGER.warn("No data fixer registered for block entity {}", Theora.loc(id).toString());
        }
        TileEntityType<T> tileentitytype = (TileEntityType<T>) TileEntityType.Builder.create(factoryIn).build(type);
        TYPES.put(Theora.loc(id), tileentitytype);
        return tileentitytype;
    }
}
