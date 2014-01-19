/**
 * @file argument-parser.cpp
 *
 * @brief This file contains implementations for a basic command-line
 *        argument processor.
 *
 * @author Matthew Bissenden
 */

#include <argument-parser.hpp>
#include <iostream>

namespace WoL
{
    ArgumentProcessorMap ArgumentParser::argProcessorMap;

    ArgumentProcessor::~ArgumentProcessor()
    {
    }

    std::string ArgumentProcessor::getHelpMessage()
    {
        return getName() + "=arg : " + helpMessage;
    }

    std::string ArgumentProcessor::getName()
    {
        return argName;
    }

    ArgumentProcessor::ArgumentProcessor(std::string argName,
                                         std::string helpMessage)
    :
    argName(argName),
    helpMessage(helpMessage)
    {
    }

    void ArgumentParser::registerProcessor(ArgumentProcessor *processor)
    {
        if (!processor)
        {
            /**
             * @TODO This error should be handled.
             *       MB 15/01/2014
             */
            return;
        }

        ArgumentProcessorMap::iterator                  argMapIt;
        std::pair<ArgumentProcessorMap::iterator, bool> result;

        result = argProcessorMap.insert(std::pair<std::string,
                                                  ArgumentProcessor*>(
                                                        processor->getName(),
                                                        processor));

        if (!result.second)
        {
            /**
             * @TODO A duplicate argument name exists. Handle this error.
             *       MB 15/01/2014
             */
        }
    }

    void ArgumentParser::parseArguments(int argc, char **argv)
    {
        std::map<std::string, ArgumentProcessor*>::iterator mapIt;

        for (int i = 1; i < argc; ++i)
        {
            std::string arg(argv[i]);
            size_t      equalsPos = arg.find("=");
            std::string argName   = arg.substr(0, equalsPos);
            std::string argValue  = arg.substr(equalsPos + 1, arg.length());

            if ((mapIt = argProcessorMap.find(argName)) != argProcessorMap.end())
            {
                if (!mapIt->second)
                {
                    /**
                     * @TODO This error should be handled.
                     *       MLB 19/01/2014
                     */
                    return;
                }

                (mapIt->second)->parseValue(argValue);
            }
            else
            {
                std::cout << "Unrecognised argument: "
                          << argName
                          << "."
                          << std::endl;
                printHelp();
                return;
            }
        }
    }

    void ArgumentParser::printHelp()
    {
        std::map<std::string, ArgumentProcessor*>::iterator mapIt;

        std::cout << "Usage: " << std::endl;

        for (mapIt = argProcessorMap.begin();
             mapIt != argProcessorMap.end();
             ++mapIt)
        {
            if (!mapIt->second)
            {
                /**
                 * @TODO Handle this error.
                 *       MLB 19/01/2014
                 */
                return;
            }

            std::cout << (mapIt->second)->getHelpMessage() << std::endl;
        }
    }
}
