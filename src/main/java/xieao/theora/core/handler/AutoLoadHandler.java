package xieao.theora.core.handler;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.objectweb.asm.Type;
import xieao.theora.core.lib.annotation.AutoLoad;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AutoLoadHandler {
    private static final Type AUTO_LOAD = Type.getType(AutoLoad.class);

    public static void load() {
        final List<ModFileScanData.AnnotationData> annotations = ModList.get().getAllScanData().stream()
                .map(ModFileScanData::getAnnotations)
                .flatMap(Collection::stream)
                .filter(a -> AUTO_LOAD.equals(a.getAnnotationType()))
                .collect(Collectors.toList());
        annotations.forEach(data -> {
            try {
                Object member = Class.forName(data.getMemberName()).newInstance();
                if (member instanceof Runnable) {
                    ((Runnable) member).run();
                }
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}
