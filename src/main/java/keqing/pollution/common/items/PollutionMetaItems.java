package keqing.pollution.common.items;

public class PollutionMetaItems {
    public static void initialization()
    {
        PollutionMetaItem1 item1 = new PollutionMetaItem1();
    }
    public static void initSubItems()
    {
        PollutionMetaItem1.registerItems();
    }

}
