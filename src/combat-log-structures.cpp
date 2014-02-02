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
#include <boost/regex.hpp>

using namespace WoL::Utils;

namespace WoL
{
    std::map<std::string, Actor> Actor::actors;
    std::map<std::string, Event> Event::eventMap;
    uint32_t                     Event::currentId;

    Actor * Actor::factory(std::string guid,
                           std::string name,
                           std::string flags,
                           std::string raidFlags)
    {
        std::string actorKey = guid + flags + raidFlags;

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

    Event * Event::factory(std::string            type,
                           std::list<std::string> data)
    {
        std::list<std::string>::iterator        dataIt;
        std::map<std::string, Event>::iterator  eventMapIt;
        Event                                  *toReturn  = NULL;
        std::string                             keyString = type;

        for (dataIt = data.begin(); dataIt != data.end(); ++dataIt)
        {
            keyString += *dataIt;
        }

        if ((eventMapIt = eventMap.find(keyString)) == eventMap.end())
        {
            eventMap[keyString] = Event(currentId++, type, data);
        }

        return &eventMap[keyString];
    }

    Event::Event()
    :
    id(),
    type(),
    dataList()
    {
    }

    Event::Event(uint32_t               id,
                 std::string            type,
                 std::list<std::string> data)
    :
    id(id),
    type(),
    dataList(data)
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

    SubjectInfo * SubjectInfo::factory(std::string guid,
                                       std::string health,
                                       std::string attackPower,
                                       std::string spellPower,
                                       std::string resourceType,
                                       std::string resourceAmount,
                                       std::string posX,
                                       std::string posY)
    {
        return new SubjectInfo(StringUtils::parseHex<uint64_t>(guid),
                               StringUtils::parseInt<uint32_t>(health),
                               StringUtils::parseInt<uint32_t>(attackPower),
                               StringUtils::parseInt<uint32_t>(spellPower),
                               StringUtils::parseInt<uint32_t>(resourceType),
                               StringUtils::parseInt<uint32_t>(resourceAmount),
                               StringUtils::parseFloat(posX),
                               StringUtils::parseFloat(posY));
    }

    SubjectInfo::SubjectInfo(uint64_t guid,
                             uint32_t health,
                             uint32_t attackPower,
                             uint32_t spellPower,
                             uint32_t resourceType,
                             uint32_t resourceAmount,
                             double   posX,
                             double   posY)
    :
    guid(guid),
    health(health),
    attackPower(attackPower),
    spellPower(spellPower),
    resourceType(resourceType),
    resourceAmount(resourceAmount),
    posX(posX),
    posY(posY)
    {
    }

    CombatLogLine * CombatLogLine::factory(std::string line)
    {
        size_t       dateLength  = line.find("  ");
        Actor       *source      = NULL;
        Actor       *destination = NULL;
        Event       *event       = NULL;
        size_t       consumable  = 0;
        SubjectInfo *info        = NULL;

        std::string  timestamp;
        boost::regex subjectInfo54Regex("(?<=,)0x[0-9A-Fa-f]{16}(?:,-?\\d+){5}(?:,-?\\d*\\.\\d+){2}");
        std::string  subjectInfo;

        /*
         * After the double-space is  a comma-separated list of data;
         * 1: eventType (can be matched vs a regex (named eventType))
         * (if 2 is a hex number (0x1550), unit fields are present)
         * 2: sourceGuid (hex long)
         * 3: sourceName (string)
         * 4: sourceFlags (hex int)
         * 5: sourceRaidFlags (hex int)
         * 6: destGuid (hex long)
         * 7: destName (string)
         * 8: destFlags (int)
         * 9: destRaidFlags (hex int)
         * (If present, the following is event data)
         * 10:
         * 6?: if (sr.nextIsString()) return com.wol3.client.data.BinaryCombatLog.Format.BASE
         */
        if (dateLength == std::string::npos)
        {
            /**
             * @TODO This error should be handled.
             *       MLB 25/01/2014
             */
            return NULL;
        }

        /* Remove subject info if any exists. */
        boost::match_results<std::string::const_iterator> results;

        if (boost::regex_search(line, results, subjectInfo54Regex))
        {
            if (!results.empty())
            {
                std::list<std::string>           infoList;
                std::list<std::string>::iterator infoListIt;

                infoList   = Utils::StringUtils::parseCsv(results[0]);
                infoListIt = infoList.begin();

                if (infoList.size() != 8)
                {
                    /**
                     * @TODO A subjectInfo that is not supported has been
                     *       matched! Handle this error.
                     *       MLB 02/02/2014
                     */
                    std::cerr << "A subjectInfo that is not supported has "
                              << "been matched! (size = "
                              << infoList.size()
                              << ")"
                              << std::endl;
                    return NULL;
                }

                info = SubjectInfo::factory(*(infoListIt++),
                                            *(infoListIt++),
                                            *(infoListIt++),
                                            *(infoListIt++),
                                            *(infoListIt++),
                                            *(infoListIt++),
                                            *(infoListIt++),
                                            *(infoListIt));

                /* Remove the subjectInfo. */
                boost::regex_replace(line, subjectInfo54Regex, "");
            }
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

           event = Event::factory(eventType,
                                  std::list<std::string>(dataIt,
                                                         data.end()));
        }

        return new CombatLogLine(timestamp,
                                 soure,
                                 destination,
                                 event,
                                 info);
    }

    std::string CombatLogLine::toString()
    {
        std::string toReturn;

        toReturn += timestamp;

        if (source)
        {
            toReturn += source->toString();
        }
        if (destination)
        {
            toReturn += destination.toString();
        }
        if (event)
        {
            toReturn += event.toString();
        }
        if (info)
        {
            toReturn += info.toString();
        }

        return toReturn;
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

    std::string CombatLog::toString()
    {
        std::list<CombatLogLine*>::iterator lineIt;
        std::string                         toReturn;

        for (lineIt = lines.begin(); lineIt != lines.end(); ++lineIt)
        {
            if (!*lineIt)
            {
                /**
                 * @TODO A NULL CombatLogLine has been encountered! Handle
                 *       this error.
                 *       MLB 02/02/2014
                 */
                return "ERROR";
            }

            toReturn += (*lineIt)->toString();
        }

        return toReturn;
    }
}
