package ppu.momen.healthcare.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ppu.momen.healthcare.model.PatientMeasurement;

import java.time.Instant;
import java.util.*;

@Service
public class PatientMeasurementsService {
    private final WriteApiBlocking writeApi;
    private final InfluxDBClient client;
    private final String bucket;

    public PatientMeasurementsService(WriteApiBlocking writeApi,
                              InfluxDBClient client,
                              @Value("${spring.influxdb.bucket}") String bucket) {
        this.writeApi = writeApi;
        this.client   = client;
        this.bucket   = bucket;
    }

    /** Write a measurement as a time-series point */
    public void record(PatientMeasurement m) {
        m.setTimestamp(Instant.now());
        writeApi.writePoint(
                com.influxdb.client.write.Point.measurement("vitals")
                        .addTag("patientNumber", m.getPatientNumber())
                        .addField("heartRate", m.getHeartRate())
                        .addField("bodyTemperature", m.getBodyTemperature())
                        .addField("systolicPressure", m.getSystolicPressure())
                        .addField("diastolicPressure", m.getDiastolicPressure())
                        .addField("bloodGlucose", m.getBloodGlucose())
                        .addField("spo2", m.getSpo2())
                        .time(m.getTimestamp(), WritePrecision.MS)
        );
    }

    public List<PatientMeasurement> getLatest(String patientNumber, int limit) {
        String flux = String.format(
                "from(bucket:\"%s\")\n" +
                        "  |> range(start: -30d)\n" +
                        "  |> filter(fn: (r) => r._measurement == \"vitals\" and r.patientNumber == \"%s\")\n" +
                        "  |> sort(columns:[\"_time\"], desc:true)\n" +
                        "  |> limit(n:%d)",
                bucket, patientNumber, limit
        );

        List<FluxTable> tables = client.getQueryApi().query(flux);
        Map<Instant, PatientMeasurement> measurementMap = new HashMap<>();

        for (FluxTable table : tables) {
            for (FluxRecord record : table.getRecords()) {
                Instant time = record.getTime();
                PatientMeasurement m = measurementMap.computeIfAbsent(time, t -> {
                    PatientMeasurement pm = new PatientMeasurement();
                    pm.setPatientNumber(patientNumber);
                    pm.setTimestamp(time);
                    return pm;
                });

                switch (Objects.requireNonNull(record.getField())) {
                    case "heart_rate":
                        m.setHeartRate(((Number) Objects.requireNonNull(record.getValue())).intValue());
                        break;
                    case "body_temperature":
                        m.setBodyTemperature(((Number) Objects.requireNonNull(record.getValue())).doubleValue());
                        break;
                    case "systolic_pressure":
                        m.setSystolicPressure(((Number) Objects.requireNonNull(record.getValue())).intValue());
                        break;
                    case "diastolic_pressure":
                        m.setDiastolicPressure(((Number) Objects.requireNonNull(record.getValue())).intValue());
                        break;
                    case "blood_glucose":
                        m.setBloodGlucose(((Number) Objects.requireNonNull(record.getValue())).doubleValue());
                        break;
                    case "spo2":
                        m.setSpo2(((Number) Objects.requireNonNull(record.getValue())).intValue());
                        break;
                }
            }
        }

        return new ArrayList<>(measurementMap.values());
    }

}
