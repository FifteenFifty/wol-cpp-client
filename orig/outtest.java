class outtest
{
    public static void main(String[] args)
    {
        String line = "1/12 15:17:35.006  SPELL_PERIODIC_HEAL,0x04800000035FEC5C,\"Flur-SteamwheedleCartel\",0x514,0x0,0x0480000002A3DFCE,\"Quickening\",0x40511,0x0,77489,\"Echo of Light\",0x2,0x0000000000000000,0,0,0,0,0,0.00,0.00,2703,2703,0,nil";

        com.wol3.client.data.BinaryCombatLog        combatLog = new com.wol3.client.data.BinaryCombatLog();
        com.wol3.client.data.TextualCombatLogParser parser = new com.wol3.client.data.TextualCombatLogParser();
        java.nio.ByteBuffer                         buffer = java.nio.ByteBuffer.allocate(0x4000000);
        java.nio.ByteBuffer                         buffer1 = java.nio.ByteBuffer.allocate(0x4000000);
        java.nio.ByteBuffer                         buffer2 = java.nio.ByteBuffer.allocate(0x4000000);
        java.nio.ByteBuffer                         buffer3 = java.nio.ByteBuffer.allocate(0x4000000);
        java.nio.ByteBuffer                         buffer4 = java.nio.ByteBuffer.allocate(0x4000000);
        java.nio.ByteBuffer                         buffer5 = java.nio.ByteBuffer.allocate(0x4000000);
        java.nio.ByteBuffer                         buffer6 = java.nio.ByteBuffer.allocate(0x4000000);

        try
        {
            parser.parseLine(combatLog, line, 1);
            combatLog.finish();

            System.out.println("1");

            combatLog.actorPool.writeTo(buffer, com.wol3.client.data.BinaryCombatLog.Format.valueOf("PATCH_5_4_2"),0);
            System.out.println(new String(buffer.array()));

            System.out.println("2");

            buffer = java.nio.ByteBuffer.allocate(0x4000000);
            combatLog.eventPool.writeTo(buffer2, 0);
            System.out.println(new String(buffer2.array()));

            System.out.println("3");

            buffer = java.nio.ByteBuffer.allocate(0x4000000);
            combatLog.entryList.writeTo(buffer3, combatLog.actorPool, 0);
            System.out.println(new String(buffer3.array()));

            System.out.println("4");

            buffer = java.nio.ByteBuffer.allocate(0x4000000);
            combatLog.stateList.writeToBuffer(buffer4, combatLog, 0);
            System.out.println(new String(buffer4.array()));

            System.out.println("5");

            buffer = java.nio.ByteBuffer.allocate(0x4000000);
            combatLog.writeTo(buffer5);
            System.out.println(new String(buffer5.array()));

            System.out.println("6");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }



    }
}
