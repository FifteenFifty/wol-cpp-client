/**
 * @file   combat-log-parser.cpp
 *
 * @brief  This file contains the implementation of a basic combat log parser.
 *
 * @author Matthew Bissenden
 */

#include <combat-log-parser.hpp>
#include <fstream>
#include <iostream>

namespace WoL
{
    CombatLogParser::CombatLogParser(std::string logFilePath)
    :
    logFilePath(logFilePath)
    {
        std::cout<<"logFilePath: " << logFilePath << std::endl;
    }

    CombatLog CombatLogParser::parseLog()
    {
        /*
         * The combat log is iterated through line by line;
         * Each line is parsed for a double-space;
         * Before the double-space is a date (that looks to be in American
         * format);
         * After the double-space is  a comma-separated list of data;
         * 1: eventType (can be matched vs a regex (named eventType))
         * (if 2 is a hex number (0x1550), unit fields are present)
         * 2: sourceGuid (hex long)
         * 3: sourceName (string)
         * 4: sourceFlags (hex int)
         * 5: sourceRaidFlags (hex int)
         * 6: destGuid (hex long)
         * 7: destName (string)
         * 8: destFlags (int)
         * 9: destRaidFlags (hex int)
         * (If present, the following is event data)
         * 10:
         * 6?: if (sr.nextIsString()) return com.wol3.client.data.BinaryCombatLog.Format.BASE
         */

        CombatLog     toReturn;
        std::ifstream logFile(logFilePath.c_str());

        CombatLogLine *combatLogLinePtr = NULL;

        for (std::string line; std::getline(logFile, line); )
        {
            combatLogLinePtr = CombatLogLine::factory(line);
        }

        return CombatLog();
    }
}
