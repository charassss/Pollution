package keqing.pollution.common.items;

import gregtech.api.GregTechAPI;
import gregtech.api.items.metaitem.StandardMetaItem;
import keqing.pollution.common.CommonProxy;

public class PollutionMetaItem1  extends StandardMetaItem {
    public PollutionMetaItem1() {
        this.setRegistryName("pollution_meta_item_1");
        setCreativeTab(GregTechAPI.TAB_GREGTECH);
    }
    public void registerSubItems() {
        PollutionMetaItems.TEST=this.addItem(1, "test").setMaxStackSize(64).setCreativeTabs(CommonProxy.Pollution_TAB);

        //空白催化核心，需要注魔合成
        PollutionMetaItems.BLANKCORE=this.addItem(2, "blank_catalyst_core").setMaxStackSize(64).setCreativeTabs(CommonProxy.Pollution_TAB);
        //燃能催化核心，用于高温催化
        PollutionMetaItems.HOTCORE=this.addItem(3, "hot_catalyst_core").setMaxStackSize(64).setCreativeTabs(CommonProxy.Pollution_TAB);
        //冷寂催化核心，用于低温催化
        PollutionMetaItems.COLDCORE=this.addItem(4, "cold_catalyst_core").setMaxStackSize(64).setCreativeTabs(CommonProxy.Pollution_TAB);
        //凝聚催化核心，用于物质的聚合催化
        PollutionMetaItems.INTEGRATECORE=this.addItem(5, "integration_catalyst_core").setMaxStackSize(64).setCreativeTabs(CommonProxy.Pollution_TAB);
        //分离催化核心，用于物质的分解催化
        PollutionMetaItems.SEGREGATECORE=this.addItem(6, "segregation_catalyst_core").setMaxStackSize(64).setCreativeTabs(CommonProxy.Pollution_TAB);
    }

}
