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
         * This method formats the combat log into a FormattedCombatLog.
         *
         * @param combatLog A reference to the CombatLog that is to be
         *                  formatted.
         *
         * @return          A FormattedCombatLog containing the CombatLog's
         *                  data. It is the responsibility of the caller
         *                  to free this memory.
         */
        FormattedCombatLog * formatLog(CombatLog &combatLog);

        /*
         * A basic, virtual, destructor.
         */
        virtual ~WolCombatLogFormatter();

        /**
         * This method adds the WoL magic number to the formatted combat log.
         *
         * @param formattedLog A pointer to the FormattedCombatLog to which
         *                     the WoL magic number is to be added.
         */
        void addMagicNumber(FormattedCombatLog *formattedLog);

        /**
         * This method adds property information to the FormattedCombatLog.
         *
         * @param formattedLog A pointer to the FormattedCombatLog to which
         *                     data is to be added.
         */
        void addPropertyInfo(FormattedCombatLog *formattedLog);

        /**
         * This method adds actor information from the combat log to the
         * FormattedCombatLog.
         *
         * @param combatLog    A reference to a CombatLog whose Actor
         *                     information shall be formatted into the
         *                     FormattedCombatLog.
         * @param formattedLog A pointer to the FormattedCombatLog to which
         *                     data is to be added.
         */
        void addActorInfo(CombatLog          &combatLog,
                          FormattedCombatLog *formattedLog);

        /**
         * This method adds event information from the combat log to the
         * FormattedCombatLog.
         *
         * @param combatLog    A reference to a CombatLog whose Event
         *                     information shall be formatted into the
         *                     FormattedCombatLog.
         * @param formattedLog A pointer to the FormattedCombatLog to which
         *                     data is to be added.
         */
        void addEventInfo(CombatLog          &combatLog,
                          FormattedCombatLog *formattedLog);
    };
}
#endif
