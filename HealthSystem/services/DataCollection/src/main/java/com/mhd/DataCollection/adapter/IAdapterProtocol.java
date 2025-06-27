package com.mhd.DataCollection.adapter;

import com.mhd.DataCollection.Domain.model.Measurment.VitalSignMeasurment;
import com.mhd.DataCollection.Domain.model.ConnectionInformation;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface IAdapterProtocol {

    VitalSignMeasurment getVitalSign(ConnectionInformation connectionInformation);

}
