/**
 * @file argument-parser.cpp
 *
 * @brief This file contains implementations for a basic command-line
 *        argument processor.
 *
 * @author Matthew Bissenden
 */

#include <argument-parser.hpp>
#include <sstream>
#include <iostream>

namespace WoL
{
    ArgumentProcessorMap ArgumentParser::argProcessorMap;

    ArgumentProcessor::~ArgumentProcessor()
    {
    }

    std::string ArgumentProcessor::getHelpMessage()
    {
        return helpMessage;
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

    template <typename T>
    TypedArgumentProcessor<T>::TypedArgumentProcessor(std::string argName,
                                                      std::string helpMessage,
                                                      T           defaultValue)
    :
    ArgumentProcessor(argName, helpMessage),
    value(defaultValue)
    {
    }
    template TypedArgumentProcessor<std::string>::TypedArgumentProcessor(std::string argName,
                                                                         std::string helpMessage,
                                                                         std::string defaultValue);

    template <typename T>
    TypedArgumentProcessor<T>::~TypedArgumentProcessor()
    {
    }

    template <typename T>
    void TypedArgumentProcessor<T>::parseValue(std::string argValue)
    {
        /**
         * @TODO This lexical casting is performed very naively, and with no
         *       error-handling.
         *       MB 15/01/2014
         */
        std::stringstream interpreter;

        interpreter << argValue;

        interpreter >> value;
    }

    template <typename T>
    T TypedArgumentProcessor<T>::getValue()
    {
        return value;
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
        for (int i = 1; i < argc; ++i)
        {
            std::string arg(argv[i]);
            size_t      equalsPos = arg.find("=");
            std::string argName   = arg.substr(0, equalsPos);
            std::string value     = arg.substr(equalsPos + 1, arg.length());

            //TODO: Pass the argument value to the correct argument parser
        }
    }
}
