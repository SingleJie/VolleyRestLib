/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.single.library.volley;

import edu.single.library.volley.error.VolleyError;
import edu.single.library.volley.interfaces.Cache;

/**
 * Encapsulates a parsed response for delivery.
 *
 * @param <T> Parsed type of this response
 */
public class Response<T> {

    /**
     * Callback interface for delivering parsed responses.
     */
    public interface Listener<T> {
        /**
         * Called when a response is received.
         */
        public void onResponse(T response);
    }

    /**
     * Callback interface for delivering error responses.
     */
    public interface ErrorListener {
        /**
         * Callback method that an error has been occurred with the
         * provided error code and optional user-readable message.
         */
        public void onErrorResponse(VolleyError error);
    }

    /**
     * Returns a successful response containing the parsed result.
     */
    public static <T> Response<T> success(T result, Cache.Entry cacheEntry) {
        return new Response<T>(result, cacheEntry);
    }

    public static <T> Response<T> error(VolleyError error) {
        return new Response<T>(error);
    }

    public final T result;

    public final Cache.Entry cacheEntry;

    public final VolleyError error;

    public boolean intermediate = false;

    public boolean isSuccess() {
        return error == null;
    }


    private Response(T result, Cache.Entry cacheEntry) {
        this.result = result;
        this.cacheEntry = cacheEntry;
        this.error = null;
    }

    private Response(VolleyError error) {
        this.result = null;
        this.cacheEntry = null;
        this.error = error;
    }
}
