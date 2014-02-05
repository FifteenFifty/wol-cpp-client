/**
 * @file   wol-combat-log-formatter.cpp
 *
 * @brief  This file contains the implementation of the World of Logs combat
 *         log formatter.
 *
 * @author Matthew Bissenden
 */

#include <wol-combat-log-formatter.hpp>

namespace WoL
{
    WolCombatLogFormatter::~WolCombatLogFormatter()
    {
    }

    std::string WolCombatLogFormatter::formatLog(CombatLog &combatLog)
    {
        /*
         * - Magic number
         * - Properties (datetime, client version, timezone, format)
         * - Actors:
         *      - ff00ff00ff00ff00 MAGIC NUMBER
         *      - The index from which the Actor pool is being uploaded
         *      - Actor pool:
         *          - Actor names separated by newlines
         *          - Actor guids separated for newlines
         *          - Actor identifiers (as indexed from the Actor list)
         *          - Actor flags
         *          - IF raidflags exist)
         *              - Actor raidflags
         *
         * - Events
         *      - aeaeaeaeaeaeaeae MAGIC NUMBER
         *      - The index from which Events are being sent
         *      - Event pool:
         *          -
        return "";
    }
}
