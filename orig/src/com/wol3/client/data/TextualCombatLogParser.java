// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   TextualCombatLogParser.java

package com.wol3.client.data;

import com.wol3.util.StringReader;
import com.wol3.util.TimestampParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.collections.primitives.ArrayLongList;

// Referenced classes of package com.wol3.client.data:
//            BinaryCombatLog, InvalidLineException, InvalidTimeException, EntryList, 
//            SubjectStateList, ActorPool, EventPool

public class TextualCombatLogParser
{

    public TextualCombatLogParser()
    {
        ttc = new TimestampParser();
        sr = new StringReader();
        eventPattern = java.util.regex.Pattern.compile("[A-Z_]+");
    }

    public com.wol3.client.data.BinaryCombatLog parse(java.io.InputStream in)
        throws java.io.IOException
    {
        return parse(in, new BinaryCombatLog(), null);
    }

    public com.wol3.client.data.BinaryCombatLog parse(java.io.InputStream in, com.wol3.client.data.BinaryCombatLog bcl, java.util.logging.Logger logger)
        throws java.io.IOException
    {
        java.io.BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        java.lang.String line;
        for (int i = 1; (line = reader.readLine()) != null; i++)
            if (!line.trim().equals(""))
                try
                {
                    parseLine(bcl, line, i);
                }
                catch (com.wol3.client.data.InvalidLineException e)
                {
                    if (logger != null)
                        logger.logp(java.util.logging.Level.SEVERE, "TextualCombatLogParser", "parse", "Encountered invalid line, see exception", e);
                    e.printStackTrace();
                }

        return bcl;
    }

    com.wol3.client.data.BinaryCombatLog.Format detectFormat(int lineNumber, java.lang.String line)
        throws com.wol3.client.data.InvalidLineException
    {
        int separatorIndex = line.indexOf("  ");
        if (separatorIndex == -1)
            throw new InvalidLineException(lineNumber, "Failed to detect format: timestamp/fields separator not found.");
        java.lang.String date = line.substring(0, separatorIndex);
        java.lang.String data = line.substring(separatorIndex + 2);
        long timestamp = ttc.parseSlow(date);
        if (timestamp == -1L)
            throw new InvalidLineException(lineNumber, (new StringBuilder("Failed to detect format: invalid date (")).append(date).append(").").toString());
        sr.setString(data);
        java.lang.String eventType = sr.readConstant();
        if (!eventPattern.matcher(eventType).matches())
            throw new InvalidLineException(lineNumber, (new StringBuilder("Failed to detect format: invalid event type (")).append(eventType).append(").").toString());
        long sourceGuid = sr.readHexLong();
        java.lang.String sourceName = sr.readString();
        int sourceFlags = sr.readHexInt();
        int sourceRaidFlags = sr.readHexInt();
        if (sr.nextIsString())
            return com.wol3.client.data.BinaryCombatLog.Format.BASE;
        else
            return com.wol3.client.data.BinaryCombatLog.Format.PATCH_4_2;
    }

