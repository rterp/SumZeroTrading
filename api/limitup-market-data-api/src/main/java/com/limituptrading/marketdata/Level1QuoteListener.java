/*
 * Created on Jan 9, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.limituptrading.marketdata;

import com.limituptrading.data.Bar;

/**
 * @author robbob
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface Level1QuoteListener {

        public abstract void quoteRecieved( ILevel1Quote quote );
		
}
