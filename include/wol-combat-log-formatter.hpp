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

        /**
         * This method adds entry list information to the FormattedCombatLog.
         *
         * @param combatLog    A reference to a CombatLog whose EntryList
         *                     information shall be formatted into the
         *                     FormattedCombatLog.
         * @param formattedLog A pointer to the FormattedCombatLog to which
         *                     data is to be added.
         */
        void addEntryInfo(CombatLog          &combatLog,
                          FormattedCombatLog *formattedLog);

        /**
         * This method adds state list information to the FormattedCombatLog.
         *
         * @param combatLog    A reference to a CombatLog whose StateList
         *                     information shall be formatted into the
         *                     FormattedCombatLog.
         * @param formattedLog A pointer to the FormattedCombatLog to which
         *                     data is to be added.
         */
        void addStateList(CombatLog          &combatLog,
                          FormattedCombatLog *formattedLog);

        /**
         * This method takes a signed 32bit integral value and converts it to
         * the smallest data type that can hold it. The resulting value is
         * then stored in the appropriate list and its type identifier is
         * returned. If the value is 0, it is not placed into any list.
         *
         * @param value     The value to be stored.
         * @param byteList  A reference to a list of unsigned 8-bit integers
         *                  into which _value_ shall be placed if it fits
         *                  inside an unsigned byte.
         * @param shortList A reference to a list of unsigned 16-bit integers
         *                  into which _value_ shall be placed if it fits
         *                  inside an unsigned short.
         * @param intList   A reference to a list of signed 32-bit integers
         *                  into which _value_ shall be placed if it does not
         *                  fit into any other list.
         *
         * @return          The identifier of the type to which _value_ has
         *                  been mapped.
         */
        uint8_t save(int32_t              value,
                     std::list<uint8_t>  &byteList,
                     std::list<uint16_t> &shortList,
                     std::list<int32_t>  &intList);
    };
}
#endif
