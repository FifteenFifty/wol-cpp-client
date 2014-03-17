class outtest
{
    public static void main(String[] args)
    {
        String line1 = "1/12 15:17:35.006  SPELL_PERIODIC_HEAL,0x04800000035FEC5C,\"Flur-SteamwheedleCartel\",0x514,0x0,0x0480000002A3DFCE,\"Quickening\",0x40511,0x0,77489,\"Echo of Light\",0x2,0x0000000000000000,0,0,0,0,0,0.00,0.00,2703,2703,0,nil";
        String line2 = "1/12 15:17:35.253  SPELL_CAST_SUCCESS,0x040000000409534D,\"Modrejtom-Drak'thul\",0x40514,0x0,0xF151175000000065,\"Siegecrafter Blackfuse\",0x10a48,0x0,102355,\"Faerie Swarm\",0x8,0x0000000000000000,0,0,0,0,0,0.00,0.00";
        String line3 = "1/12 15:17:35.334  SPELL_SUMMON,0x04000000043BFCF4,\"Trailing-KulTiras\",0x512,0x0,0xF1303C4E000001BB,\"Greater Fire Elemental\",0xa28,0x0,117663,\"Fire Elemental Totem\",0x4";
        String line4 = "1/12 15:17:35.334  SPELL_CAST_SUCCESS,0x04000000043BFCF4,\"Trailing-KulTiras\",0x512,0x0,0x0000000000000000,nil,0x80000000,0x80000000,2894,\"Fire Elemental Totem\",0x4,0x0000000000000000,0,0,0,0,0,0.00,0.00";

        com.wol3.client.data.BinaryCombatLog        combatLog = new com.wol3.client.data.BinaryCombatLog();
        com.wol3.client.data.TextualCombatLogParser parser = new com.wol3.client.data.TextualCombatLogParser();
        java.nio.ByteBuffer                         bb     = java.nio.ByteBuffer.allocate(0x1fb);
        int                                         fromActor = 0;
        int                                         fromEvent = 0;
        int                                         fromEntry = 0;

        try
        {
            parser.parseLine(combatLog, line1, 1);
            parser.parseLine(combatLog, line2, 2);
            parser.parseLine(combatLog, line3, 3);
            parser.parseLine(combatLog, line4, 4);
            combatLog.finish();

            for (int i = 0; i < com.wol3.client.data.EntryList.sources.size(); ++i)
            {
                System.out.println("source: " + com.wol3.client.data.EntryList.sources.get(i);
            }

            /* The following is copied code from     BinaryCombatLog::public void
             * writeTo(java.nio.ByteBuffer bb, int fromActor, int fromEvent,
             * int fromEntry)                           |
             */
            com.wol3.util.perf.SimpleProfiler.staticEnter("BCL:writeTo");
            bb.putLong(0x1fb902131029L);
            bb.rewind();
System.out.println("1");
System.out.println(bytesToHexString(bb.array()));
bb = java.nio.ByteBuffer.allocate(0x1fb);

            java.io.ByteArrayOutputStream bytesOut = new java.io.ByteArrayOutputStream();
            try
            {
                combatLog.properties.setProperty("format", combatLog.format.name());
                combatLog.properties.store(bytesOut, "");
            }
            catch (java.io.IOException e)
            {
                throw new RuntimeException(e);
            }
            com.wol3.util.ByteBufferUtils.putBytes(bb, bytesOut.toByteArray());
System.out.println("2");
System.out.println(bytesToHexString(bb.array()));
bb = java.nio.ByteBuffer.allocate(0x1fb);

            combatLog.actorPool.writeTo(bb, combatLog.format, fromActor);
System.out.println("3");
System.out.println(bytesToHexString(bb.array()));
bb = java.nio.ByteBuffer.allocate(0x1fb);
            combatLog.eventPool.writeTo(bb, fromEvent);
System.out.println("4");
System.out.println(bytesToHexString(bb.array()));
bb = java.nio.ByteBuffer.allocate(0x1fb);
            combatLog.entryList.writeTo(bb, combatLog.actorPool, fromEntry);
System.out.println("5");
System.out.println(bytesToHexString(bb.array()));
bb = java.nio.ByteBuffer.allocate(0x1fb);

            if (combatLog.format.hasNewSubjectStates)
                combatLog.stateList.writeToBuffer(bb, combatLog, fromEntry);
System.out.println("6");
System.out.println(bytesToHexString(bb.array()));
bb = java.nio.ByteBuffer.allocate(0x1fb);
            com.wol3.util.perf.SimpleProfiler.staticExit();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public static String bytesToHexString(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for(byte b : bytes){
            sb.append(String.format("%02x", b&0xff));
        }
        return sb.toString();
    }

}
