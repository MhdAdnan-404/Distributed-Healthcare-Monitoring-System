package com.mhd.vitalSignManagement.Clients.DataCollection.DTO;

import lombok.Builder;

import java.util.List;

@Builder
public record BulkDeleteRequest(
        List<String> deviceUniqueIdentifiers
) {
}
