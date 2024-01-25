package keqing.pollution.common;

import gregtech.core.sound.GTSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.client.lib.events.ShaderHandler;

import java.util.Random;

import static net.minecraftforge.fml.common.event.FMLInterModComms.sendMessage;

@Mod.EventBusSubscriber(
        modid = "pollution"
)
public class EventLoader {


    @SubscribeEvent
    static void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            Random rand = new Random();
            int randomNum = rand.nextInt(10000);




            if(AuraHelper.getFlux(player.world, player.getPosition())>10&&randomNum>=100&&randomNum<=110) {
                player.sendMessage(new TextComponentTranslation("pollution.command.slowness"));
                player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 0));
                player.world.playSound(player,player.getPosition(), GTSoundEvents.FIRE, SoundCategory.PLAYERS, 1F, 1F);
            }
            if(AuraHelper.getFlux(player.world, player.getPosition())>20&&randomNum>=140&&randomNum<=150) {
                player.sendMessage(new TextComponentTranslation("pollution.command.weakness"));
                player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 200, 0));
            }
            if(AuraHelper.getFlux(player.world, player.getPosition())>30&&randomNum>=80&&randomNum<=90) {
                player.sendMessage(new TextComponentTranslation("pollution.command.nausea"));
                player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 0));
            }
            if(AuraHelper.getFlux(player.world, player.getPosition())>40&&randomNum>=60&&randomNum<=70) {
                player.sendMessage(new TextComponentTranslation("pollution.command.mining"));
                player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 200, 0));
            }
            if(AuraHelper.getFlux(player.world, player.getPosition())>50&&randomNum>=10&&randomNum<=15) {
                player.sendMessage(new TextComponentTranslation("pollution.command.blindness"));
                player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 200, 0));
            }

            if(AuraHelper.getFlux(player.world, player.getPosition())>60&&randomNum<=5) {
                for (int h = -2; h <1; h++)
                    for (int i=-8;i<=8;i++)
                        for (int j=-8;j<=8;j++)
                {
                    BlockPos sand = player.getPosition().add(i , h, j );
                    if(player.world.getBlockState(sand)==Blocks.GRASS.getDefaultState())
                        player.world.setBlockState(sand, Blocks.SAND.getDefaultState());

                    if(player.world.getBlockState(sand)==Blocks.DIRT.getDefaultState())
                        player.world.setBlockState(sand, Blocks.SAND.getDefaultState());

                    if(player.world.getBlockState(sand)==Blocks.WATER.getDefaultState())
                        player.world.setBlockState(sand, Blocks.LAVA.getDefaultState());
                }

            }
        }
    }
}