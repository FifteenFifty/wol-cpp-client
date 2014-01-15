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

#include <string>
#include <map>

/**
 * This namespace encapsulates all WoL client functionality.
 */
namespace WoL
{
    /*
     * Forward declaration such that the ArgProcessorMap can be defined at the
     * top of this file.
     */
    class ArgumentProcessor;

    /**
     * This typedef aids readability by shortening the typename of a mapping
     * of argument name to ArgumentProcessor pointer.
     */
    typedef std::map<std::string, ArgumentProcessor*> ArgumentProcessorMap;

    /**
     * This class defines an ArgumentProcessor from which all other
     * ArgumentProcessors must inherit. As such, this class cannot be
     * instantiated directly.
     */
    class ArgumentProcessor
    {
    public:
        /**
         * A basic destructor.
         */
        virtual ~ArgumentProcessor();

        /**
         * This method processes the argument's given value.
         *
         * @param argValue A string containing this argument's given value.
         */
        virtual void parseValue(std::string argValue) = 0;

        /**
         * This method return's this ArgumentProcessor's help messsage.
         *
         * @return This ArgumentProcessor's help message.
         */
        std::string getHelpMessage();

        /**
         * This method retrieves the name of the argument represented by this
         * ArgumentProcessor.
         *
         * @return The name of the argument represented by this
         *         ArgumentProcessor.
         */
        std::string getName();

    protected:
        /**
         * A basic constructor.
         *
         * @param argName        A string containing the name of this
         *                       command-line argument (without preceding
         *                       hyphens).
         * @param helpMessage This ArgumentProcessor's help message.
         */
        ArgumentProcessor(std::string argName,
                          std::string helpMessage);

    private:
        std::string argName;     /**< The name of this argument. */
        std::string helpMessage; /**< The help message associated with this
                                  *   ArgumentProcessor. */
    };

    /**
     * This class defines a typed ArgumentProcessor. It is used to retrieve a
     * typed command-line argument.
     */
    template <typename T>
    class TypedArgumentProcessor : public ArgumentProcessor
    {
    public:
        /**
         * A basic constructor.
         */
        TypedArgumentProcessor(std::string argName,
                               std::string helpMessage,
                               T           defaultValue);

        /**
         * A basic destructor.
         */
        virtual ~TypedArgumentProcessor();

        /**
         * @copydoc ArgumentProcessor::parseValue(std::string)
         */
        void parseValue(std::string argValue);

        /**
         * This method returns the value of this argument. If no value has been
         * passed through the command-line, the default value is returned.
         *
         * @return The value of this argument. If no value has been passed
         *         through the command-line, the default value is returned.
         */
        T getValue();

    private:
        T value; /** The value of this command-line argument. */
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
        static ArgumentProcessorMap argProcessorMap; /**< A mapping of
                                                      *   argument name to
                                                      *   ArgumentProcessor
                                                      *   pointer, containing
                                                      *   all
                                                      *   ArgumentProcessor%s
                                                      *   that have been
                                                      *   registered. */
    };
}

#endif
