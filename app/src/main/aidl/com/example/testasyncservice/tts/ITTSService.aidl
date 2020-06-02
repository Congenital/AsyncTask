// ITTSService.aidl
package com.example.testasyncservice.tts;

// Declare any non-default types here with import statements

interface ITTSService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void testITTS();
    String convertDataToString(in byte[] data);
}
