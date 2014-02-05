/**
 * @file   wol-combat-log-formatter.hpp
 *
 * @brief  This file contains definitions for a basic combat log formatter
 *         that formats the combat log into the World of Logs upload format.
 *
 * @author Matthew Bissenden
 */

#ifndef WOW_WOL_COMBATLOG_FORMATTER
#define WOW_WOL_COMBATLOG_FORMATTER

#include <string>
#include <combat-log-formatter.hpp>

/**
 * This namespace encapsulates all WoL client functionality.
 */
namespace WoL
{
    /**
     * This class defines a CombatLogFormatter that shall be used to format a
     * WoW combat log into the upload format that World of Logs expects.
     */
    class WolCombatLogFormatter : public CombatLogFormatter
    {
    public:
        /**
         * This method formats the combat log into a std::string.
         *
         * @param combatLog A reference to the CombatLog that is to be
         *                  formatted.
         *
         * @return          A std::string containing the CombatLog's data in
         *                  string format.
         */
        std::string formatLog(CombatLog &combatLog);

        /*
         * A basic, virtual, destructor.
         */
        virtual ~WolCombatLogFormatter();
    };
}
#endif
