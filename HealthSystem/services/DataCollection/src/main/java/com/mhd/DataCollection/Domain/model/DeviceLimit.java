package com.mhd.DataCollection.Domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DeviceLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "device_limit_values", joinColumns = @JoinColumn(name = "device_limit_id"))
    @MapKeyColumn(name = "limit_key")
    @Column(name = "limit_value")
    private Map<String, Double> limits = new HashMap<>();

/*
The related Device will not be loaded immediately when you load a DeviceLimit.

It will only be fetched when you access it, e.g., deviceLimit.getDevice().

This saves memory and speeds up performance when the relationship isn't always needed.
*/
    @OneToOne()
    @JoinColumn(name = "device_id")
    @JsonBackReference
    private Device device;
}
