/**
 * @file   combat-log-parser.cpp
 *
 * @brief  This file contains the implementation of a basic combat log parser.
 *
 * @author Matthew Bissenden
 */

#include <combat-log-parser.hpp>

namespace WoL
{
    CombatLogParser::CombatLogParser(std::string logDir)
    :
    logDir(logDir),
    logDirFound(false)
    {
        logDirFound = validLogDir();
    }

    bool CombatLogParser::validLogDir()
    {
        /**
         * @TODO This method is NYI and always returns true.
         *       MLB 20/01/2014
         */
        return true;
    }
}
