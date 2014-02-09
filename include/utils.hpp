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
#include <sstream>
#include <boost/lexical_cast.hpp>

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

            /**
             * This method parses a string value as a float.
             *
             * @param value A string containing the value to be parsed.
             *
             * @return      A double containing the parsed value.
             *
             * @throws TODO
             */
            static double parseFloat(std::string value);

            /**
             * This method parses a string value as a hexadecimal number,
             * which is returned as an integer of specified size.
             *
             * @param  value A string containing the value to be parsed.
             * @tparam T     The type of value that is to be returned.
             *
             * @return       A T containing the parsed value.
             *
             * @throws TODO
             */
            template <typename T>
            static T parseHex(std::string value);

            /**
             * This method parses a string value as an integral number,
             * which is returned as an integer of specified size.
             *
             * @param  value A string containing the value to be parsed.
             * @tparam T     The type of value that is to be returned.
             *
             * @return       A T containing the parsed value.
             *
             * @throws TODO
             */
            template <typename T>
            static T parseInt(std::string value);

            /**
             * This method parses a string value as a combat log string.
             *
             * @param  value A string containing the value to be parsed.
             *
             * @return       A string containing the parsed value.
             *
             * @throws TODO
             */
            static std::string parseString(std::string value);
        };

        /**
         * This class contains conversion utility methods.
         */
        class Conversion
        {
        public:
            /**
             * This method performs a lexical cast, making use of a
             * stringstream to perform the cast.
             *
             * @param  toCast The value, of type S, being lexical cast.
             * @tparam S      The source type.
             * @tparam D      The destination type.
             *
             * @return        The value, of type D, resulting from performing
             *                a lexical cast on _value.
             *
             * @throws TODO
             * @throws Currently throws a boost::bad_lexical_cast on error.
             */
            template <typename S, typename D>
            static D lexicalCast(S toCast)
            {
                return boost::lexical_cast<D, S>(toCast);
            }
        };
    }
}

#endif
