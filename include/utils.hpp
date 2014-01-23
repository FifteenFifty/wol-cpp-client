/**
 * @file   utils.hpp
 *
 * @brief  This file contains definitions for utilities that shall be used by
 *         the WoL client.
 *
 * @author Matthew Bissenden
 */

#ifndef WOL_CLIENT_UTILS
#define WOL_CLIENT_UTILS

#include <string>
#include <list>
#include <stdint.h>

/**
 * This namespace encapsulates all WoL client functionality.
 */
namespace WoL
{
    /**
     * This namespace encapsulates all WoL utils functionality.
     */
    namespace Utils
    {
        /**
         * This cl  ass encapsulates all std::string utils functionality.
         */
        class StringUtils
        {
        public:
            /**
             * This method parses a line of CSV data and returns an ordered
             * std::list of std::strings, each containing an element of the
             * passed list.
             *
             * @param csv A line of CSV data that is to be split into a
             *            std::list of std::strings.
             *
             * @return    A std::list of std::strings containing the data
             *            that was passed in CSV format.
             */
            static std::list<std::string> parseCsv(std::string csv);
        };
    }
}

#endif
