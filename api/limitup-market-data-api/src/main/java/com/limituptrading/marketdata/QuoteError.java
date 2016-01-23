/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limituptrading.marketdata;

/**
 *
 * @author robbob
 */
public class QuoteError {
    
    protected int id = -1;
    protected int errorCode = -1;
    protected String message;
    protected Exception ex;
    
    
    public QuoteError( Exception ex ) {
        this.ex = ex;
    }
    
    public QuoteError( String message ) {
        this.message = message;
    }

    public QuoteError(int id, int errorCode, String message) {
        this.id = id;
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public Exception getException() {
        return ex;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final QuoteError other = (QuoteError) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.errorCode != other.errorCode) {
            return false;
        }
        if ((this.message == null) ? (other.message != null) : !this.message.equals(other.message)) {
            return false;
        }
        if (this.ex != other.ex && (this.ex == null || !this.ex.equals(other.ex))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.id;
        hash = 53 * hash + this.errorCode;
        hash = 53 * hash + (this.message != null ? this.message.hashCode() : 0);
        hash = 53 * hash + (this.ex != null ? this.ex.hashCode() : 0);
        return hash;
    }
    
    
    
                     
    public String toString() {
        if( id != -1 ) {
            return "id='" + id + "' errorCode=" + "'" + errorCode + "' message='" + message + "'";
        } else {
            return message;
        }
    }
    
    
}
