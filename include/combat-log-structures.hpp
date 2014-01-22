/**
 * @file   combat-log-structures.hpp
 *
 * @brief  This file contains definitions for structures that contain Combat
 *         Log information.
 *
 * @author Matthew Bissenden
 */

#ifndef WOL_CLIENT_COMBATLOG_STRUCTURES
#define WOL_CLIENT_COMBATLOG_STRUCTURES

/**
 * This namespace encapsulates all WoL client functionality.
 */
namespace WoL
{
    /**
     * This class represents a single combat log line. It should not be
     * constructed directly. Instead, a child class should be created from a
     * combat log line.
     */
    class CombatLogLine
    {
        protected:
            /**
             * A basic constructor.
             */
            CombatLogLine();
    };

    /**
     * This class defines a structure that contains a parsed combat log.
     */
    class CombatLog
    {
    public:
        /**
         * A basic constructor.
         */
        CombatLog();

    private:
        std::list<CombatLogLine*> lines; /**< An ordered list of
                                          *   CombatLogLine%s, representing
                                          *   the contents of the combat log.
                                          */
    };
}

#endif
