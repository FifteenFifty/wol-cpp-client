public class test
{
    public static void main(String[] args)
    {
        String line = "1/12 15:17:35.006  SPELL_PERIODIC_HEAL,0x04800000035FEC5C,\"Flur-SteamwheedleCartel\",0x514,0x0,0x0480000002A3DFCE,\"Quickening\",0x40511,0x0,77489,\"Echo of Light\",0x2,0x0000000000000000,0,0,0,0,0,0.00,0.00,2703,2703,0,nil";
        sr = new com.wol3.util.StringReader();

        sr.setString(line);

        System.out.println(sr.getSubjectInfo());
        System.out.println(sr.getRemaining());
    }

    static private com.wol3.util.StringReader sr;
}
