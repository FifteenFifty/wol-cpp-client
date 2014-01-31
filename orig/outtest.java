class outtest
{
    public static void main(String[] args)
    {
        String line = "1/12 15:17:35.006  SPELL_PERIODIC_HEAL,0x04800000035FEC5C,\"Flur-SteamwheedleCartel\",0x514,0x0,0x0480000002A3DFCE,\"Quickening\",0x40511,0x0,77489,\"Echo of Light\",0x2,0x0000000000000000,0,0,0,0,0,0.00,0.00,2703,2703,0,nil";

        com.wol3.client.data.BinaryCombatLog        combatLog = new com.wol3.client.data.BinaryCombatLog();
        com.wol3.client.data.TextualCombatLogParser parser = new com.wol3.client.data.TextualCombatLogParser();
        java.nio.ByteBuffer                         bb     = java.nio.ByteBuffer.allocate(0x1fb);
        int                                         fromActor = 0;
        int                                         fromEvent = 0;
        int                                         fromEntry = 0;

        try
        {
            parser.parseLine(combatLog, line, 1);
            combatLog.finish();

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
