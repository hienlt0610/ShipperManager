package edu.hutech.shippermanager.service;

import java.util.List;

import edu.hutech.shippermanager.model.Routes;

/**
 * Created by jerem on 12/11/2016.
 */

public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Routes> route);
}
