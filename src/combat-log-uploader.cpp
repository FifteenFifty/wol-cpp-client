/**
 * @file   combat-log-uploader.cpp
 *
 * @brief  This file contains the implementation for a basic combat log
 *         upload manager.
 *
 * @author Matthew Bissenden
 */

#include <combat-log-uploader.hpp>
#include <curl/curl.h>

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

    bool CombatLogUploader::upload()
    {
        CURL *curl = NULL;

        curl = curl_easy_init();

        if (curl)
        {
            curl_easy_cleanup(curl);
        }
        else
        {
            /**
             * @TODO This error should be handled.
             *       MLB 21/01/2014
             */
        }
        return true;
    }
}
