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

namespace WoL
{
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

    CombatLog::CombatLog()
    :
    lines()
    {
    }
}
