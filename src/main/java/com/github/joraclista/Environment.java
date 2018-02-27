package com.github.joraclista;

import com.amazonaws.regions.Regions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * Created by Alisa
 * version 1.0.
 */
@AllArgsConstructor
@Data
public class Environment {
    @Getter
    private Regions region;
}
