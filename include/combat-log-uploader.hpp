/**
 * @file   combat-log-uploader.hpp
 *
 * @brief  This file contains definitions for a basic combat log upload
 *         manager.
 *
 * @author Matthew Bissenden
 */

#ifndef WOL_CLIENT_COMBATLOG_UPLOADER
#define WOL_CLIENT_COMBATLOG_UPLOADER

#include <combat-log-parser.hpp>

/**
 * This namespace encapsulates all WoL client functionality.
 */
namespace WoL
{
    /**
     * This class defines a basic combat log uploader.
     */
    class CombatLogUploader
    {
    public:
        /**
         * A basic constructor that initialises internal variables and
         * associates a CombatLogParser with this uploader.
         *
         * @param username     The username of the account to which logs are
         *                     to be uploaded.
         * @param passwordHash A hash of the password of the account to which
         *                     logs are to be uploaded.
         * @param logParser    A reference to a CombatLogParser that shall be
         *                     used to parse the combat log for uploading.
         */
        CombatLogUploader(std::string      username,
                          std::string      passwordHash,
                          CombatLogParser &logParser);

        /**
         * This method uploads combat log information to the WoL server.
         *
         * @return True if the combat log was parsed and uploaded
         *         successfully. False otherwise.
         */
        bool upload();


    private:
        std::string      username;     /**< The username of the account to
                                        *   which logs are to be uploaded. */
        std::string      passwordHash; /**< A hash of the password of the
                                        *   account to which logs are to be
                                        *   uploaded. */
        CombatLogParser &logParser;    /**< A reference to a CombatLogParser
                                        *   that shall be used to parse
                                        *   combat logs for uploading. */
    };
}

#endif
