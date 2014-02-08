/**
 * @file   combat-log-formatter.cpp
 *
 * @brief  This file contains the implementation of the combat log formatter.
 *
 * @author Matthew Bissenden
 */

#include <combat-log-formatter.hpp>
#include <iostream>
#include <cstdio>

namespace WoL
{
    FormattedCombatLog::FormattedCombatLog()
    :
    byteBuffer()
    {
    }

    template <>
    void FormattedCombatLog::add(uint8_t toAdd)
    {
        byteBuffer.push_back(toAdd);
    }

    template <>
    void FormattedCombatLog::add(uint16_t toAdd)
    {
        byteBuffer.push_back((toAdd >> 8 & 0xFF));
        byteBuffer.push_back((toAdd & 0xFF));
    }

    template <>
    void FormattedCombatLog::add(uint32_t toAdd)
    {
        byteBuffer.push_back((toAdd >> 24 & 0xFF));
        byteBuffer.push_back((toAdd >> 16 & 0xFF));
        byteBuffer.push_back((toAdd >> 8 & 0xFF));
        byteBuffer.push_back((toAdd & 0xFF));
    }

    template <>
    void FormattedCombatLog::add(uint64_t toAdd)
    {
        byteBuffer.push_back((toAdd >> 56 & 0xFF));
        byteBuffer.push_back((toAdd >> 48 & 0xFF));
        byteBuffer.push_back((toAdd >> 40 & 0xFF));
        byteBuffer.push_back((toAdd >> 32 & 0xFF));
        byteBuffer.push_back((toAdd >> 24 & 0xFF));
        byteBuffer.push_back((toAdd >> 16 & 0xFF));
        byteBuffer.push_back((toAdd >> 8 & 0xFF));
        byteBuffer.push_back((toAdd & 0xFF));
    }

    template <>
    void FormattedCombatLog::add(int64_t toAdd)
    {
        byteBuffer.push_back((toAdd >> 56 & 0xFF));
        byteBuffer.push_back((toAdd >> 48 & 0xFF));
        byteBuffer.push_back((toAdd >> 40 & 0xFF));
        byteBuffer.push_back((toAdd >> 32 & 0xFF));
        byteBuffer.push_back((toAdd >> 24 & 0xFF));
        byteBuffer.push_back((toAdd >> 16 & 0xFF));
        byteBuffer.push_back((toAdd >> 8 & 0xFF));
        byteBuffer.push_back((toAdd & 0xFF));
    }

    template <>
    void FormattedCombatLog::add(std::string toAdd)
    {
        std::cout<<"Adding: " << toAdd << std::endl;
        byteBuffer.insert(byteBuffer.end(), toAdd.begin(), toAdd.end());
    }

    template <>
    void FormattedCombatLog::add(const FormattedCombatLog toAdd)
    {
        byteBuffer.insert(byteBuffer.end(),
                          toAdd.byteBuffer.begin(),
                          toAdd.byteBuffer.end());
    }

    void FormattedCombatLog::clear()
    {
        byteBuffer.clear();
    }

    uint32_t FormattedCombatLog::size()
    {
        return byteBuffer.size();
    }

    void FormattedCombatLog::toTerminal()
    {
        std::vector<uint8_t>::iterator it;

        for (it = byteBuffer.begin(); it != byteBuffer.end(); ++it)
        {
            printf("%02x", *it);
        }

        std::cout << std::endl;
    }

    CombatLogFormatter::~CombatLogFormatter()
    {
    }
}
