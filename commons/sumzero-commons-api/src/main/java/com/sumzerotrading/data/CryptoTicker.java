package com.sumzerotrading.data;

public class CryptoTicker extends GenericTicker{

    public CryptoTicker(String symbol) {
        super(symbol);
    }

    @Override
    public InstrumentType getInstrumentType() {
        return InstrumentType.CRYPTO;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return super.hashCode();
    }

    
}
