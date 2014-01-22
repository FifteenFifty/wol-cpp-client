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
     * This class represents a combat log Actor.
     */
    class Actor
    {
    public:
        /**
         * A basic constructor that initialises internal variables.
         *
         * @param guid      This Actor's GUID.
         * @param name      The name of the Actor.
         * @param flags     An integral representation of bit-flags that
         *                  represent this Actor's state.
         * @param raidFlags An integral reptresentation of bit-flags that
         *                  represent the Actor's raid status.
         */
        Actor(uint64_t    guid,
              std::string name,
              uint32_t    flags,
              uint32_t    raidFlags);

    private:
        uint64_t    guid;      /**< The Actor's GUID. */
        std::string name;      /**< The name of the Actor. */
        uint32_t    flags;     /**< An integral representation of bit-flags
                                *   that represent this Actor's state. */
        uint32_t    raidFlags; /**< An integral reptresentation of bit-flags
                                *   that represent the Actor's raid status.
                                */
    };

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
