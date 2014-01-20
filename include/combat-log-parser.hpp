/**
 * @file   combat-log-parser.hpp
 *
 * @brief  This file contains definitions for a basic combat log parser.
 *
 * @author Matthew Bissenden
 */
#ifndef WOL_CPP_CLIENT_COMBATLOG_PARSER
#define WOL_CPP_CLIENT_COMBATLOG_PARSER

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
         * @param logDir The path to the World of Warcraft Logs directory.
         */
        CombatLogParser(std::string logDir);

    private:
        /**
         * This method checks if the World of Warcraft Logs directory is
         * valid.
         *
         * @return True if the logDir path is valid. False otherwise.
         */
        bool validLogDir();

        std::string logDir;      /**< The path to the World of Warcraft Logs
                                  *   directory. */
        bool        logDirFound; /**< A boolean value indicating whether or
                                  *   not the Logs directory is valid.
    };
}
#endif
