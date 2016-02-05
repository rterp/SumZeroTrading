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


package com.sumzerotrading.ib;

/**
 *
 * @author RobTerpilowski
 */
public class IBConnectionUtil {

    protected String host;
    protected int port;
    protected int clientId;
    protected boolean connected = false;
    protected IBSocket ibSocket = null;

    public IBConnectionUtil(String host, int port, int clientId) {
        this.host = host;
        this.port = port;
        this.clientId = clientId;
    }

    public IBSocket getIBSocket() {
        if (ibSocket == null) {
            IBConnectionInfo info = new IBConnectionInfo(host, port, clientId);
            ibSocket = IBConnectionRegistry.getIBSocket(info);
        }

        return ibSocket;
    }
}
