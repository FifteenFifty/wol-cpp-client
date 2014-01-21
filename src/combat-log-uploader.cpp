/**
 * @file   combat-log-uploader.cpp
 *
 * @brief  This file contains the implementation for a basic combat log
 *         upload manager.
 *
 * @author Matthew Bissenden
 */

#include <combat-log-uploader.hpp>

namespace WoL
{
    CombatLogUploader::CombatLogUploader(std::string      username,
                                         std::string      passwordHash,
                                         CombatLogParser &logParser)
    :
    username(username),
    passwordHash(passwordHash),
    logParser(logParser)
    {
    }
}
