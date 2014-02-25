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
    std::list<Actor*>            Actor::actorList;
    std::map<std::string, Event> Event::eventMap;
    std::list<Event*>            Event::eventList;
    uint32_t                     Actor::lastIndex = 0;
    uint32_t                     Event::currentId = 0;

    Actor * Actor::factory(std::string guid,
                           std::string name,
                           std::string flags,
                           std::string raidFlags)
    {
        if (name == "nil")
        {
            return NULL;
        }

        std::string actorKey = guid + flags + raidFlags;

        if (actors.find(actorKey) == actors.end())
        {
            Actor actor(lastIndex++,
                        StringUtils::parseHex<uint64_t>(guid),
                        StringUtils::parseString(name),
                        StringUtils::parseHex<uint32_t>(flags),
                        StringUtils::parseHex<uint32_t>(raidFlags));

            actors[actorKey] = actor;
            actorList.push_back(&actors[actorKey]);
        }

        return &actors[actorKey];
    }

    Actor::Actor()
    :
    index(0),
    guid(),
    name(),
    flags(),
    raidFlags()
    {
    }

    Actor & Actor::operator=(const Actor &ass)
    {
        this->index     = ass.index;
        this->guid      = ass.guid;
        this->name      = ass.name;
        this->flags     = ass.flags;
        this->raidFlags = ass.raidFlags;

        return *this;
    }

    std::list<Actor*> Actor::getActors()
    {
        return actorList;
    }

    uint32_t Actor::getIndex()
    {
        return index;
    }

    uint64_t Actor::getGuid()
    {
        return guid;
    }

    std::string Actor::getName()
    {
        return name;
    }

    uint32_t Actor::getFlags()
    {
        return flags;
    }

    uint32_t Actor::getRaidFlags()
    {
        return raidFlags;
    }

    Actor::Actor(uint32_t    index,
                 uint64_t    guid,
                 std::string name,
                 uint32_t    flags,
                 uint32_t    raidFlags)
    :
    index(index),
    guid(guid),
    name(name),
    flags(flags),
    raidFlags(raidFlags)
    {
    }

    Event * Event::factory(std::string            type,
                           std::list<std::string> data)
    {
        std::list<std::string>::iterator       dataIt;
        std::map<std::string, Event>::iterator eventMapIt;
        std::string                            keyString = type;

        for (dataIt = data.begin(); dataIt != data.end(); ++dataIt)
        {
            keyString += *dataIt;
        }

        if ((eventMapIt = eventMap.find(keyString)) == eventMap.end())
        {
            eventMap[keyString] = Event(currentId++, type, data);
            eventList.push_back(&eventMap[keyString]);
        }

        return &eventMap[keyString];
    }

    std::list<Event*> Event::getEvents()
    {
        return eventList;
    }

    uint32_t Event::getId()
    {
        return id;
    }

    std::string Event::getType()
    {
        return type;
    }

    std::list<std::string> Event::getData()
    {
        return dataList;
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
    type(type),
    dataList(data)
    {
        //TODO - Add the typeString to a set of unique strings (unique data
        //       structures?), and set the reference.
        //TODO - Parse the data list and save it.
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

        boost::posix_time::ptime timestamp;
        boost::regex             subjectInfo54Regex("(?<=,)0x[0-9A-Fa-f]{16}(?:,-?\\d+){5}(?:,-?\\d*\\.\\d+){2}");
        std::string              subjectInfo;

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
            std::cout << "No date length found"<<std::endl;
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

                std::string guid(*(infoListIt++));
                std::string health(*(infoListIt++));
                std::string attackPower(*(infoListIt++));
                std::string spellPower(*(infoListIt++));
                std::string resourceType(*(infoListIt++));
                std::string resourceAmount(*(infoListIt++));
                std::string posX(*(infoListIt++));
                std::string posY(*(infoListIt++));

                info = SubjectInfo::factory(guid,
                                            health,
                                            attackPower,
                                            spellPower,
                                            resourceType,
                                            resourceAmount,
                                            posX,
                                            posY);

                /* Remove the subjectInfo. */
                boost::regex remove54Regex(",?0x[0-9A-Fa-f]{16}(?:,-?\\d+){5}(?:,-?\\d*\\.\\d+){2}");
                line = boost::regex_replace(line, remove54Regex, "");
            }
        }

        boost::posix_time::time_input_facet *tif =
                    new boost::posix_time::time_input_facet("%Y/%m/%d %H:%M:%S%f");

        std::stringstream timeConverter;

        timeConverter.imbue(std::locale(timeConverter.getloc(), tif));
        timeConverter.exceptions(std::ios_base::failbit);


        timeConverter << "2014/" << line.substr(0, dateLength);
        timeConverter >> timestamp;

        line      = line.substr(dateLength + 2, line.length());

        std::list<std::string>           data   = Utils::StringUtils::parseCsv(line);
        std::list<std::string>::iterator dataIt = data.begin();

        dataIt = data.begin();
        consumable = data.size();

        if (dataIt == data.end())
        {
            /**
             * @TODO No data has been parsed for this event. This error
             *       should be handled.
             *       MLB 25/01/2014
             */
            std::cout << "No data has been parsed :("<<std::endl;
            return NULL;
        }

        std::string eventType = *(dataIt++);
        --consumable;

        // TODO Check that the event type is legit before continuing

        //If the next item is a hex number, we've found an actor
        if (dataIt->substr(0, 2) == "0x")
        {
            if (consumable < 8)
            {
            std::cout << "Not enough data for our actors :("<<std::endl;
                /**
                 * @TODO There are not enough elements for the following
                 *       Actors to be created. This is an error.
                 *       MLB 06/01/2014
                 */
                return NULL;
            }

            /* Due to the order of argument evaluation being undefined in the
             * C++ standard, the iterator is dereferenced into intermediary
             * strings before being passed into the factory. */
            std::string guid      = *(dataIt++);
            std::string name      = *(dataIt++);
            std::string flags     = *(dataIt++);
            std::string raidFlags = *(dataIt++);

            source = Actor::factory(guid, name, flags, raidFlags);

            guid      = *(dataIt++);
            name      = *(dataIt++);
            flags     = *(dataIt++);
            raidFlags = *(dataIt++);

            destination = Actor::factory(guid, name, flags, raidFlags);
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
                                 source,
                                 destination,
                                 event,
                                 info);
    }

    Actor * CombatLogLine::getSourceActor()
    {
        return source;
    }

    Actor * CombatLogLine::getDestinationActor()
    {
        return destination;
    }

    boost::posix_time::ptime CombatLogLine::getTimestamp()
    {
        return timestamp;
    }

    CombatLogLine::CombatLogLine(boost::posix_time::ptime  timestamp,
                                 Actor                    *source,
                                 Actor                    *destination,
                                 Event                    *event,
                                 SubjectInfo              *info)
    :
    timestamp(timestamp),
    source(source),
    destination(destination),
    event(event),
    info(info)
    {
    }

    CombatLogLine::CombatLogLine(const CombatLogLine &copy)
    :
    timestamp(),
    source(NULL),
    destination(NULL),
    event(NULL),
    info(NULL)
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

    std::list<Actor*> CombatLog::getActors()
    {
        return Actor::getActors();
    }

    std::list<Event*> CombatLog::getEvents()
    {
        return Event::getEvents();
    }

    std::list<CombatLogLine*> CombatLog::getLines()
    {
        return lines;
    }
}
