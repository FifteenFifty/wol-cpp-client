/**
 * @file   wol-combat-log-formatter.cpp
 *
 * @brief  This file contains the implementation of the World of Logs combat
 *         log formatter.
 *
 * @author Matthew Bissenden
 */

#include <wol-combat-log-formatter.hpp>
#include <iostream>
#include <boost/bimap.hpp>

namespace WoL
{
    WolCombatLogFormatter::~WolCombatLogFormatter()
    {
    }

    FormattedCombatLog * WolCombatLogFormatter::formatLog(CombatLog &combatLog)
    {
        /*
         * - Magic number
         * - Properties (datetime, client version, timezone, format)
         * - Actors:
         *
         * - Events
         *      - aeaeaeaeaeaeaeae MAGIC NUMBER
         *      - The index from which Events are being sent
         *      - Event pool:
         *          -
         */

        FormattedCombatLog *log = new FormattedCombatLog();

        addMagicNumber(log);
        addActorInfo(combatLog, log);
        addEventInfo(combatLog, log);

        return log;
    }

    void WolCombatLogFormatter::addMagicNumber(FormattedCombatLog *formattedLog)
    {
        formattedLog->add(0x00001fb902131029);
    }

    void WolCombatLogFormatter::addPropertyInfo(FormattedCombatLog *formattedLog)
    {
        //TODO
    }

    void WolCombatLogFormatter::addActorInfo(CombatLog          &combatLog,
                                             FormattedCombatLog *formattedLog)
    {
        /*
         * - Actors:
         *      - ff00ff00ff00ff00 MAGIC NUMBER
         *      - The index from which the Actor pool is being uploaded
         *      - Actor pool:
         *          - Number of names
         *          - Actor names (name length & name)
         *          - Number of GUIDs
         *          - Actor guids separated for newlines
         *          - Actor identifiers (as indexed from the Actor list)
         *          - Actor flags
         *          - IF raidflags exist)
         *              - Actor raidflags
         */
        std::list<Actor*>           actors     = combatLog.getActors();
        uint32_t                    actorCount = actors.size();
        std::list<Actor*>::iterator actorIt;
        FormattedCombatLog          formattedFragment;

        formattedLog->add(0xff00ff00ff00ff00);

        // The start index. It is currently zero
        formattedLog->add((uint32_t) 0);

        for (int i = 0; i <= 4; ++i)
        {
            formattedFragment.clear();

            formattedFragment.add(actorCount);

            for (actorIt = actors.begin(); actorIt != actors.end(); ++actorIt)
            {
                switch (i)
                {
                    case 0:
                        formattedFragment.add((uint16_t) (*actorIt)->getName().length());
                        formattedFragment.add((*actorIt)->getName());
                        break;

                    case 1:
                        formattedFragment.add((*actorIt)->getGuid());
                        break;

                    case 2:
                        formattedFragment.add((*actorIt)->getIndex());
                        break;

                    case 3:
                        formattedFragment.add((*actorIt)->getFlags());
                        break;

                    case 4:
                        formattedFragment.add((*actorIt)->getRaidFlags());
                        break;

                    default:
                        /**
                         * @TODO This is an error condition and should be
                         *       handled as such.
                         *       MLB 08/02/2014
                         */
                        std::cerr << "Reached default case of Actor "
                                     "formatting."
                                  << std::endl;
                }
            }

            if (i == 0)
            {
                formattedLog->add(formattedFragment.size());
            }
            formattedLog->add(formattedFragment);
        }
    }

