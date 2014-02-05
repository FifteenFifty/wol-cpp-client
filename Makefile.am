# A basic automake Makefile.

ACLOCAL_AMFLAGS = -Im4

bin_PROGRAMS = wol-client

wol_client_SOURCES  = include/argument-parser.hpp\
                      include/combat-log-formatter.hpp\
                      include/combat-log-parser.hpp\
                      include/combat-log-structures.hpp\
                      include/combat-log-uploader.hpp\
                      include/wol-combat-log-formatter.hpp\
                      include/utils.hpp\
                      src/argument-parser.cpp\
                      src/client.cpp\
                      src/combat-log-parser.cpp\
                      src/combat-log-structures.cpp\
                      src/combat-log-uploader.cpp\
                      src/utils.cpp\
                      src/wol-combat-log-formatter.cpp
wol_client_CXXFLAGS = $(CURL_CXXFLAGS)\
                      $(BOOST_REGEX_CXXFLAGS)\
                      '-Iinclude'
wol_client_LDADD    = $(CURL_LIBS)\
                      $(BOOST_REGEX_LIBS)
