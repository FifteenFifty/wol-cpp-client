/**
 * @file  argument-parser.hpp
 *
 * @brief This file contains definitions for a basic command-line argument
 *        parser.
 *
 * @author Matthew Bissenden
 */
#ifndef WOL_CPP_CLIENT_ARGUMENT_PARSER
#define WOL_CPP_CLIENT_ARGUMENT_PARSER

/**
 * This class defines an ArgumentProcessor from which all other
 * ArgumentProcessors must inherit. As such, this class cannot be
 * instantiated directly.
 */
class ArgumentProcessor
{
public:
    /**
     * This method sets the ArgumentProcessor's help message.
     */
    virtual void setHelpMessage(std::string message) = 0;
};

/**
 * This class implements a basic command-line argument parser.
 */
class ArgumentParser
{
public:
    /**
     * This method registers an argument processor with the argument parser.
     *
     * @param processor A pointer to an argument processor that is to be
     *                  added to this ArgumentParser.
     */
    static void registerProcessor(ArgumentProcessor *processor);

    /**
     * This method parses command-line arguments. It is passed the argc and
     * argv from the program's main method, and iterates through the list of
     * registered ArgumentProcessors, populating each if applicable.
     *
     * @param argc The argument count, as passed into the program's main()
     *             method.
     * @param argv The argument values, as passed into the program's main
     *             method.
     */
    static void parseArguments(int argc, char **argv);

private:
    static std::list<ArgumentProcessor*> argParsers; /**< A list of pointers
                                                      *   to
                                                      *   ArgumentProcessor%s,
                                                      *   containing all
                                                      *   ArgumentProcessor%s
                                                      *   that have been
                                                      *   registered. */
};

#endif
