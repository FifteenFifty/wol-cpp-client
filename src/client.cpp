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

int main(int argc, char **argv)
{
    WoL::TypedArgumentProcessor<std::string> wowDirProcessor(
                                                        "wowdir",
                                                        "The path to the "
                                                            "World of Warcraft "
                                                            "directory.",
                                                        "unset");
    WoL::ArgumentParser::registerProcessor(&wowDirProcessor);

    WoL::TypedArgumentProcessor<std::string> usernameProcessor(
                                                        "username",
                                                        "The username of "
                                                            "the World of "
                                                            "Logs account "
                                                            "to which combat "
                                                            "logs are to be "
                                                            "uploaded.",
                                                        "username");
    WoL::ArgumentParser::registerProcessor(&usernameProcessor);

    WoL::TypedArgumentProcessor<std::string> passwordProcessor(
                                                        "password",
                                                        "The password of "
                                                            "the World of "
                                                            "Logs account "
                                                            "to which combat "
                                                            "logs are to be "
                                                            "uploaded.",
                                                        "pa55word");
    WoL::ArgumentParser::registerProcessor(&passwordProcessor);

    WoL::ArgumentParser::parseArguments(argc, argv);

    return 0;
}
