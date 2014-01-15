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
    WoL::TypedArgumentProcessor<std::string> processor("hello",
                                                       "This is a help "
                                                        "message.",
                                                       "test");

    WoL::ArgumentParser::registerProcessor(&processor);
    WoL::ArgumentParser::parseArguments(argc, argv);

    return 0;
}
