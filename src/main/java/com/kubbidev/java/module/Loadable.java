package com.kubbidev.java.module;

/**
 * The Loadable interface represents an object that can be loaded and unloaded in a system.
 * It provides methods to set up, shut down, and reload the object.
 */
public interface Loadable {

    /**
     * Performs the initial setup for the object.
     * This method is called when the object is first loaded into the system.
     * Implementations should use this method to perform any necessary setup tasks.
     */
    void setup();

    /**
     * Shuts down the object.
     * This method is called when the object needs to be unloaded from the system.
     * Implementations should use this method to release resources and perform cleanup tasks.
     */
    void shutdown();

    /**
     * Reloads the object by first shutting it down and then setting it up again.
     * This method can be called to refresh the state of the object without completely unloading it.
     * Implementations should use this method to reset the object's state if necessary.
     * This method is optional, and the default implementation is provided here.
     * By default, this method calls the {@link #shutdown()} method followed by the {@link #setup()} method.
     * However, classes implementing this interface can override this default behavior if needed.
     */
    default void reload() {
        shutdown();
        setup();
    }
}