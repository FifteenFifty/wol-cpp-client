/**
 * @file   combat-log-structures.cpp
 *
 * @brief  This file contains the implementation of basic combat log
 *         structures.
 *
 * @author Matthew Bissenden
 */

#include <combat-log-structures.hpp>
#include <sstream>

using namespace WoL::Utils;

namespace WoL
{
    std::map<std::string, Actor> Actor::actors;

    Actor * Actor::factory(std::string guid,
                           std::string name,
                           std::string flags,
                           std::string raidFlags)
    {
        std::string actorKey = guid + name + flags + raidFlags;

        if (actors.find(actorKey) == actors.end())
        {
            actors[actorKey] = Actor(StringUtils::parseHex<uint64_t>(guid),
                                     StringUtils::parseString(name),
                                     StringUtils::parseHex<uint32_t>(flags),
                                     StringUtils::parseHex<uint32_t>(raidFlags));
        }

        return &actors[actorKey];
    }

    Actor::Actor()
    :
    guid(guid),
    name(name),
    flags(flags),
    raidFlags(raidFlags)
    {
    }

    Actor::Actor(uint64_t    guid,
                 std::string name,
                 uint32_t    flags,
                 uint32_t    raidFlags)
    :
    guid(guid),
    name(name),
    flags(flags),
    raidFlags(raidFlags)
    {
    }

    Event::Event(uint32_t    id,
                 std::string type,
                 std::string data)
    :
    id(id),
    type(),
    dataList()
    {
        //TODO - Add the typeString to a set of unique strings (unique data
        //       structures?), and set the reference.
        //TODO - Parse the data list and save it.
    }

    std::string Event::toString()
    {
        std::stringstream                toReturn;
        std::list<std::string>::iterator dataIt;

        toReturn << "<Event "
                 << id
                 << ", "
                 << type
                 << ", [";

        dataIt = dataList.begin();

        if (dataIt != dataList.end())
        {
            toReturn << *(dataIt++);
        }

        for (; dataIt != dataList.end(); ++dataIt)
        {
            toReturn << ", "
                     << *dataIt;
        }

        toReturn << "]>";

        return toReturn.str();
    }

    CombatLogLine * CombatLogLine::factory(std::string line)
    {
        size_t dateLength   = line.find("  ");
        Actor  *source      = NULL;
        Actor  *destination = NULL;
        Event  *event       = NULL;
        size_t  consumable  = 0;

        std::string timestamp;

        if (dateLength == std::string::npos)
        {
            /**
             * @TODO This error should be handled.
             *       MLB 25/01/2014
             */
            return NULL;
        }

        timestamp = line.substr(0, dateLength);
        line      = line.substr(dateLength + 2, line.length());

        std::list<std::string>           data   = Utils::StringUtils::parseCsv(line);
        std::list<std::string>::iterator dataIt = data.begin();

        consumable = data.size();

        if (dataIt == data.end())
        {
            /**
             * @TODO No data has been parsed for this event. This error
             *       should be handled.
             *       MLB 25/01/2014
             */
            return NULL;
        }

        std::string eventType = *(dataIt++);
        --consumable;

        // TODO Check that the event type is legit before continuing

        //If the next item is a hex number, we've found an actor
        if (dataIt->substr(0, 2) == "0x")
        {
            if (consumable >= 8)
            {
                /**
                 * @TODO There are not enough elements for the following
                 *       Actors to be created. This is an error.
                 *       MLB 06/01/2014
                 */
                return NULL;
            }

            source = Actor::factory(*(dataIt++),
                                    *(dataIt++),
                                    *(dataIt++),
                                    *(dataIt++));

            destination = Actor::factory(*(dataIt++),
                                         *(dataIt++),
                                         *(dataIt++),
                                         *(dataIt++));
            consumable -= 8;
        }

        if (consumable > 0)
        {
            /* There is also Event information. */

           event = Event::Factory(
        }

        return NULL;
    }

    CombatLogLine::CombatLogLine()
    :
    timestamp(),
    source(NULL),
    destination(NULL),
    event(NULL)
    {
    }

    CombatLogLine::CombatLogLine(const CombatLogLine &copy)
    :
    timestamp(),
    source(NULL),
    destination(NULL),
    event(NULL)
    {
        //TODO - throw a ForbiddenMEthodCallException.. And create an
        //error-handling mechanism
    }

    const CombatLogLine & CombatLogLine::operator=(const CombatLogLine ass)
    {
        //TODO - throw a ForbiddenMEthodCallException.. And create an
        //error-handling mechanism
    }

    CombatLog::CombatLog()
    :
    lines()
    {
    }

    void CombatLog::addLine(CombatLogLine *line)
    {
        if (line)
        {
            lines.push_back(line);
        }
        else
        {
            /**
             * @TODO This error should be handled.
             *       MLB 24/01/2014
             */
        }
    }
}