    public void parseLine(com.wol3.client.data.BinaryCombatLog bcl, java.lang.String line, int lineNumber)
        throws com.wol3.client.data.InvalidLineException
    {
        if (line.startsWith("\uFEFF"))
            line = line.substring(1);
        if (bcl.format == null)
            bcl.format = detectFormat(lineNumber, line);
        int splitIndex;
        java.lang.String date;
        java.lang.String data;
        long timestamp;
        org.apache.commons.collections.primitives.ArrayLongList timestamps;
        long sinceStart;
        java.lang.String eventType;
        long srcGUID;
        java.lang.String srcName;
        int srcFlags;
        int srcRaidFlags;
        long dstGUID;
        java.lang.String dstName;
        int dstFlags;
        int dstRaidFlags;
        boolean hasUnitFields;
        com.wol3.util.StringReader.InvalidStringException e;
        java.lang.String eventData;
        java.lang.String subjectInfo;
        long subjectInfoGuid;
        int src;
        int dst;
        int health;
        int event;
        int attackPower;
        int spellPower;
        short resourceType;
        int resourceAmount;
        float posX;
        float posY;
        java.lang.NumberFormatException e;
        try
        {
            splitIndex = line.indexOf("  ");
            if (splitIndex == -1)
            {
                java.lang.System.err.println((new StringBuilder("Invalid line: ")).append(line).toString());
                return;
            }
        }
        catch (java.lang.RuntimeException e)
        {
            java.lang.System.out.printf("Exception caused by line %d, contents: \"%s\"\n", new java.lang.Object[] {
                java.lang.Integer.valueOf(bcl.entryList.size() + 1), line
            });
            throw new InvalidLineException(lineNumber, e);
        }
        date = line.substring(0, splitIndex);
        data = line.substring(splitIndex + 2);
        timestamp = ttc.parseSlow(date);
        if (timestamp == -1L)
            throw new InvalidTimeException(lineNumber, (new StringBuilder("Invalid date: ")).append(date).toString());
        timestamps = bcl.entryList.timestamps;
        sinceStart = timestamps.size() <= 0 ? 0L : timestamp - timestamps.get(0);
        if (sinceStart > 0x7fffffffL)
            throw new InvalidTimeException(lineNumber, (new StringBuilder("Error: time is more then 24 days (2^31-1 ms) since start; offending time: ")).append(date).append(", parsed as ").append(new Date(timestamp)).toString());
        sr.setString(data);
        eventType = sr.readConstant();
        if (!eventPattern.matcher(eventType).matches())
            throw new InvalidLineException(lineNumber, (new StringBuilder("Invalid event: \"")).append(eventType).append("\"").toString());
        srcGUID = 0L;
        srcName = null;
        srcFlags = 0;
        srcRaidFlags = 0;
        dstGUID = 0L;
        dstName = null;
        dstFlags = 0;
        dstRaidFlags = 0;
        hasUnitFields = sr.nextIsHexNumber();
        if (hasUnitFields)
        {
            srcGUID = sr.readHexLong();
            srcName = sr.readString();
            if (srcName == null)
                srcName = "";
            srcFlags = sr.readHexInt();
            srcRaidFlags = 0;
            if (bcl.format.hasRaidFlags)
                srcRaidFlags = sr.readHexInt();
            dstGUID = sr.readHexLong();
            dstName = null;
            try
            {
                dstName = sr.readString();
                if (dstName == null)
                    dstName = "";
            }
            // Misplaced declaration of an exception variable
            catch (com.wol3.util.StringReader.InvalidStringException e)
            {
                throw new InvalidLineException(lineNumber, "Invalid string found while parsing target actor name.\n\nYour combat log probably contains pre-4.2 and post-4.2 content.\n\nPlease split your combat log (Tools > Split & archive) and upload the last resulting chunk using the Open a File function.\nYou can extract this chunk from the Logs\\archive\\<chunk start time>.zip file", e);
            }
            dstFlags = sr.readHexInt();
            dstRaidFlags = 0;
            if (bcl.format.hasRaidFlags)
                dstRaidFlags = sr.readHexInt();
        }
        eventData = sr.getRemaining();
        subjectInfo = sr.getSubjectInfo();
        if (subjectInfo == null)
        {
            bcl.stateList.addEmpty();
        } else
        {
            if (bcl.format.isBefore(com.wol3.client.data.BinaryCombatLog.Format.PATCH_4_2))
                throw new InvalidLineException(lineNumber, "Subject state fields found while parsing combat log in pre-4.2 mode.\n\nPlease report this issue on the forums: http://forums.worldoflogs.com/");
            bcl.format = com.wol3.client.data.BinaryCombatLog.Format.PATCH_5_2_NEW;
            sr.setString(subjectInfo);
            subjectInfoGuid = sr.readHexLong();
            health = sr.readInt();
            attackPower = sr.readInt();
            spellPower = sr.readInt();
            resourceType = (short)sr.readInt();
            resourceAmount = sr.readInt();
            posX = (0.0F / 0.0F);
            posY = (0.0F / 0.0F);
            if (sr.available() > 0)
                try
                {
                    posX = sr.readFloat();
                    posY = sr.readFloat();
                    bcl.format = com.wol3.client.data.BinaryCombatLog.Format.PATCH_5_4_2;
                }
                // Misplaced declaration of an exception variable
                catch (java.lang.NumberFormatException e)
                {
                    throw new InvalidLineException(lineNumber, "Failed to parse position value as floating-point number", e);
                }
            if (subjectInfoGuid == 0L)
                bcl.stateList.addEmpty();
            else
                bcl.stateList.add(subjectInfoGuid, health, attackPower, spellPower, resourceType, resourceAmount, posX, posY);
        }
        src = srcGUID != 0L ? bcl.actorPool.indexOf(srcGUID, srcName, srcFlags, srcRaidFlags) : -1;
        dst = dstGUID != 0L ? bcl.actorPool.indexOf(dstGUID, dstName, dstFlags, dstRaidFlags) : -1;
        event = bcl.eventPool.indexOf(eventType, eventData);
        bcl.entryList.add(timestamp, src, dst, event);
        if (bcl.entryList.size() % 25000 == 0)
            java.lang.System.out.printf("Processed %d lines\n", new java.lang.Object[] {
                java.lang.Integer.valueOf(bcl.entryList.size())
            });
    }

    protected com.wol3.util.TimestampParser ttc;
    protected com.wol3.util.StringReader sr;
    protected java.util.regex.Pattern eventPattern;
}
