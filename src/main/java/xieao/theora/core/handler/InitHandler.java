package xieao.theora.core.handler;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.objectweb.asm.Type;
import xieao.theora.Theora;
import xieao.theora.core.lib.annotation.InitLoad;
import xieao.theora.core.lib.annotation.PostLoad;
import xieao.theora.core.lib.annotation.PreLoad;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class InitHandler {
    private static final Type LOADER_PRE = Type.getType(PreLoad.class);
    private static final Type LOADER_INIT = Type.getType(InitLoad.class);
    private static final Type LOADER_POST = Type.getType(PostLoad.class);

    public static void pre() {
        load(LOADER_PRE);
    }

    public static void init() {
        load(LOADER_INIT);
    }

    public static void post() {
        load(LOADER_POST);
    }

    static void load(Type type) {
        final List<ModFileScanData.AnnotationData> annotations = ModList.get().getAllScanData().stream()
                .map(ModFileScanData::getAnnotations)
                .flatMap(Collection::stream)
                .filter(a -> type.equals(a.getAnnotationType()))
                .collect(Collectors.toList());
        annotations.forEach(annotationData -> {
            try {
                Class.forName(annotationData.getMemberName()).newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                Theora.LOGGER.error("Failed to load {} instance with annotation {}.", annotationData.getMemberName(), type.getClassName());
                e.printStackTrace();
            }
        });
    }
}
