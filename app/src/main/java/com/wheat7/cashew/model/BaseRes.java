package com.wheat7.cashew.model;

import java.io.Serializable;

/**
 * Created by wheat7 on 2017/8/5.
 */

public class BaseRes<T> implements Serializable {

    private boolean error;
    private T results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