    void WolCombatLogFormatter::addEventInfo(CombatLog          &combatLog,
                                             FormattedCombatLog *formattedLog)
    {
        /*
         * Events:
         *      - aeaeaeaeaeaeaeae MAGIC NUMBER
         *      - The index from which Events are being sent
         *      - Event pool:
         *          - For each type of int (u8, u16, u32)
         *              - The number of integers present that fit into that
         *                data type but none below it
         *              - The integers in encountered-order
         *          -
         *
         */
        std::list<Event*>                events     = combatLog.getEvents();
        uint32_t                         eventCount = events.size();
        std::list<Event*>::iterator      eventIt;
        FormattedCombatLog               formattedFragment;
        std::list<std::string>           eventDataList;
        std::list<std::string>::iterator eventDataListIt;

        uint32_t numericData;

        std::list<uint8_t>                                       byteList;
        std::list<uint16_t>                                      shortList;
        std::list<uint32_t>                                      intList;
        uint16_t                                                 stringIndex = 0;
        boost::bimap<uint16_t, std::string>                      stringMap;
        boost::bimap<uint16_t, std::string>::right_map::iterator stringMapRightIt;
        boost::bimap<uint16_t, std::string>::left_map::iterator  stringMapLeftIt;
        std::list<uint16_t>                                      stringIndexList;
        uint8_t                                                  eventTypeIndex = 0;
        boost::bimap<uint8_t, std::string>                       eventTypeMap;
        boost::bimap<uint8_t, std::string>::right_map::iterator  eventTypeRightMapIt;
        boost::bimap<uint8_t, std::string>::left_map::iterator   eventTypeLeftMapIt;
        std::list<uint8_t>                                       eventTypeIndexList;

        /* The magic string seems to notify WoL of the data that is contained
         * within an element */
        std::list<std::string>           magicStringList;
        std::list<std::string>::iterator magicStringListIt;
        std::string                      magicString;

        formattedLog->add(0xaeaeaeaeaeaeaeae);

        // The start index. It is currently zero
        formattedLog->add((uint32_t) 0);

        for (eventIt = events.begin(); eventIt != events.end(); ++eventIt)
        {
            eventDataList = (*eventIt)->getData();
            magicString   = "";

            for (eventDataListIt = eventDataList.begin();
                 eventDataListIt != eventDataList.end();
                 ++eventDataListIt)
            {
                //TODO - A better 'nil' check would be super useful
                if (*eventDataListIt == "nil")
                {
                    magicString += "n";
                    continue;
                }

                try
                {
                    numericData = Utils::Conversion::lexicalCast<std::string,
                                                                 uint32_t>(
                                                        *eventDataListIt);
                }
                catch (boost::bad_lexical_cast e)
                {
                    magicString += "t";

                    if ((stringMapRightIt = stringMap.right.find(*eventDataListIt)) == stringMap.right.end())
                    {
                        stringIndexList.push_back(stringIndex);
                        stringMap.insert(boost::bimap<uint16_t,
                                                      std::string>::value_type( stringIndex++,
                                                                               *eventDataListIt));
                    }
                    else
                    {
                        stringIndexList.push_back(stringMapRightIt->second);
                    }
                }

                switch (numericData)
                {
                    case 0:
                        magicString += "0";
                        break;

                    case 1:
                        magicString += "1";
                        break;

                    case 2:
                        magicString += "!";
                        break;

                    case 4:
                        magicString += "@";
                        break;

                    case 8:
                        magicString += "#";
                        break;

                    case 16:
                        magicString += "$";
                        break;

                    case 32:
                        magicString += "%";
                        break;

                    case 64:
                        magicString += "^";
                        break;

                    default:
                        if (numericData <= 255)
                        {
                            byteList.push_back((uint8_t) numericData);
                        }
                        else if (numericData <= 65535)
                        {
                            shortList.push_back((uint16_t) numericData);
                        }
                        else
                        {
                            intList.push_back(numericData);
                        }
                        break;
                }
            }

            magicStringList.push_back(magicString);

            if ((eventTypeRightMapIt = eventTypeMap.right.find((*eventIt)->getType())) == eventTypeMap.right.end())
            {
                eventTypeIndexList.push_back(eventTypeIndex);
                eventTypeMap.insert(boost::bimap<uint8_t,
                                                 std::string>::value_type( eventTypeIndex++,
                                                                          (*eventIt)->getType()));
            }
            else
            {
                eventTypeIndexList.push_back(eventTypeRightMapIt->second);
            }
        }

        // 32bit int -> Number of u8 following
        formattedLog->add((uint32_t) byteList.size());
        formattedLog->add(byteList);

        // 32bit int -> Number of u16 following
        formattedLog->add((uint32_t) shortList.size());
        formattedLog->add(shortList);

        // 32bit int -> Number of u32 following
        formattedLog->add((uint32_t) shortList.size());
        formattedLog->add(intList);

        // 32but int -> Number of string indices (u16) following
        formattedLog->add((uint32_t) stringIndexList.size());
        formattedLog->add(stringIndexList);

        // 32bit int -> Length of strings section (Done via fragment)
        formattedFragment.clear();
        for (stringMapLeftIt = stringMap.left.begin();
             stringMapLeftIt != stringMap.left.end();
             ++stringMapLeftIt)
        {
            formattedFragment.add((uint16_t) stringMapLeftIt->second.length());
            formattedFragment.add(stringMapLeftIt->second);
        }

        formattedLog->add((uint32_t) formattedFragment.size());
        formattedLog->add(formattedFragment);

        // 32bit int -> Number of type indices (u16) following
        formattedLog->add((uint32_t) eventCount);
        for (uint16_t i = 0; i < eventCount; ++i)
        {
            formattedLog->add(i);
        }

        // 32bit int -> Length of Type Section // 32bit int -> Length of
        // Type section (done via fragment)
        formattedFragment.clear();
        for (magicStringListIt = magicStringList.begin();
             magicStringListIt != magicStringList.end();
             ++magicStringListIt)
        {
            formattedFragment.add((uint16_t) magicStringListIt->length());
            formattedFragment.add(*magicStringListIt);
        }

        formattedLog->add((uint32_t) formattedFragment.size());
        formattedLog->add(formattedFragment);

        // 32bit int -> Number of event type indices (u8) following
        formattedLog->add((uint32_t) eventTypeIndexList.size());
        formattedLog->add(eventTypeIndexList);

        // 32bit int -> Length of event type string section (done via
        // fragment)
        formattedFragment.clear();
        for (eventTypeLeftMapIt = eventTypeMap.left.begin();
             eventTypeLeftMapIt != eventTypeMap.left.end();
             ++eventTypeLeftMapIt)
        {
            formattedFragment.add((uint16_t) eventTypeLeftMapIt->second.length());
            formattedFragment.add(eventTypeLeftMapIt->second);
        }

        formattedLog->add((uint32_t) formattedFragment.size());
        formattedLog->add(formattedFragment);
    }
}