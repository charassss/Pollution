package keqing.pollution.common.items;

import gregtech.api.items.metaitem.MetaItem;

public class PollutionMetaItems {
    public static MetaItem<?>.MetaValueItem TEST;
    public static MetaItem<?>.MetaValueItem BLANKCORE;
    public static MetaItem<?>.MetaValueItem HOTCORE;
    public static MetaItem<?>.MetaValueItem COLDCORE;
    public static MetaItem<?>.MetaValueItem INTEGRATECORE;
    public static MetaItem<?>.MetaValueItem SEGREGATECORE;


    public static void initialization()
    {
        PollutionMetaItem1 item1 = new PollutionMetaItem1();
    }
    public static void initSubItems()
    {
        PollutionMetaItem1.registerItems();
    }

}
