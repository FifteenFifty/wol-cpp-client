/**
 * @file   combat-log-parser.hpp
 *
 * @brief  This file contains definitions for a basic combat log parser.
 *
 * @author Matthew Bissenden
 */

#ifndef WOL_CPP_CLIENT_COMBATLOG_PARSER
#define WOL_CPP_CLIENT_COMBATLOG_PARSER

#include <string>
#include <combat-log-structures.hpp>

/**
 * This namespace encapsulates all WoL client functionality.
 */
namespace WoL
{
    /**
     * This class defines a CombatLogParser that shall parse a WoW combat log
     * and prepare it for sending to the WoL server.
     */
    class CombatLogParser
    {
    public:
        /**
         * A basic constructor that initialises internal variables.
         *
         * @param logFilePath The path to the World of Warcraft Log file.
         */
        CombatLogParser(std::string logFilePath);

        /**
         * This method parses the combat log into a CombatLog object.
         *
         * @return A CombatLog object containing the parsed combat log.
         */
        CombatLog parseLog();

    private:
        std::string logFilePath; /**< The path to the World of Warcraft Log
                                  *   file. */
    };
}
#endif
