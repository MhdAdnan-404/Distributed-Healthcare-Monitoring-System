package com.mhd.DataCollection.DTO.Device;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BulkDeleteRequest(
        @NotNull(message = "Device IDs list cannot be null")
        List<String> deviceUniqueIdentifiers
) {
}
