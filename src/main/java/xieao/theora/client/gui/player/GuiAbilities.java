package xieao.theora.client.gui.player;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xieao.theora.Theora;
import xieao.theora.api.TheoraAPI;
import xieao.theora.api.player.ability.Abilities;
import xieao.theora.api.player.ability.Ability;
import xieao.theora.api.player.data.PlayerData;

import java.util.Map;

@SideOnly(Side.CLIENT)
public class GuiAbilities extends GuiScreen {

    public static final ResourceLocation BACKGROUND_TEXTURE = Theora.location("textures/gui/ability/background.png");

    private final EntityPlayer player;
    private int x, y, w = 256, h = 234;

    private ResourceLocation[] textures = new ResourceLocation[0];
    private boolean[] status = new boolean[0];
    private int[] levels = new int[0];

    public GuiAbilities(EntityPlayer player) {
        this.player = player;
        refreshAbilities();
    }

    //TODO improve the look

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 5; ++j) {
                int index = j + i * 5;
                if (index < this.textures.length) {
                    if (mousePressed(this.x + 17 + j * 35, this.y + 17 + i * 35, mouseX, mouseY)) {
                        if (mouseButton == 0) {
                            this.status[index] = !this.status[index];
                        }
                    }
                }
            }
        }
    }

    public boolean mousePressed(int x, int y, int mouseX, int mouseY) {
        return mouseX >= x && mouseY >= y && mouseX < x + 32 && mouseY < y + 32;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        this.x = (this.width - this.w) / 2;
        this.y = (this.height - this.h) / 2;
        GlStateManager.pushMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.translate(this.x, this.y, 0.0D);
        this.mc.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        drawTexturedModalRect(0, 0, 0, 0, this.w, this.h);
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 5; ++j) {
                int index = j + i * 5;
                if (index < this.textures.length) {
                    GlStateManager.pushMatrix();
                    if (this.status[index]) {
                        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    } else {
                        GlStateManager.color(0.0F, 0.0F, 0.0F, 1.0F);
                    }
                    this.mc.getTextureManager().bindTexture(this.textures[index]);
                    drawTexturedModalRect(17 + j * 35, 17 + i * 35, 32, 32);
                    GlStateManager.popMatrix();
                }
            }
        }
        GlStateManager.popMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void updateScreen() {
        if (this.mc.world.getTotalWorldTime() % 20 == 0) {
            refreshAbilities();
        }
    }

    public void drawTexturedModalRect(int x, int y, int width, int height) {
        float f0 = 1.0F / (float) width;
        float f1 = 1.0F / (float) height;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double) (x), (double) (y + height), (double) this.zLevel).tex((double) (f0), (double) ((float) (height) * f1)).endVertex();
        bufferbuilder.pos((double) (x + width), (double) (y + height), (double) this.zLevel).tex((double) ((float) (width) * f0), (double) ((float) (height) * f1)).endVertex();
        bufferbuilder.pos((double) (x + width), (double) (y), (double) this.zLevel).tex((double) ((float) (width) * f0), (double) ((float) f1)).endVertex();
        bufferbuilder.pos((double) (x), (double) (y), (double) this.zLevel).tex((double) (f0), (double) ((float) f1)).endVertex();
        tessellator.draw();
    }

    private void refreshAbilities() {
        PlayerData data = TheoraAPI.getPlayerData(this.player);
        if (data != null) {
            Abilities abilities = data.getAbilities();
            Map<Ability, NBTTagCompound> map = abilities.getAbilityMap();
            int abilityCount = map.size();
            this.textures = new ResourceLocation[abilityCount];
            this.status = new boolean[abilityCount];
            this.levels = new int[abilityCount];
            int index = 0;
            for (Ability ability : map.keySet()) {
                ResourceLocation regName = ability.getRegistryName();
                this.textures[index] = new ResourceLocation(regName.getResourceDomain(), "textures/abilities/" + regName.getResourcePath() + ".png");
                this.status[index] = abilities.isActive(ability);
                this.levels[index] = abilities.getAbilityLevel(ability);
                index++;
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
