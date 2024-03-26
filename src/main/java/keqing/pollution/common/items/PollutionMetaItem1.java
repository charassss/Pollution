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
    }

}
