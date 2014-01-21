# A basic automake Makefile.

bin_PROGRAMS = wol-client

wol_client_SOURCES  = include/argument-parser.hpp\
                      include/combat-log-parser.hpp\
                      include/combat-log-structures.hpp\
                      include/combat-log-uploader.hpp\
                      src/argument-parser.cpp\
                      src/client.cpp\
                      src/combat-log-parser.cpp\
                      src/combat-log-structures.cpp\
                      src/combat-log-uploader.cpp
wol_client_CXXFLAGS = $(CURL_CXXFLAGS)\
                      '-Iinclude'
wol_client_LDADD    = $(CURL_LIBS)
