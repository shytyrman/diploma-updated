package com.technomad.diplomaupdated.service;

import com.technomad.diplomaupdated.model.Route;
import com.technomad.diplomaupdated.model.Stop;
import com.technomad.diplomaupdated.model.response.Place;
import com.technomad.diplomaupdated.model.state.PlaceState;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    public List<Place> getReservedPlaces(Route route, Stop start, Stop finish) {

        List<Place> result = new ArrayList<>();

        //Initializing all places
        for (int i = 0; i < 40; i++) {
            if (route.isPlaceFreeInRouteBetweenStops(i, start, finish)) {
                result.add(new Place(i, PlaceState.FREE));
            }
            else {
                result.add(new Place(i, PlaceState.TAKEN));
            }
        }

        return result;
    }

}
