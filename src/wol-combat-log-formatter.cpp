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
         */
        std::list<Event*>           events     = combatLog.getEvents();
        uint32_t                    eventCount = events.size();
        std::list<Event*>::iterator eventIt;
        FormattedCombatLog          formattedFragment;

        formattedLog->add(0xaeaeaeaeaeaeaeae);

        // The start index. It is currently zero
        formattedLog->add((uint32_t) 0);

        for (int i = 0; i <= 4; ++i)
        {
            formattedFragment.clear();

            formattedFragment.add(eventCount);

            for (eventIt = events.begin(); eventIt != events.end(); ++eventIt)
            {
                switch (i)
                {
                }
            }

            if (i == 0)
            {
                formattedLog->add(formattedFragment.size());
            }
            formattedLog->add(formattedFragment);
        }
    }

}
