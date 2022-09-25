package se.hackerbrau;

public class Tuple3<T, T1, T2> {
    T fst;
    T1 snd;
    T2 srd;

    public Tuple3(T fst, T1 snd, T2 srd) {
        this.fst = fst;
        this.snd = snd;
        this.srd = srd;
    }

    public T getFst() {
        return fst;
    }

    public T1 getSnd() {
        return snd;
    }

    public T2 getSrd() {
        return srd;
    }
}
