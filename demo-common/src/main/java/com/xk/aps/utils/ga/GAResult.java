package com.xk.aps.utils.ga;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sy
 * @date 2022/4/1 - 14:55
 */
@Data
public class GAResult {
    public double[] bestOne;
    public List<ArrayList<Double>> workEndList;
}
