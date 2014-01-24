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

#include <utils.hpp>
#include <list>

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
        uint32_t    raidFlags; /**< An integral representation of bit-flags
                                *   that represent the Actor's raid status.
                                */
    };

    /**
     * This class represents a combat log Event.
     */
    class Event
    {
    public:
        /**
         * A basic constructor that initialises internal variables.
         *
         * @param id   This Event's identifier.
         * @param type A string containing the type of this Event.
         * @param data A comma-separated list of arbitrary values that this
         *             Event shall parse and contain.
         */
        Event(uint32_t id, std::string type, std::string data);

        /**
         * This method returns the data contained with this event as a
         * formatted string. The string returned shall be of the following
         * form:
         * <Event _id_, _type_, [data[1], data[2], ...]>
         * where data[x] refers to the elements within the ordered list of
         * Event data.
         *
         * @return A formatted string containing the data contained within
         *         this Event.
         */
        std::string toString();

    private:
        uint32_t                id;      /** The identifier of this event.
                                          */
        std::string             type ;   /**< A reference to an element of
                                          *   _uniqueStrings_, containing
                                          *   the type of this event. */
        std::list<std::string> dataList; /**< A list strings containing the
                                          *   data that have been assigned to
                                          *   this event. */
    };

    /**
     * This class represents a single combat log line. It should not be
     * constructed directly. Instead, a child class should be created from a
     * combat log line.
     */
    class CombatLogLine
    {
    public:
        /**
         * This is a factory method. It takes a line of combat-log text and
         * from it creates the appropriate CombatLogLine child, which is
         * returned.
         *
         * @param line A line from the combat log.
         *
         * @return     A pointer to a child of CombatLogLine, containing the
         *             information passed in _line_.
         */
        static CombatLogLine * factory(std::string line);

    protected:
        /**
         * A basic constructor.
         */
        CombatLogLine();

    private:
        /**
         * This copy constructor should not be called. If it is called, a
         * ForbiddenMethodCallException is thrown.
         *
         * @param copy ignored.
         */
        CombatLogLine(const CombatLogLine &copy);

        /**
         * This assignment operator should not be used. If it is used, it
         * throws a ForbiddenMethodCallException.
         *
         * @param ass Ignored.
         *
         * @return    Unreachable.
         */
        const CombatLogLine & operator=(const CombatLogLine ass);

        std::string  timestamp;   /**< This line's timestamp. */
        Actor       *source;      /**< A pointer to the source actor. */
        Actor       *destination; /**< A pointer to the destination actor. */
        Event       *event;       /**< The event that this line describes. */
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

        /**
         * This method adds a line to the combat log.
         *
         * @param linePtr A pointer to a CombatLogLine that is to be added to
         *                this combat log.
         */
        void addLine(CombatLogLine *line);

    private:
        std::list<CombatLogLine*> lines; /**< An ordered list of
                                          *   CombatLogLine%s, representing
                                          *   the contents of the combat log.
                                          */
    };
}

#endif
