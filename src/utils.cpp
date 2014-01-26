/**
 * @file   utils.cpp
 *
 * @brief  This file contains implementations for utilities that shall be
 *         used by the WoL client.
 *
 * @author Matthew Bissenden
 */

#include <utils.hpp>
#include <iostream>

namespace WoL
{
    namespace Utils
    {
        std::list<std::string> StringUtils::parseCsv(std::string csv)
        {
            size_t      commaPos     = 0;
            size_t      quotePos     = 0;
            size_t      lastCommaPos = -1;
            size_t      lastQuotePos = 0;

            std::string            toAdd;
            std::list<std::string> toReturn;

            quotePos = csv.find("\"");

            while ((commaPos = csv.find(",", lastCommaPos + 1)) !=
                   std::string::npos)
            {
                if ((quotePos != std::string::npos) && (commaPos > quotePos))
                {
                    /* Inside a quoted string. The next quote position is the
                     * terminating quote. */
                    lastQuotePos = quotePos;

                    if ((quotePos = csv.find("\"", lastQuotePos + 1)) ==
                        std::string::npos)
                    {
                        /**
                         * @TODO There exists an unterminated quoted string.
                         *       Handle this error.
                         *       MLB 26/01/2014
                         */

                    }
                    else
                    {
                        if ((commaPos = csv.find(",", quotePos + 1)) ==
                            std::string::npos)
                        {
                            /* If there are no more commas, exit the loop. */
                            break;
                        }
                    }

                    quotePos = csv.find("\"", commaPos + 1);
                }

                toAdd        = csv.substr(lastCommaPos + 1,
                                          commaPos - lastCommaPos - 1);
                lastCommaPos = commaPos;

                toReturn.push_back(toAdd);
            }

            toReturn.push_back(csv.substr(lastCommaPos + 1,
                                          csv.length() - commaPos - 1));

            return toReturn;
        }

        double StringUtils::parseFloat(std::string value)
        {
            return Conversion::lexicalCast<std::string, double>(value);
        }

        template <typename T>
        T StringUtils::parseHex(std::string value)
        {
            if (value.substr(0, 2) != "0x")
            {
                /**
                 * @TODO This is not a hex string. Handle this error.
                 *       MLB 26/01/2014
                 */
                return 0;
            }

            std::stringstream converter;
            T                 toReturn;

            converter << std::hex << value;
            converter >> toReturn;

            return toReturn;
        }
        template uint64_t StringUtils::parseHex(std::string value);
        template uint32_t StringUtils::parseHex(std::string value);

        template <typename T>
        T StringUtils::parseInt(std::string value)
        {
            return Conversion::lexicalCast<std::string, T>(value);
        }
        template uint64_t StringUtils::parseInt(std::string value);
        template uint32_t StringUtils::parseInt(std::string value);

        std::string StringUtils::parseString(std::string(value))
        {
            if (value.at(0) != '"' || value.at(value.length() - 1) != '"')
            {
                /**
                 * @TODO Not a valid quoted string. Handle this error.
                 *       MLB 26/01/2014
                 */
                return "";
            }

            return value.substr(1, value.length() -2);
        }
    }
}
