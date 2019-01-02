package org.neubauerfelix.manawars.game

interface IHandler {

    /**
     * Loading / Disposing order:
     * 1. 1x ILoadable#load()
     * 2. 1x ILoadableAsync#loadedAssets()
     * 3. 1x ILoadableContent#loadContent()
     * 4.a ?x ILoadableAsync#loadedAssets() - Called whenever something new was loaded asynchronously
     * 4.b ?x IResetable#reset()
     * 5. 1x IDisposable#dispose()
     */
}