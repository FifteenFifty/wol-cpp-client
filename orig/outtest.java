class outtest
{
    public static void main(String[] args)
    {
        String line = "1/12 15:17:35.006  SPELL_PERIODIC_HEAL,0x04800000035FEC5C,\"Flur-SteamwheedleCartel\",0x514,0x0,0x0480000002A3DFCE,\"Quickening\",0x40511,0x0,77489,\"Echo of Light\",0x2,0x0000000000000000,0,0,0,0,0,0.00,0.00,2703,2703,0,nil";

        com.wol3.client.data.BinaryCombatLog        combatLog = new com.wol3.client.data.BinaryCombatLog();
        com.wol3.client.data.TextualCombatLogParser parser = new com.wol3.client.data.TextualCombatLogParser();
        java.nio.ByteBuffer                         buffer = java.nio.ByteBuffer.allocate(0x4000000);


        try
        {
            parser.parseLine(combatLog, line, 1);
            combatLog.finish();
            combatLog.writeTo(buffer);
            System.out.println(new String(buffer.array()));
        }
        catch(Exception e)
        {
            System.out.println(e);
        }



    }
}
