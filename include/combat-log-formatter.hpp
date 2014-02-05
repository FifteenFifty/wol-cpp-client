/**
 * @file   combat-log-formatter.hpp
 *
 * @brief  This file contains definitions for a basic combat log formatter.
 *
 * @author Matthew Bissenden
 */

#ifndef WOW_COMBATLOG_FORMATTER
#define WOW_COMBATLOG_FORMATTER

#include <string>
#include <combat-log-structures.hpp>

/**
 * This namespace encapsulates all WoL client functionality.
 */
namespace WoL
{
    /**
     * This class defines a CombatLogFormatter that shall be used to format a
     * WoW combat log as per specific requirements. The methods within this
     * class are virtual, enabling different extensions of this class to
     * format the combat log in different ways.
     */
    class CombatLogFormatter
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
        virtual std::string formatLog(CombatLog &combatLog) = 0;

        /**
         * Virtual destructor due to virtual methods.
         */
        virtual ~CombatLogFormatter();
    };
}
#endif
