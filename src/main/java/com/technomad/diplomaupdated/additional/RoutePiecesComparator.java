package com.technomad.diplomaupdated.additional;

import com.technomad.diplomaupdated.model.RoutePiece;
import com.technomad.diplomaupdated.model.Stop;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class RoutePiecesComparator implements Comparator<RoutePiece> {

    @Override
    public int compare(RoutePiece o1, RoutePiece o2) {
        return (o1.getStartPoint().getOrderInList() - o2.getStartPoint().getOrderInList());
    }
}