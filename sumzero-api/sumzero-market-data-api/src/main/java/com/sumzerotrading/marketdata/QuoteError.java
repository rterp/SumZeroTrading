/**
 * MIT License

Copyright (c) 2015  Rob Terpilowski

Permission is hereby granted, free of charge, to any person obtaining a copy of this software 
and associated documentation files (the "Software"), to deal in the Software without restriction, 
including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, 
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING 
BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 
*/

package com.sumzerotrading.marketdata;

/**
 *
 * @author Rob Terpilowski
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
