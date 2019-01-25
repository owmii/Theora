package xieao.theora.core.handler;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.objectweb.asm.Type;
import xieao.theora.core.lib.annotation.AutoLoad;
import xieao.theora.core.lib.util.debug.ModLogger;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AutoLoadHandler {
    private static final Logger LOGGER = ModLogger.get();
    private static final Marker LOADER = MarkerManager.getMarker("LOADER");

    private static final Type AUTO_LOAD = Type.getType(AutoLoad.class);

    public static void load() {
        final List<ModFileScanData.AnnotationData> annotations = ModList.get().getAllScanData().stream()
                .map(ModFileScanData::getAnnotations)
                .flatMap(Collection::stream)
                .filter(a -> AUTO_LOAD.equals(a.getAnnotationType()))
                .collect(Collectors.toList());
        annotations.forEach(annotationData -> {
            try {
                Class.forName(annotationData.getMemberName()).newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                LOGGER.error(LOADER, "Failed to load {} instance with annotation {}.", annotationData.getMemberName(), AUTO_LOAD.getClassName());
                e.printStackTrace();
            }
        });
    }
}
