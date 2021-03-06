/**
 * @file client.cpp
 *
 * @desc The entry-point to the world of logs c++ client.
 *
 * @author Matthew Bissenden
 */
#include <iostream>
#include <string>
#include <argument-parser.hpp>
#include <combat-log-parser.hpp>
#include <sstream>
#include <combat-log-formatter.hpp>
#include <wol-combat-log-formatter.hpp>

using namespace WoL;

int main(int argc, char **argv)
{
    TypedArgumentProcessor<std::string> logFilePathProcessor(
                                                        "logPath",
                                                        "The path to the "
                                                            "World of Warcraft "
                                                            "log file.",
                                                        "unset");
    ArgumentParser::registerProcessor(&logFilePathProcessor);

    TypedArgumentProcessor<std::string> usernameProcessor(
                                                        "username",
                                                        "The username of "
                                                            "the World of "
                                                            "Logs account "
                                                            "to which combat "
                                                            "logs are to be "
                                                            "uploaded.",
                                                        "username");
    ArgumentParser::registerProcessor(&usernameProcessor);

    TypedArgumentProcessor<std::string> passwordProcessor(
                                                        "password",
                                                        "The password of "
                                                            "the World of "
                                                            "Logs account "
                                                            "to which combat "
                                                            "logs are to be "
                                                            "uploaded.",
                                                        "pa55word");
    ArgumentParser::registerProcessor(&passwordProcessor);

    ArgumentParser::parseArguments(argc, argv);

    CombatLogParser parser(logFilePathProcessor.getValue());
    CombatLog log = parser.parseLog();

    CombatLogFormatter *formatter = NULL;

    formatter = new WolCombatLogFormatter();

    FormattedCombatLog *formattedLog = formatter->formatLog(log);

    delete formatter;

    formattedLog->toTerminal();

    delete formattedLog;

    return 0;
}
