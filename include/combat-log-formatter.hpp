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
#include <vector>

/**
 * This namespace encapsulates all WoL client functionality.
 */
namespace WoL
{
    /**
     * This class defines a formatted combat log that is to be uploaded to a
     * destination.
     */
    class FormattedCombatLog
    {
    public:
        /**
         * A basic constructor.
         */
        FormattedCombatLog();

        /**
         * This method inserts an element into the formatted combat log. It
         * converts the data structure into a byte array and appends it to
         * the end of _byteBuffer_.
         *
         * @param  toAdd The item that is to be added to the formatted combat
         *               log.
         * @tparam T     The type of the item that is being added to the
         *               formatted combat log.
         */
        template <typename T>
        void add(T toAdd);

        /**
         * This method empties the formatted combat log, removing all of its
         * stored data.
         */
        void clear();

        /**
         * This method returns the number of bytes that have been stored in
         * this FormattedCombatLog.
         */
        uint32_t size();

        /**
         * This method prints the contents of the FormattedCombatLog to the
         * terminal. IT SHOULD BE REMOVED BEFORE DEPLOYMENT!!!!!!
         */
        void toTerminal();

    private:
        std::vector<uint8_t> byteBuffer; /**< A byte buffer in which the
                                          *   formatted combat log is stored.
                                          */
    };

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
         * This method formats the combat log into a FormattedCombatLog.
         *
         * @param combatLog A reference to the CombatLog that is to be
         *                  formatted.
         *
         * @return          A pointer to a FormattedCombatLog containing the
         *                  CombatLog's data. It is the responsibility of the
         *                  caller to free this memory.
         */
        virtual FormattedCombatLog * formatLog(CombatLog &combatLog) = 0;

        /**
         * Virtual destructor due to virtual methods.
         */
        virtual ~CombatLogFormatter();
    };
}
#endif
