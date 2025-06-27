package com.mhd.DataCollection.Domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mhd.DataCollection.Domain.model.Enums.DeviceProtocol;
import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class ConnectionInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private DeviceProtocol protocol;

    private String connectionUrl;

//    For MQTT only
    private String topic;

    @OneToOne
    @JoinColumn(name = "device_id", nullable = false)
    @JsonBackReference
    private Device device;
}
