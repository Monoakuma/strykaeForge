package com.github.monoakuma.strykae.objects.items.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static com.github.monoakuma.strykae.Strykae.MOD_ID;
import static com.github.monoakuma.strykae.Strykae.spellHandler;

@SideOnly(Side.CLIENT)
public class GuiSigilTablet extends GuiScreen {
    EntityPlayer carver;
    ItemStack tablet;
    private static final ResourceLocation TEXTURES = new ResourceLocation(MOD_ID,"textures/gui/tablet.png");
    private final ArrayList<String> focusNames = spellHandler.getSpellnameList();
    private final ArrayList<String> specifierNames = new ArrayList<>(Arrays.asList("ia","at","ti","um","on"));
    private final ArrayList<String> exaggerators = new ArrayList<>(Arrays.asList("Kat","Dag","Ral","Vut","Tur","Mut","Yal","Cae","Sid"));
    private GuiSigilTablet.GuiCycleButton exag1;
    private GuiSigilTablet.GuiCycleButton exag2;
    private GuiSigilTablet.GuiCycleButton exag3;
    private GuiSigilTablet.GuiCycleButton focus;
    private GuiSigilTablet.GuiCycleButton specifier;
    private GuiButton finish;
    public GuiSigilTablet(EntityPlayer carver, ItemStack tabula_rasa) {
        this.carver=carver;
        this.tablet=tabula_rasa;
    }
    @Override
    public void initGui() {
        int guiLeft = (this.width - 192) / 2;
        boolean carved = false;
        if (this.tablet.hasTagCompound()){
            if (!this.tablet.getTagCompound().getString("StrykaeSpell").equals("")) {
                carved=true;
            }
        }
        if (!carved) {
            super.initGui();
            exag1 =this.addButton(new GuiSigilTablet.GuiCycleButton(0,guiLeft+60,29,18,16,exaggerators));
            exag2 =this.addButton(new GuiSigilTablet.GuiCycleButton(1,guiLeft+81,29,18,16,exaggerators));
            exag3 =this.addButton(new GuiSigilTablet.GuiCycleButton(2,guiLeft+102,29,18,16,exaggerators));
            focus =this.addButton(new GuiSigilTablet.GuiCycleButton(3,guiLeft+60,48,61,16,focusNames));
            specifier=this.addButton(new GuiSigilTablet.GuiCycleButton(4,guiLeft+81,67,18,16,specifierNames));
            finish=this.addButton(new GuiButton(5,guiLeft+70,129,41,8,"CARVE"));
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int x, int y, float z) {
        this.drawDefaultBackground();
        int guiLeft = (this.width - 192) / 2;
        this.mc.getTextureManager().bindTexture(TEXTURES);
        this.drawTexturedModalRect(guiLeft, 2, 0, 0, 158, 180);
        if (exag1!=null&&exag2!=null&&exag3!=null&&focus!=null) {
            this.fontRenderer.drawString(String.valueOf((exag1.getIndex()+1)*(exag2.getIndex()+1)*(exag3.getIndex()+1)),guiLeft+110,113,18);
            this.fontRenderer.drawString(String.valueOf(spellHandler.findSpell(focus.displayString).getCost()),guiLeft+110,90,18);
            String type = (spellHandler.isGaldic(spellHandler.findSpell(focus.displayString))) ? "G" : "S";
            int color = (spellHandler.isGaldic(spellHandler.findSpell(focus.displayString))) ? 16752640 : 6173439;
            this.fontRenderer.drawString(type,guiLeft+87,150,color);
            finish.enabled=(exag1.getIndex() + 1) * (exag2.getIndex() + 1) * (exag3.getIndex() + 1) >= spellHandler.findSpell(focus.displayString).getCost();
        }
        super.drawScreen(x, y, z);
    }

    @Override
    protected void actionPerformed(@Nonnull GuiButton button) throws IOException {
        super.actionPerformed(button);
        if (button.id==5) {
            spellHandler.assignSpellNBT(this.tablet,String.format("%d%d%d ",(exag1.getIndex()+1),(exag2.getIndex()+1),(exag3.getIndex()+1)) + focus.displayString + specifier.displayString);
            button.enabled=false;
            this.mc.displayGuiScreen((GuiScreen)null);
            //PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
            //packetbuffer.writeItemStack(this.tablet);
            //this.mc.getConnection().sendPacket(new CPacketCustomPayload("strykae", packetbuffer));
            //Strykae.network.sendTo(new SigilCraftMessage(String.format("%d%d%d ",(exag1.getIndex()+1),(exag2.getIndex()+1),(exag3.getIndex()+1)) + focus.displayString + specifier.displayString),(EntityPlayerMP) this.carver);
        }
    }

    static class GuiCycleButton extends GuiButton {
        private final ArrayList<String> titles;
        private int index=0;
        public GuiCycleButton(int i, int x, int y,int width,int height, ArrayList<String> titles) {
            super(i, x, y, width, height, titles.get(0));
            this.titles = titles;
        }
        public int getIndex() {
            return index%titles.size();
        }
        @Override
        public boolean mousePressed(@Nonnull Minecraft minecraft, int x, int y) {
            boolean clicked = super.mousePressed(minecraft, x, y);
            if (clicked) {
                ++index;
                this.displayString=titles.get(index%titles.size());
            }
            return clicked;
        }
    }
}
