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
#include <map>
#include <boost/date_time/posix_time/posix_time.hpp>

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
         * This method creates, or retrieves (if already created), an Actor.
         *
         * @param guid      A string containing the Actor's GUID.
         * @param name      A string containing the name of the Actor.
         * @param flags     A string containing bit-flags that represent the
         *                  Actor's state.
         * @param raidFlags A string containing bit-flags representing the
         *                  Actor's raid status.
         *
         * @return          A pointer to the requested Actor.
         */
        static Actor * factory(std::string guid,
                               std::string name,
                               std::string flags,
                               std::string raidFlags);

        /**
         * A basic, empty, constructor.
         */
        Actor();

        /**
         * A basic assignment operator.
         *
         * @param ass A reference to an Actor to which this Actor is being
         *            assigned.
         *
         * @return    A reference to an Actor
         */
        Actor & operator=(const Actor &ass);

        /**
         * This method returns a list of pointers to every Actor that took
         * part in the combat log.
         *
         * @return A list of pointers to every Actor that took part in the
         *         combat log.
         */
        static std::list<Actor*> getActors();

        /**
         * This method returns the Actor's index.
         *
         * @return The Actor's index.
         */
        uint32_t getIndex();

        /**
         * This method returns the Actor's GUID.
         *
         * @return The Actor's GUID.
         */
        uint64_t getGuid();

        /**
         * This method returns the name of the Actor.
         *
         * @return The name of the Actor.
         */
        std::string getName();

        /**
         * This method returns the Actor's flags.
         *
         * @return The Actor's flags.
         */
        uint32_t getFlags();

        /**
         * This method returns the Actor's raid flags.
         *
         * @return The Actor's raid flags.
         */
        uint32_t getRaidFlags();

    protected:
        /**
         * A basic constructor that initialises internal variables.
         *
         * @param index     This actor's index.
         * @param guid      This Actor's GUID.
         * @param name      The name of the Actor.
         * @param flags     An integral representation of bit-flags that
         *                  represent this Actor's state.
         * @param raidFlags An integral representation of bit-flags that
         *                  represent the Actor's raid status.
         */
        Actor(uint32_t    index,
              uint64_t    guid,
              std::string name,
              uint32_t    flags,
              uint32_t    raidFlags);

    private:
        static std::map<std::string, Actor> actors;    /**< A mapping of
                                                        *   Actor information
                                                        *   to Actor object,
                                                        *   containing Actors
                                                        *   that have already
                                                        *   been created. */
        static std::list<Actor*>            actorList; /**< A list of Actor
                                                        *   pointers
                                                        *   containing Actors
                                                        *   in the order in
                                                        *   which they were
                                                        *   encountered. */
        static uint32_t                     lastIndex; /**< The index of the
                                                        *   last-added actor
                                                        *   within the actors
                                                        *   map. */
        uint32_t                            index;     /**< The index of this
                                                        *   current actor
                                                        *   within the actors
                                                        *   map. */
        uint64_t                            guid;      /**< The Actor's GUID.
                                                        */
        std::string                         name;      /**< The name of the
                                                        *   Actor. */
        uint32_t                            flags;     /**< An integral
                                                        *   representation of
                                                        *   bit-flags that
                                                        *   represent this
                                                        *   Actor's state. */
        uint32_t                            raidFlags; /**< An integral
                                                        *   representation of
                                                        *   bit-flags that
                                                        *   represent the
                                                        *   Actor's raid
                                                        *   status. */
    };

    /**
     * This class represents a combat log Event.
     */
    class Event
    {
    public:
        /**
         * A basic, empty, constructor.
         */
        Event();

        /**
         * A basic factory method that sets up an Event from passed data and
         * returns a pointer to it.
         *
         * @param type A string containing the type of this Event.
         * @param data A list of arbitrary string values that this Event
         *             shall parse and contain.
         */
        static Event * factory(std::string            type,
                               std::list<std::string> data);

        /**
         * This method returns a list containing pointers to every Event that
         * took place in the combat log.
         *
         * @return A list containing pointers to every Event that took place
         *         in the combat log.
         */
        static std::list<Event*> getEvents();

        /**
         * This method returns the identifier of this event.
         *
         * @return The identifier of this event.
         */
        uint32_t getId();

        /**
         * This method returns a string containing the type of this event.
         *
         * @return A string containing the type of this event.
         */
        std::string getType();

        /**
         * This method returns a list strings containing the data that have
         * been assigned to this event.
         *
         * @return This method returns a list strings containing the data
         *         that have been assigned to this event.
         */
        std::list<std::string> getData();

    protected:
        /**
         * A basic constructor that initialises internal variables.
         *
         * @param id   The identifier of this event.
         * @param type A string containing the type of this Event.
         * @param data A list of arbitrary string values that this Event
         *             shall parse and contain.
         */
        Event(uint32_t id, std::string type, std::list<std::string> data);

    private:
        static std::map<std::string, Event> eventMap;  /**< A map of event
                                                        *   key to Event
                                                        *   object pointer,
                                                        *   containing
                                                        *   Event%s that
                                                        *   have been
                                                        *   created. */
        static std::list<Event*>            eventList; /**< A list of
                                                        *   pointers to
                                                        *   Events in the
                                                        *   order that they
                                                        *   were encountered.
                                                        *   */
        static uint32_t                     currentId; /**< The identifier
                                                        *   of the next new
                                                        *   event. */

        uint32_t               id;       /** The identifier of this event.
                                          */
        std::string            type ;    /**< A string containing
                                          *   the type of this event. */
        std::list<std::string> dataList; /**< A list strings containing the
                                          *   data that have been assigned to
                                          *   this event. */
    };

    /**
     * This class contains subject information.
     */
    class SubjectInfo
    {
    public:
        /**
         * A basic factory method that sets up a SubjectInfo and returns a
         * pointer to it. It is the responsibility of the caller to delete
         * the returned pointer.
         *
         * @param guid           The GUID of the SubjectInfo.
         * @param health         The subject's health.
         * @param attackPower    The subject's attack power.
         * @param spellPower     The subject's spell power.
         * @param resoureType    The type of resource that the subject
         *                       consumed.
         * @param resourceAmount The amount of resource that the subject
         *                       used.
         * @param posX           The subject's X coordinate.
         * @param posY           The subject's Y coordinate.
         */
        static SubjectInfo * factory(std::string guid,
                                     std::string health,
                                     std::string attackPower,
                                     std::string spellPower,
                                     std::string resourceType,
                                     std::string resourceAmount,
                                     std::string posX,
                                     std::string posY);

    protected:
        /**
         * A basic constructor that initialises internal variables.
         *
         * @param guid           The GUID of the SubjectInfo.
         * @param health         The subject's health.
         * @param attackPower    The subject's attack power.
         * @param spellPower     The subject's spell power.
         * @param resoureType    The type of resource that the subject
         *                       consumed.
         * @param resourceAmount The amount of resource that the subject
         *                       used.
         * @param posX           The subject's X coordinate.
         * @param posY           The subject's Y coordinate.
         */
        SubjectInfo(uint64_t guid,
                    uint32_t health,
                    uint32_t attackPower,
                    uint32_t spellPower,
                    uint32_t resourceType,
                    uint32_t resourceAmount,
                    double   posX,
                    double   posY);

    private:
        uint64_t guid;           /**< The GUID of the SubjectInfo. */
        uint32_t health;         /**< The subject's health. */
        uint32_t attackPower;    /**< The subject's attack power. */
        uint32_t spellPower;     /**< The subject's spell power. */
        uint32_t resourceType;   /**< The type of resource that the subject
                                  *   consumed. */
        uint32_t resourceAmount; /**< The amount of resource that the
                                  *   subject used. */
        double   posX;           /**< The subject's X coordinate. */
        double   posY;           /**< The subject's Y coordinate. */
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

        /**
         * This method returns a pointer to this line's Source actor.
         *
         * @return A pointer to this line's Source actor.
         */
        Actor * getSourceActor();

        /**
         * This method returns a pointer to this line's Destination actor.
         *
         * @return A pointer to this line's Destination actor.
         */
        Actor * getDestinationActor();

        /**
         * This method returns the timestamp of this line.
         *
         * @return The timestamp of this line.
         */
        boost::posix_time::ptime getTimestamp();

        /**
         * This method returns a pointer to this line's Event.
         *
         * @return A pointer to this line's Event.
         */
        Event * getEvent();

    protected:
        /**
         * A basic constructor.
         * @param timestamp   This line's timestamp.
         * @param source      A pointer to the source actor.
         * @param destination A pointer to the destination actor.
         * @param event       A pointer to the event that this line
         *                    describes.
         * @param info        A pointer to an object containing subject
         *                    information relating to this line.
         */
        CombatLogLine(boost::posix_time::ptime  timestamp,
                      Actor                    *source,
                      Actor                    *destination,
                      Event                    *event,
                      SubjectInfo              *info);

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

        boost::posix_time::ptime  timestamp;   /**< This line's timestamp. */
        Actor                    *source;      /**< A pointer to the source
                                                *   actor. */
        Actor                    *destination; /**< A pointer to the
                                                *   destination actor. */
        Event                    *event;       /**< The event that this line
                                                *   describes. */
        SubjectInfo              *info;        /**< Subject information
                                                *   relating to this line. */
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

        /**
         * This method returns a list of unique Actor%s present within the
         * Combat Log.
         *
         * @return A list pointers to Actor objects representing every Actor
         *         that took part in the combat log.
         */
        std::list<Actor*> getActors();

        /**
         * This method returns a list of unique Events present within the
         * Combat Log.
         *
         * @return A list pointers to Events objects representing every Event
         *         that happened in the combat log.
         */
        std::list<Event*> getEvents();

        /**
         * This method returns an ordered list of pointers CombatLogLine%s,
         * representing the combat log.
         *
         * @return An ordered list of CombatLogLine%s, representing the
         *         combat log.
         */
        std::list<CombatLogLine*> getLines();

    private:
        std::list<CombatLogLine*> lines; /**< An ordered list of
                                          *   CombatLogLine%s, representing
                                          *   the contents of the combat log.
                                          */
    };
}

#endif
