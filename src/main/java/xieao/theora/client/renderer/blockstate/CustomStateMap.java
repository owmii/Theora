package xieao.theora.client.renderer.blockstate;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class CustomStateMap extends StateMapperBase {

    private final IProperty<?> name;
    private final String prefix;
    private final List<IProperty<?>> ignored;

    public CustomStateMap(IProperty<?> name, String prefix, List<IProperty<?>> ignored) {
        this.name = name;
        this.prefix = prefix;
        this.ignored = ignored;
    }

    @Override
    protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
        Map<IProperty<?>, Comparable<?>> map = Maps.newLinkedHashMap(state.getProperties());
        Block block = state.getBlock();
        ResourceLocation rl = Block.REGISTRY.getNameForObject(block);
        String s = String.format("%s:%s", rl.getResourceDomain(), this.prefix + "_" + removeName(this.name, map));
        for (IProperty<?> iproperty : this.ignored) map.remove(iproperty);
        return new ModelResourceLocation(s, this.getPropertyString(map));
    }

    @SuppressWarnings("unchecked")
    private <T extends Comparable<T>> String removeName(IProperty<T> property, Map<IProperty<?>, Comparable<?>> values) {
        return property.getName((T) values.remove(this.name));
    }

    @SuppressWarnings("NullableProblems")
    @SideOnly(Side.CLIENT)
    public static class Builder {

        private IProperty<?> name;
        private String prefix;
        private final List<IProperty<?>> ignored = Lists.newArrayList();

        public Builder withName(IProperty<?> builderPropertyIn) {
            this.name = builderPropertyIn;
            return this;
        }

        public Builder withPreffix(String builderSuffixIn) {
            this.prefix = builderSuffixIn;
            return this;
        }

        public Builder ignore(IProperty<?>... ignores) {
            Collections.addAll(this.ignored, ignores);
            return this;
        }

        public CustomStateMap build() {
            return new CustomStateMap(this.name, this.prefix, this.ignored);
        }
    }
}
