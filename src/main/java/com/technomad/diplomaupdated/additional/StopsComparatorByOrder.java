package com.technomad.diplomaupdated.additional;

import com.technomad.diplomaupdated.model.Stop;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class StopsComparatorByOrder implements Comparator<Stop> {

    @Override
    public int compare(Stop o1, Stop o2) {
        return (o1.getOrderInList() - o2.getOrderInList());
    }
}
