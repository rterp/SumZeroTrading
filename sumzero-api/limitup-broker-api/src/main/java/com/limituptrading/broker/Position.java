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

package com.limituptrading.broker;

import java.io.Serializable;

public class Position implements Serializable {

    enum Status {

        OPEN, CLOSED
    };
    protected Transaction openTransaction;
    protected Transaction closeTransaction;
    protected Status status;

    public Transaction getOpenTransaction() {
        return openTransaction;
    }

    public void setOpenTransaction(Transaction openTransaction) {
        this.openTransaction = openTransaction;
    }

    public Transaction getCloseTransaction() {
        return closeTransaction;
    }

    public void setCloseTransaction(Transaction closeTransaction) {
        this.closeTransaction = closeTransaction;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + ((closeTransaction == null) ? 0 : closeTransaction.hashCode());
        result = prime * result
                + ((openTransaction == null) ? 0 : openTransaction.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Position)) {
            return false;
        }
        Position other = (Position) obj;
        if (closeTransaction == null) {
            if (other.closeTransaction != null) {
                return false;
            }
        } else if (!closeTransaction.equals(other.closeTransaction)) {
            return false;
        }
        if (openTransaction == null) {
            if (other.openTransaction != null) {
                return false;
            }
        } else if (!openTransaction.equals(other.openTransaction)) {
            return false;
        }
        if (status == null) {
            if (other.status != null) {
                return false;
            }
        } else if (!status.equals(other.status)) {
            return false;
        }
        return true;
    }
}
