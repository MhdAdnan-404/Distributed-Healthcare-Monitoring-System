package com.example.notification.kafka.Alert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VitalAlert {
    private String deviceId;
    private Integer patientId;
    private String patientName;
    private String vitalKey;
    private Double vitalValue;
    private String limitBreach;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getVitalKey() {
        return vitalKey;
    }

    public void setVitalKey(String vitalKey) {
        this.vitalKey = vitalKey;
    }

    public Double getVitalValue() {
        return vitalValue;
    }

    public void setVitalValue(Double vitalValue) {
        this.vitalValue = vitalValue;
    }

    public String getLimitBreach() {
        return limitBreach;
    }

    public void setLimitBreach(String limitBreach) {
        this.limitBreach = limitBreach;
    }

    public String getContent() {
        return String.format(
                "Patient: %s (%d) has a %s %s — Value: %.2f",
                patientName,
                patientId,
                limitBreach,
                vitalKey,
                vitalValue
        );
    }
}
