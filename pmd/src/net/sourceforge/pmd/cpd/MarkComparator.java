package net.sourceforge.pmd.cpd;

import java.util.Comparator;
import java.util.List;

public class MarkComparator implements Comparator {

    private final int comparisonUpdateInterval;
    private CPDListener l;
    private long comparisons;
    private List code;

    public MarkComparator(CPDListener l, List code) {
        this(l, code, 10000);
    }

    public MarkComparator(CPDListener l, List code, int comparisonUpdateInterval) {
        this.l = l;
        this.code = code;
        this.comparisonUpdateInterval = comparisonUpdateInterval;
    }

    public int compare(Object o1, Object o2) {
        comparisons++;
        if (comparisons % comparisonUpdateInterval == 0) {
            l.comparisonCountUpdate(comparisons);
        }

        Mark mark1 = (Mark)o1;
        Mark mark2 = (Mark)o2;
        for (int i = 1; i < code.size(); i++) {
            int cmp = tokenAt(i, mark1).compareTo(tokenAt(i, mark2));
            if (cmp != 0) {
                return cmp;
            }
        }
        return 0;
    }

    public TokenEntry tokenAt(int index, Mark mark) {
        return (TokenEntry)code.get((index + mark.getIndexIntoTokenArray()) % code.size());
    }
}
