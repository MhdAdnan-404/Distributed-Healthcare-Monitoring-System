package com.mhd.vitalSignConsumer.model;

import com.mhd.vitalSignConsumer.model.Limits.LimitEntry;
import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record DeviceRef(
        String uniqueIdentifier,
        List<LimitEntry> limits
)
{}
