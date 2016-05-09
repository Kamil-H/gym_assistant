package com.gymassistant;

import android.app.Application;

import com.gymassistant.Models.Series;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by KamilH on 2016-03-25.
 */
public class GlobalClass extends Application {
    public Map<Integer, List<Series>> map = new HashMap<Integer, List<Series>>();
}
