cmake_minimum_required(VERSION 3.22.1)

project("fmodcore")

set(lib_dir ${CMAKE_SOURCE_DIR}/../../../libs/${ANDROID_ABI})

set(include_dir ${CMAKE_SOURCE_DIR}/include)

include_directories(${include_dir})

add_library(fmod
        SHARED
        IMPORTED)
set_target_properties(fmod
        PROPERTIES IMPORTED_LOCATION
        ${lib_dir}/libfmod.so)

add_library(fmodL
        SHARED
        IMPORTED)
set_target_properties(fmodL
        PROPERTIES IMPORTED_LOCATION
        ${lib_dir}/libfmodL.so)

add_library(${CMAKE_PROJECT_NAME} SHARED
        FmodCore.cpp
        FmodEffects.cpp
        FmodPlayer.cpp)

target_link_libraries(${CMAKE_PROJECT_NAME}

        android
        log
        fmod
        fmodL)